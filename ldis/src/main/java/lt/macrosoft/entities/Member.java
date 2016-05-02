package lt.macrosoft.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lt.macrosoft.ModelEnums.YesNoType;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id")
    @SequenceGenerator(name = "id", sequenceName = "hibernate_sequence")
    protected Long id;

    protected String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    private YesNoType isAdmin;

	@NotNull
    @Enumerated(EnumType.STRING)
    private YesNoType isFullMember;
	
    private int creditAmount;
	
    public int getCreditAmount() {
		return creditAmount;
	}

	public void setCreditAmount(int creditAmount) {
		this.creditAmount = creditAmount;
	}

	public YesNoType getIsFullMember() {
		return isFullMember;
	}

	public void setIsFullMember(YesNoType isFullMember) {
		this.isFullMember = isFullMember;
	}
	
    @OneToMany(mappedBy = "member")
    protected Set<ReservationMember> reservationItems = new HashSet<>();

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

    public Set<ReservationMember> getReservationItems() {
        return reservationItems;
    }


    public YesNoType getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(YesNoType isAdmin) {
		this.isAdmin = isAdmin;
	}
}
