package lt.macrosoft.mail;

import java.text.DateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Named
@Stateless
public class MailerBean {
	private static final Logger logger = Logger.getLogger(MailerBean.class.getName());
    private Session session;
	final String username = "psk.macrosoft@gmail.com";
	final String password = "varma-KEPASA";

	@Asynchronous
    public Future<MailStatus> sendMessage(String email) {
        MailStatus status;

        try {
            Properties properties = new Properties();
            properties.put("mail.smtp.auth", "true");
    		properties.put("mail.smtp.starttls.enable", "true");
    		properties.put("mail.smtp.host", "smtp.gmail.com");
    		properties.put("mail.smtp.port", "587");
            session = Session.getInstance(properties,
          		  new javax.mail.Authenticator() {
    			protected PasswordAuthentication getPasswordAuthentication() {
    				return new PasswordAuthentication(username, password);
    			}
    		  });            
            Message message = new MimeMessage(session);
            message.setFrom();
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(email, false));
            message.setSubject("Test message from async example");
            message.setHeader("X-Mailer", "JavaMail");
            DateFormat dateFormatter = DateFormat
                    .getDateTimeInstance(DateFormat.LONG, DateFormat.SHORT);
            Date timeStamp = new Date();
            String messageBody = "This is a test message from the async "
                    + "example of the Java EE Tutorial. It was sent on "
                    + dateFormatter.format(timeStamp)
                    + ".";
            message.setText(messageBody);
            message.setSentDate(timeStamp);
            Transport.send(message);
            status = MailStatus.SENT;
            logger.log(Level.INFO, "Mail sent to {0}", email);
        } catch (MessagingException ex) {
            logger.severe("Error in sending message.");
            status = MailStatus.NOT_SENT;
            logger.severe(ex.getMessage());
        }
        return new AsyncResult<>(status);
	}
}
