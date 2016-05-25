package lt.macrosoft.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arnas on 2016-05-25.
 */
@Entity
@Table(name = "DISTRICT")
@NamedQueries({
        @NamedQuery(name = "District.findAll", query = "SELECT d FROM District d"),
})
public class District {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id")
    //@SequenceGenerator(name = "id", sequenceName = "hibernate_sequence")
    private Long id;

    @Column(name = "NAME", length = 100, unique = true)
    private String name;

    @OneToMany(mappedBy = "district")
    protected List<Summerhouse> summerhouses = new ArrayList<>();
}
