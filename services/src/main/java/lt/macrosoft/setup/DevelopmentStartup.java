package lt.macrosoft.setup;

import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

import javax.enterprise.inject.Alternative;
import javax.inject.Inject;

import lt.macrosoft.daos.MemberDAO;
import lt.macrosoft.daos.ReservationDAO;
import lt.macrosoft.daos.ParameterDAO;
import lt.macrosoft.daos.SummerhouseDAO;
import lt.macrosoft.entities.Member;
import lt.macrosoft.entities.Reservation;
import lt.macrosoft.entities.Parameter;
import lt.macrosoft.entities.Summerhouse;
import lt.macrosoft.entities.Summerhouse.District;
import lt.macrosoft.enums.Role;
import lt.macrosoft.utils.DateUtils;
import lt.macrosoft.utils.PasswordService;

@Alternative
public class DevelopmentStartup extends SystemStartup {
    
    /**
     * Logger
     */
    private static final Logger logger = Logger.getLogger("DevelopmentStartup");
    
    @Inject
    private  SummerhouseDAO summerhouseDao;
    @Inject
    private  MemberDAO memberDao;
    @Inject
    private ParameterDAO dao;
    @Inject
    ReservationDAO reservationDAO;
    /**
     * Initializes the system
     */
    @Override
    public void init() {
        Calendar calendar = Calendar.getInstance();
        Date date;
        Date[] dates;
        logger.info("Development startup initialized...");



        /************************************************
         * Summerhouses
         ***********************************************/

        // 1
        dates = DateUtils.generateDateDuration("2016-01-01", "2017-01-01");

        Summerhouse summerhouse = new Summerhouse();
        summerhouse.setDescription("Puikiausias vasarnamis");
        summerhouse.setDistrict(District.MOLETAI);
        summerhouse.setPrice(7);
        summerhouse.setNumberOfPlaces(8);
        summerhouse.setName("Ramybe");
        summerhouse.setDateFrom(dates[0]);
        summerhouse.setDateTo(dates[1]);
        summerhouse.setImageUrl("http://mstcontractors.com/mstc/images/stories/nice-house-main.jpg");
        summerhouseDao.saveIfNotExists(summerhouse);

        // 2
        dates = DateUtils.generateDateDuration("2016-03-01", "2017-06-01");
        Summerhouse summerhouse2 = new Summerhouse();
        summerhouse2.setDescription("Puikesnis vasarnamis");
        summerhouse2.setDistrict(District.SVENCIONYS);
        summerhouse2.setPrice(10);
        summerhouse2.setNumberOfPlaces(10);
        summerhouse2.setName("Kultas");
        summerhouse2.setDateFrom(dates[0]);
        summerhouse2.setDateTo(dates[0]);
        summerhouse2.setImageUrl("http://mstcontractors.com/mstc/images/stories/nice-house-main.jpg");
        summerhouseDao.saveIfNotExists(summerhouse2);


        /************************************************
         * Members
         ***********************************************/

        Member admin = new Member();
        admin.setEmail("admin@admin.com");
        admin.setName("admin");
        admin.setCreditAmount(100000);
        admin.setPassword(PasswordService.hashPassword("admin"));
        admin.setRole(Role.ADMIN);
        memberDao.saveIfNotExists(admin);

        // 1
        Member member = new Member();
        member.setEmail("test@test.com");
        member.setName("test");
        member.setCreditAmount(100);
        member.setPassword(PasswordService.hashPassword("test"));
        member.setRole(Role.FULLUSER);
        memberDao.saveIfNotExists(member);

        Member member2 = new Member();
        member.setEmail("test2@test.com");
        member.setName("test2");
        member.setCreditAmount(100);
        member.setPassword(PasswordService.hashPassword("test"));
        member.setRole(Role.FULLUSER);
        memberDao.saveIfNotExists(member);

        Member candidate = new Member();
        candidate.setEmail("can@can.com");
        candidate.setName("candidate");
        candidate.setCreditAmount(0);
        candidate.setPassword(PasswordService.hashPassword("candidate"));
        candidate.setRole(Role.CANDIDATE);
        memberDao.saveIfNotExists(candidate);

        Parameter parameter = new Parameter();
        parameter.setName("MEMBERSHIP_PRICE");
        parameter.setPvalue("50");
        dao.saveOrUpdate(parameter);
        parameter = new Parameter();
        parameter.setName("MAX_MEMBERS");
        parameter.setPvalue("200");
        dao.saveOrUpdate(parameter);
        parameter = new Parameter();
        parameter.setName("MAX_REGISTRATION_DAYS");
        parameter.setPvalue("14");
        dao.saveOrUpdate(parameter);
        parameter = new Parameter();
        parameter.setName("MINIMUM_RECOMMENDATIONS");
        parameter.setPvalue("2");
        dao.saveOrUpdate(parameter);
        parameter = new Parameter();
        parameter.setName("BIRTHDAY_REQUIRED");
        parameter.setPvalue("T");
        dao.saveOrUpdate(parameter);



        /************************************************
         * Reservations
         ***********************************************/

        // 1
        Reservation reservation = new Reservation();
        date = DateUtils.generateDate("2016-02-03");
        reservation.setDateStart(date);
        date = DateUtils.generateDate("2016-03-03");
        reservation.setDateEnd(date);

        reservation.setSummerhouse(summerhouse);
        reservation.setMember(member);
        reservationDAO.saveIfNotExists(reservation);

        // 2
        reservation = new Reservation();
        date = DateUtils.generateDate("2016-03-15");
        reservation.setDateStart(date);
        date = DateUtils.generateDate("2016-03-25");
        reservation.setDateEnd(date);

        reservation.setSummerhouse(summerhouse);
        reservation.setMember(member2);
        reservationDAO.saveIfNotExists(reservation);

        // 3
        reservation = new Reservation();
        date = DateUtils.generateDate("2016-02-10");
        reservation.setDateStart(date);
        date = DateUtils.generateDate("2016-02-20");
        reservation.setDateEnd(date);

        reservation.setSummerhouse(summerhouse2);
        reservation.setMember(member);
        reservationDAO.saveIfNotExists(reservation);


    }
}
