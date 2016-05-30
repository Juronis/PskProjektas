package lt.macrosoft.daos;

import com.google.common.base.Optional;
import lt.macrosoft.entities.Approval;

import java.util.List;
import lt.macrosoft.enums.Exceptions;

/**
 * Created by Arnas on 2016-05-25.
 */
public interface ApprovalDAO extends GenericDAO<Approval, Long> {
    Optional<List<Approval>> findByCandidateEmail(String email);

    Optional<List<Approval>> findByApproverEmail(String email);

    Optional<Approval> findByCandidateAndApprover(String candidateEmail, String approverEmail);

    Exceptions deleteApprovals(List<Approval> approvals);
}
