package loger;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.PasswordAuthentication;

import example.Student;

public class MailLogger implements Logger {

	final String username = "testowy126@gmail.com";
	final String password = "testowy33djbr";
	
	@Override
	public void log(String status, Student student) {
		

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });
		
		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse("lukasz.r00@gmail.com"));
			message.setSubject("Crawler Notification");
			message.setText(status + " " + student.toString());

			Transport.send(message);


		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

}
