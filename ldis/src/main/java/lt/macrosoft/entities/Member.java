package lt.macrosoft.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lt.macrosoft.enums.Role;

import java.util.*;


@Entity
@Table(name = "MEMBER", uniqueConstraints = @UniqueConstraint(columnNames = {"EMAIL", "FACEBOOKUSER"}))
@NamedQueries({
    @NamedQuery(name = "Member.findAll", query = "SELECT m FROM Member m"),
    @NamedQuery(name = "Member.findByEmail", query = "SELECT m FROM Member m WHERE m.email = :email"),
    @NamedQuery(name = "Member.findByFacebook", query = "SELECT m FROM Member m WHERE m.facebookUser = :facebookUser"),
	@NamedQuery(name = "Member.findByToken", query = "SELECT m FROM Member m WHERE m.logintoken = :logintoken"),
	@NamedQuery(name = "Member.findCandidates", query = "SELECT m FROM Member m WHERE m.role = :role")
})
public class Member {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	//@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id")
	//@SequenceGenerator(name = "id", sequenceName = "hibernate_sequence")
	private Long id;
	
	@Column(name = "NAME", length = 100)
	private String name;

	@Column(name = "PASSWORD", nullable = true, length = 2000)
	private String password;
	
	@NotNull
	@Column(name = "EMAIL", length = 150, unique=true)
	private String email;

	@NotNull
	@Column(name = "ROLE")
	@Enumerated(EnumType.STRING)
	private Role role;
	
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	//Jei facebooko useris tai storiname facebooko id.
	@Column(name = "FACEBOOKUSER")
	private String facebookUser;

	private int creditAmount;

	@Column(name = "LOGINTOKEN", nullable = true, length = 2000)
	private String logintoken;

	@Column(name = "MEMBERSHIP_UNTIL")
	@Temporal(TemporalType.DATE)
	private Date membership;

	public int getCreditAmount() {
		return creditAmount;
	}

	public void setCreditAmount(int creditAmount) {
		this.creditAmount = creditAmount;
	}

	@JsonIgnore
	@OneToMany(mappedBy = "member")
	protected List<Reservation> reservations = new ArrayList<>();

	public Member() {
	}

	public Member(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Reservation> getReservations() {
		return reservations;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other)
			return true;
		if (other == null)
			return false;
		if (!(other instanceof Member))
			return false;
		Member that = (Member) other;
		return this.getEmail().equals(that.getEmail());
	}

	@Override
	public int hashCode() {
		return getEmail().hashCode();
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFacebookUser() {
		return facebookUser;
	}

	public void setFacebookUser(String FacebookUser) {
		this.facebookUser = FacebookUser;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLoginToken() {
		return logintoken;
	}

	public void setLoginToken(String logintoken) {
		this.logintoken = logintoken;
	}

	public Date getMembership() {
		return membership;
	}

	public void setMembership(Date membership) {
		this.membership = membership;
	}

}
