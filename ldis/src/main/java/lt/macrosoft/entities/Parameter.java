package lt.macrosoft.entities;

import javax.persistence.*;

/**
 * Created by Main on 5/29/2016.
 */

@Entity
@Table(name = "PARAMETERS", uniqueConstraints = @UniqueConstraint(columnNames = {"NAME"}))
@NamedQueries({
        @NamedQuery(name = "Parameters.find", query = "SELECT m FROM Parameter m WHERE m.name = :name")
})



public class Parameter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "NAME", length = 100)
    private String name;

    @Column(name = "PVALUE", length = 100)
    private String pvalue;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPvalue() {return pvalue;}

    public void setPvalue(String pvalue) {this.pvalue = pvalue;}

}
