package lt.macrosoft.daos;

import com.google.common.base.Optional;
import lt.macrosoft.entities.Approval;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by Arnas on 2016-05-25.
 */
public class ApprovalDAOImpl extends GenericDAOImpl<Approval, Long> implements ApprovalDAO {

    @Inject
    public ApprovalDAOImpl(EntityManager em) {
        super(em, Approval.class);
    }

    @Override
    public Optional<List<Approval>> findByCandidateEmail(String email) {
        return Optional.fromNullable(
                getEntityManager().createNamedQuery("Approval.findByCandidateEmail", Approval.class)
                .setParameter("email", email).getResultList()
        );
    }

    @Override
    public Optional<List<Approval>> findByApproverEmail(String email) {
        return Optional.fromNullable(
                getEntityManager().createNamedQuery("Approval.findByApproverEmail", Approval.class)
                        .setParameter("email", email).getResultList()
        );
    }

    @Override
    public Optional<Approval> findByCandidateAndApprover(String candidateEmail, String approverEmail) {
        return Optional.fromNullable(
                getEntityManager().createNamedQuery("Approval.findByCandidateAndApprover", Approval.class)
                        .setParameter("approverEmail", approverEmail).setParameter("candidateEmail", candidateEmail)
                        .getSingleResult()
        );
    }
}
