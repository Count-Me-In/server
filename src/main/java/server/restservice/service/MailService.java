package server.restservice.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
@NoArgsConstructor
public class MailService{

    private JavaMailSender javaMailSender = getJavaMailSender();

    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername("countmeintheoffice@gmail.com");
        mailSender.setPassword("SNSN9397");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }


    private String getDay(int day){
        switch(day){
            case 0:
                return "Sunday";
            case 1:
                return "Monday";
            case 2:
                return "Tuesday";
            case 3:
                return "Wednesday";
            case 4:
                return "Thursday";
            default:
                return "";
        }
    }

    private String mailText(String username, int day){
        return "Hello,\n" +
                username + " wanted to invite you to arrive to the office on next " + getDay(day);
    }

    public void sendEmail(String username, int day, String[] mails) {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(mails);

        msg.setSubject("Schedule Arrival Day");
        //TODO: get employees name instead of username from db
        msg.setText(mailText(username, day));

        javaMailSender.send(msg);
    }
}
