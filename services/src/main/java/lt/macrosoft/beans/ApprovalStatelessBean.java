package lt.macrosoft.beans;

import com.google.common.base.Optional;
import lt.macrosoft.daos.ApprovalDAO;
import lt.macrosoft.daos.ParameterDAO;
import lt.macrosoft.entities.Approval;
import lt.macrosoft.entities.Parameter;
import lt.macrosoft.enums.Exceptions;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

/**
 * Created by Arnas on 2016-05-30.
 */
@Stateless(name = "ApprovalStatelessEJB")
public class ApprovalStatelessBean {
    @Inject
    ApprovalDAO approvalDAO;
    @Inject
    ParameterDAO parameterDAO;

    public ApprovalStatelessBean() {
    }

    public boolean tryToMakeFullMember(String candidateEmail) {
        Optional<List<Approval>> approvals = approvalDAO.findByCandidateEmail(candidateEmail);
        if (!approvals.isPresent())
            return false;

        int size = 0;
        for (Approval approval : approvals.get()) {
            if (approval.isApproved()) {
                size++;
            }
        }

        Optional<Parameter> parameter = parameterDAO.findParameterValue("MINIMUM_RECOMMENDATIONS");

        if (!parameter.isPresent()) {
            return false;
        }

        if (Integer.parseInt(parameter.get().getPvalue()) > size)
            return false;

        if(approvalDAO.deleteApprovals(approvals.get()) != Exceptions.SUCCESS)
            return false;

        return true;
    }
}

