package lt.macrosoft.interceptors;

import java.util.logging.Logger;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

public class LoggingIntercept {
   @Inject
   private Logger logger;

   @AroundInvoke
   private Object doLog(InvocationContext ic) {
      Object obj = null;
      try {
         logger.entering(ic.getTarget().toString(),
            ic.getMethod().getName());
         obj = ic.proceed();
      } catch (Exception ex) {

      } finally {
         logger.exiting(ic.getTarget().toString(),
            ic.getMethod().getName());
      }
      return obj;
   }

}
