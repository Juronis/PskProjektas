package lt.macrosoft.beans;

import com.nimbusds.jose.JOSEException;
import lt.macrosoft.daos.MemberDAO;
import lt.macrosoft.entities.Member;
import lt.macrosoft.utils.AuthUtils;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.text.ParseException;

/**
 * Created by Arnas on 2016-05-27.
 */
@Stateless(name = "MemberStatelessEJB")
public class MemberStatelessBean {
    @Inject
    MemberDAO memberDAO;

    public MemberStatelessBean() {
    }

    public Member getMember(String authHeader) {
        String userId = null;
        try {
            userId = AuthUtils.getSubject(authHeader);
        } catch (ParseException | JOSEException e) {
            e.printStackTrace();
        }
        if (userId == null)
            return null;

        return memberDAO.findById(Long.getLong(userId));
    }
}
