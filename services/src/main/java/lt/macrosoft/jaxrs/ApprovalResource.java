package lt.macrosoft.jaxrs;

import com.google.common.base.Optional;
import com.nimbusds.jose.JOSEException;
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
    public Response sendEmails(List<String> emailList) {
        Member member = memberStatelessBean.getMember(request.getHeader(AuthUtils.AUTH_HEADER_KEY));
        if(member == null)
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(Error.MEMBER_COULND_NOT_EXTRACT_FROM_HEADER).build();

        List<Future<MailStatus>> statusList = new ArrayList<>();
        List<Approval> approvals = new ArrayList<>();
        for (String email : emailList) {
            statusList.add(mailerBean.sendMessage(email));
            approvals.add(new Approval(email, member.getEmail()));
        }

        for (Approval approval : approvals) {
            logger.log(Level.INFO, "saving approval");
            approvalDAO.save(approval);
        }

        return Response.ok().build();
    }


    @Path("approver/candidates/{email}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Approval> getApproverCandidatesEmailList(@PathParam("email") String email) {
        Optional<List<Approval>> approvals = approvalDAO.findByApproverEmail(email);
        if (approvals.isPresent())
            return approvals.get();
        else {
            return null;
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

    @Path("approver/approve/{email}")
    @POST
    public Response approve(@PathParam("email") String candidateEmail) {
        Member member = memberStatelessBean.getMember(request.getHeader(AuthUtils.AUTH_HEADER_KEY));
        if(member == null)
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(Error.MEMBER_COULND_NOT_EXTRACT_FROM_HEADER).build();

        String userEmail = member.getEmail();

        Optional<Approval> approval = approvalDAO.findByCandidateAndApprover(candidateEmail, userEmail);
        if (approval.isPresent()) {
            Approval approvalValue = approval.get();
            approvalValue.setApproved(true);
            approvalDAO.save(approvalValue);
            return Response.ok().build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity(Error.DB_APPROVAL_NOT_FOUND).build();
    }

    @Path("approver/approve")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response approve(List<String> emailList) {
        Member member = memberStatelessBean.getMember(request.getHeader(AuthUtils.AUTH_HEADER_KEY));
        if(member == null)
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(Error.MEMBER_COULND_NOT_EXTRACT_FROM_HEADER).build();
        String userEmail = member.getEmail();

        Optional<List<Approval>> approvalList = approvalDAO.findByApproverEmail(userEmail);
        if (approvalList.isPresent()) {
            for (Approval approval : approvalList.get()) {
                if (emailList.contains(approval.getCandidateEmail())) {
                    approval.setApproved(true);
                    approvalDAO.save(approval);
                }
            }
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity(Error.DB_APPROVAL_LIST_NOT_FOUND).build();
        }
    }
}
