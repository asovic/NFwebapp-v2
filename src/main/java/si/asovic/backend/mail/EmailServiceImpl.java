package si.asovic.backend.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service("EmailService")
public class EmailServiceImpl implements EmailService {
	
    @Autowired
    public JavaMailSender emailSender;
 
    public void sendMessage(
      String to, String subject, String text) {
       try {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("asovic@t-2.si");
        message.setTo(to); 
        message.setSubject(subject); 
        message.setText(text);
        emailSender.send(message);
       } catch (MailException exception) {
           exception.printStackTrace();
       }
    }
}