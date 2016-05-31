package lt.macrosoft.beans;

import com.google.common.base.Optional;
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

    public Optional<Member> getMember(String authHeader) throws ParseException, JOSEException {

        String userId = AuthUtils.getSubject(authHeader);
        if (userId.equals("0")) {
            return Optional.absent();
        } else {

            return memberDAO.getMemberById(Long.parseLong(userId));
        }
    }
}
