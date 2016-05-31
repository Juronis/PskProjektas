package lt.macrosoft.interceptors;

import org.apache.log4j.Logger;

import com.nimbusds.jose.JOSEException;

import lt.macrosoft.daos.MemberDAO;
import lt.macrosoft.entities.Member;
import lt.macrosoft.utils.AuthUtils;

import java.text.ParseException;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

@Interceptor
public class LoggingIntercept {

	@Inject
	MemberDAO memberDao;

	@AroundInvoke
	private Object doLog(InvocationContext ic) throws Exception{
		Object obj = null;
			String methodName = ic.getMethod().getName();
			String className = ic.getTarget().getClass().getName();
			Object[] parameters = ic.getParameters();

			/* Find logger */
			Logger log = Logger.getLogger(className);
			HttpServletRequest request = null;
			for (Object e : parameters) {
				if (e instanceof HttpServletRequest) {
					request = (HttpServletRequest) e;
					break;
				}
			}
			String usernameAndRole = "";
			if (request != null) {
				String userId = null;
				try {
					userId = AuthUtils.getSubject(request.getHeader(AuthUtils.AUTH_HEADER_KEY));
				} catch (ParseException | JOSEException e) {
					e.printStackTrace();
				}
				if (userId != null) {
					Member member = memberDao.findById(Long.parseLong(userId));
					usernameAndRole = "User with Email: " + member.getEmail() + " and id " + member.getId();
				}
			}
			usernameAndRole = usernameAndRole + " called method: " + methodName + " in class: " + className;
			log.info(usernameAndRole);
			return ic.proceed();
	}

}
