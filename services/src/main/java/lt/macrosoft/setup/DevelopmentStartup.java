package lt.macrosoft.setup;

import java.util.logging.Logger;

import javax.enterprise.inject.Alternative;
import javax.inject.Inject;

import lt.macrosoft.daos.MemberDAO;
import lt.macrosoft.daos.ParameterDAO;
import lt.macrosoft.daos.SummerhouseDAO;
import lt.macrosoft.entities.Member;
import lt.macrosoft.entities.Parameter;
import lt.macrosoft.entities.Summerhouse;
import lt.macrosoft.entities.Summerhouse.District;
import lt.macrosoft.enums.Role;
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
    /**
     * Initializes the system
     */
    @Override
    public void init() {
        logger.info("Development startup initialized...");
        Summerhouse firstSummerhouse = new Summerhouse();
        firstSummerhouse.setDescription("Puikiausias vasarnamis");
        firstSummerhouse.setDistrict(District.MOLETAI);
        firstSummerhouse.setPrice(7);
        firstSummerhouse.setNumberOfPlaces(8);
        firstSummerhouse.setName("Ramybe");
        firstSummerhouse.setDateFrom("2016-05-05");
        firstSummerhouse.setDateTo("2016-09-05");
        firstSummerhouse.setImageUrl("http://mstcontractors.com/mstc/images/stories/nice-house-main.jpg");
        summerhouseDao.saveIfNotExists(firstSummerhouse);
        
        Member admin = new Member();
        admin.setEmail("admin@admin.com");
        admin.setName("admin");
        admin.setCreditAmount(100000);;
        admin.setPassword(PasswordService.hashPassword("admin"));
        admin.setRole(Role.ADMIN);
        memberDao.saveIfNotExists(admin);
        
        Member member = new Member();
        member.setEmail("test@test.com");
        member.setName("test");
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
    }
}
