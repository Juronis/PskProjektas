package lt.macrosoft.beans;

import com.google.common.base.Optional;
import lt.macrosoft.daos.ReservationDAO;
import lt.macrosoft.entities.Member;
import lt.macrosoft.entities.Reservation;
import lt.macrosoft.entities.Summerhouse;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Date;
import java.util.List;

/**
 * Created by Arnas on 2016-05-26.
 */
@Stateless(name = "SummerhouseStatelessEJB")
public class SummerhouseStatelessBean {
    @Inject
    ReservationDAO reservationDAO;

    public SummerhouseStatelessBean() {
    }

    public boolean isDateAvailable(Summerhouse summerhouse, Date dateStart, Date dateEnd) {
        Optional<List<Date[]>> dates = reservationDAO.findReservedDatesBySummerhouse(summerhouse.getId());
        if(dates.isPresent()) {
            for(Date[] twoDates : dates.get()) {
                // Patikrinam ar start date dubliuojasi su kita data
                if(dateStart.equals(twoDates[0])) {
                    return false;
                }
                // Patikrinam ar start date yra tarp dviejų datų
                if(dateStart.after(twoDates[0]) && dateStart.before(twoDates[1])) {
                    return false;
                }
                // Jei pabaigos data yra PO kitos reservacijos STARTO ir prieš PABAIGĄ
                // nes gali būti po labai toli...
                if(dateEnd.after(twoDates[0]) && dateEnd.before(twoDates[1])) {
                    return false;
                }
            }
            return true;
        } else {
            return true;
        }
    }

    public boolean reserve(Summerhouse summerhouse, Member member, Date dateStart, Date dateEnd) {
        Reservation reservation = new Reservation();
        reservation.setDateStart(dateStart);
        reservation.setDateEnd(dateEnd);
        reservation.setMember(member);
        reservation.setSummerhouse(summerhouse);
        reservationDAO.save(reservation);
        return reservation.getId() != null;
    }
}
