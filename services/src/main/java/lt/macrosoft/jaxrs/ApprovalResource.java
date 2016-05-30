package lt.macrosoft.jaxrs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Optional;
import com.nimbusds.jose.JOSEException;
import lt.macrosoft.beans.ApprovalStatelessBean;
import lt.macrosoft.beans.MemberStatelessBean;
import lt.macrosoft.daos.ApprovalDAO;
import lt.macrosoft.daos.MemberDAO;
import lt.macrosoft.entities.Approval;
import lt.macrosoft.entities.Member;
import lt.macrosoft.enums.Role;
import lt.macrosoft.mail.MailStatus;
import lt.macrosoft.mail.MailerBean;
import lt.macrosoft.security.Secured;
import lt.macrosoft.utils.AuthUtils;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Arnas on 2016-05-25.
 */
@Path("/approvals")
@Stateless
public class ApprovalResource {
    private static final Logger logger = Logger.getLogger(ApprovalResource.class.getName());
    @Context
    HttpServletRequest request;
    @EJB
    ApprovalStatelessBean approvalStatelessBean;
    @EJB
    MailerBean mailerBean;
    @EJB
    MemberStatelessBean memberStatelessBean;
    @Inject
    MemberDAO memberDAO;
    @Inject
    ApprovalDAO approvalDAO;

    public ApprovalResource() {
        this.approvalDAO = null;
    }

    public ApprovalResource(ApprovalDAO approvalDAO) {
        this.approvalDAO = approvalDAO;
    }
    @Secured({Role.ADMIN})
    @Path("send")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response sendEmails(List<String> emailList) throws ParseException, JOSEException {

        Optional<Member> member = memberStatelessBean.getMember(request.getHeader(AuthUtils.AUTH_HEADER_KEY));
        if (!member.isPresent()) { return Response.status(Response.Status.FORBIDDEN).build(); }
        Member member1 = member.get();

        List<Future<MailStatus>> statusList = new ArrayList<>();
        List<Approval> approvals = new ArrayList<>();
        for (String email : emailList) {
            statusList.add(mailerBean.sendMessage(email));
            approvals.add(new Approval(email, member1.getEmail()));
        }

        for (Approval approval : approvals) {
            logger.log(Level.INFO, "saving approval");
            approvalDAO.save(approval);
        }

        return Response.ok().build();
    }


    @Path("approver/candidates")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getApproverCandidatesEmailList(@Context final HttpServletRequest request) throws ParseException, JOSEException {
        Optional<Member> member = memberStatelessBean.getMember(request.getHeader(AuthUtils.AUTH_HEADER_KEY));
        if (!member.isPresent()) { return Response.status(Response.Status.FORBIDDEN).build(); }
        Member member1 = member.get();

        Optional<List<Approval>> approvals = approvalDAO.findByApproverEmail(member1.getEmail());
        if (approvals.isPresent())
            return Response.ok().entity(approvals.get()).build();
        else {
            return Response.status(Response.Status.NOT_FOUND).entity(Error.DB_APPROVAL_LIST_NOT_FOUND).build();
        }
    }

    @Path("candidate/approvers/{email}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Approval> getCandidateApproversEmailList(@PathParam("email") String email) {
        Optional<List<Approval>> approvals = approvalDAO.findByCandidateEmail(email);
        if (approvals.isPresent())
            return approvals.get();
        else {
            return null;
        }
    }

    @Path("approver/approve")
    @POST
    public Response approve(String json) throws ParseException, JOSEException {
        Optional<Member> member = memberStatelessBean.getMember(request.getHeader(AuthUtils.AUTH_HEADER_KEY));
        if (!member.isPresent()) { return Response.status(Response.Status.FORBIDDEN).build(); }
        Member member1 = member.get();

        String userEmail = member1.getEmail();


        ObjectMapper mapper = new ObjectMapper();
        JsonNode actualObj;
        String candidateEmail;
        try {
            actualObj = mapper.readTree(json);
            JsonNode jsonNode1 = actualObj.get("email");
            if (jsonNode1 == null) {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
            candidateEmail = jsonNode1.textValue();

        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            return Response.status(Response.Status.UNAUTHORIZED).build();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }


        Optional<Approval> approval = approvalDAO.findByCandidateAndApprover(candidateEmail, userEmail);
        if (approval.isPresent()) {
            Approval approvalValue = approval.get();
            approvalValue.setApproved(true);
            approvalDAO.save(approvalValue);
            if (approvalStatelessBean.tryToMakeFullMember(candidateEmail)) {

                Optional<Member> memberAprove = memberDAO.findByEmail(candidateEmail);
                if (!memberAprove.isPresent()) {
                    return Response.status(Response.Status.NOT_FOUND).build();
                }
                Member memberUpdate = memberAprove.get();
                memberUpdate.setRole(Role.FULLUSER);
                memberDAO.save(memberUpdate);
                return Response.ok().build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).entity(Error.DB_APPROVAL_NOT_FOUND).build();
    }

}
