package lt.macrosoft.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "EXTRAACTIVITIES")
public class ExtraActivities {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)

   // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id")
    //@SequenceGenerator(name = "id", sequenceName = "hibernate_sequence")
    protected Long id;
	
	private Integer Boats;
	private Integer Horses;
	private Integer Cayaks;
	private Integer Bicycles;
	private Integer ChildrenActivities;
	private Integer Trampoline;
	private Integer Sauna;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getBoats() {
		return Boats;
	}
	public void setBoats(Integer boats) {
		Boats = boats;
	}
	public Integer getHorses() {
		return Horses;
	}
	public void setHorses(Integer horses) {
		Horses = horses;
	}
	public Integer getCayaks() {
		return Cayaks;
	}
	public void setCayaks(Integer cayaks) {
		Cayaks = cayaks;
	}
	public Integer getBicycles() {
		return Bicycles;
	}
	public void setBicycles(Integer bicycles) {
		Bicycles = bicycles;
	}
	public Integer getChildrenActivities() {
		return ChildrenActivities;
	}
	public void setChildrenActivities(Integer childrenActivities) {
		ChildrenActivities = childrenActivities;
	}
	public Integer getTrampoline() {
		return Trampoline;
	}
	public void setTrampoline(Integer trampoline) {
		Trampoline = trampoline;
	}
	public Integer getSauna() {
		return Sauna;
	}
	public void setSauna(Integer sauna) {
		Sauna = sauna;
	}
	
	
}
