package lt.macrosoft.beans;

import com.google.common.base.Optional;
import lt.macrosoft.daos.ReservationDAO;
import lt.macrosoft.entities.Member;
import lt.macrosoft.entities.Reservation;
import lt.macrosoft.entities.Summerhouse;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.MultivaluedMap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
    
	/**
	 * header sample
	 * {
	 * 	Content-Type=[image/png], 
	 * 	Content-Disposition=[form-data; name="file"; filename="filename.extension"]
	 * }
	 **/
	//get uploaded filename, is there a easy way in RESTEasy?
	public String getFileName(MultivaluedMap<String, String> header) {

		String[] contentDisposition = header.getFirst("Content-Disposition").split(";");
		
		for (String filename : contentDisposition) {
			if ((filename.trim().startsWith("filename"))) {

				String[] name = filename.split("=");
				
				String finalFileName = name[1].trim().replaceAll("\"", "");
				return finalFileName;
			}
		}
		return "unknown";
	}

	//save to somewhere
	public void writeFile(byte[] content, String filename) throws IOException {

		File file = new File(filename);

		if (!file.exists()) {
			file.createNewFile();
		}

		FileOutputStream fop = new FileOutputStream(file);

		fop.write(content);
		fop.flush();
		fop.close();

	}
}
