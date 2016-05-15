package lt.macrosoft.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "MEMBER", uniqueConstraints = @UniqueConstraint(columnNames = "USERNAME"))
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id")
	@SequenceGenerator(name = "id", sequenceName = "hibernate_sequence")
	private Long id;
	
	@Column(name = "USERNAME", nullable = false, length = 100)
	private String userName;

	@Column(name = "EMAIL", length = 150)
	private String email;

	@NotNull
	private boolean isAdmin;

	@NotNull
	private boolean isFullMember;

	@Column(name = "ISFACEBOOKUSER")
	private boolean facebookUser;

	private int creditAmount;

	public int getCreditAmount() {
		return creditAmount;
	}

	public void setCreditAmount(int creditAmount) {
		this.creditAmount = creditAmount;
	}

	public boolean getIsFullMember() {
		return isFullMember;
	}

	public void setIsFullMember(boolean isFullMember) {
		this.isFullMember = isFullMember;
	}

	@OneToMany(mappedBy = "member")
	protected Set<ReservationMember> reservationItems = new HashSet<>();

	public Member() {
	}

	public Member(String name) {
		this.userName = name;
	}

	public Long getId() {
		return id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String name) {
		this.userName = name;
	}

	public Set<ReservationMember> getReservationItems() {
		return reservationItems;
	}

	public boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
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
		return this.getUserName().equals(that.getUserName());
	}

	@Override
	public int hashCode() {
		return getUserName().hashCode();
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public Boolean getIsFacebookUser() {
		return facebookUser;
	}

	public void setFacebookUser(Boolean isFacebookUser) {
		this.facebookUser = isFacebookUser;
	}
}
