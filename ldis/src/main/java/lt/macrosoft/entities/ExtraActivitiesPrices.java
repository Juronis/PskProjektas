package lt.macrosoft.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by Arnas on 2016-05-30.
 */
@Entity
public class ExtraActivitiesPrices {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    protected Long id;

    private Integer Boat;
    private Integer Horse;
    private Integer Cayak;
    private Integer Bicycle;
    private Integer ChildrenActivity;
    private Integer Trampoline;
    private Integer Sauna;
    private Integer Tub;
}
