package lt.macrosoft.entities;

import javax.persistence.*;

/**
 * Created by Arnas on 2016-05-25.
 */
@Entity
@Table(name = "APPROVAL", uniqueConstraints={
        @UniqueConstraint(columnNames = {"CANDIDATE_EMAIL", "APPROVER_EMAIL"})
})
@NamedQueries({
        @NamedQuery(name = "Approval.findByCandidateEmail", query = "SELECT a FROM Approval a WHERE a.candidateEmail = :email"),
        @NamedQuery(name = "Approval.findByApproverEmail", query = "SELECT a FROM Approval a WHERE a.approverEmail = :email"),
        @NamedQuery(name = "Approval.findByCandidateAndApprover", query = "SELECT a FROM Approval a WHERE a.approverEmail = :approverEmail AND a.candidateEmail = :candidateEmail")

})
public class Approval {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id")
    // @SequenceGenerator(name = "id", sequenceName = "hibernate_sequence")
    protected Long id;

    @Column(name = "CANDIDATE_EMAIL")
    private String candidateEmail;

    @Column(name = "APPROVER_EMAIL")
    private String approverEmail;

    @Column(name = "APPROVED")
    private boolean approved = false;

    @Column(name = "EMAIL_SENT")
    private boolean emailSent = false;

    public Approval(String candidateEmail, String approverEmail) {
        this.candidateEmail = candidateEmail;
        this.approverEmail = approverEmail;
    }

    public Approval() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCandidateEmail() {
        return candidateEmail;
    }

    public void setCandidateEmail(String candidateEmail) {
        this.candidateEmail = candidateEmail;
    }

    public String getApproverEmail() {
        return approverEmail;
    }

    public void setApproverEmail(String approverEmail) {
        this.approverEmail = approverEmail;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public boolean isEmailSent() {
        return emailSent;
    }

    public void setEmailSent(boolean emailSent) {
        this.emailSent = emailSent;
    }

    @Override
    public String toString() {
        return "Approval{" +
                "id=" + id +
                ", candidateEmail='" + candidateEmail + '\'' +
                ", approverEmail='" + approverEmail + '\'' +
                ", approved=" + approved +
                ", emailSent=" + emailSent +
                '}';
    }
}
