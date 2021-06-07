package server.restservice.service;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import server.restservice.models.Employee;
import server.restservice.repository.EmployeeRepository;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

@Service
@NoArgsConstructor
public class MailService {

    @Autowired
    private EmployeeRepository employeeRepository;

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

    private String getDay(int day) {
        switch (day) {
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

    private String mailText(String username, int day) {
        return "Hello,\n" + username + " wanted to invite you to arrive to the office on next " + getDay(day);
    }

    public void sendEmail(String username, int day, String[] mails) {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(mails);

        msg.setSubject("Schedule Arrival Day");
        Employee emp = employeeRepository.findEmployeeByUsername(username);
        emp.readlock();
        msg.setText(mailText(emp.getName(), day));
        emp.readunlock();

        javaMailSender.send(msg);
    }

    private String assignmentText(List<Long> days) {
        String output = "Hello,\n";
        if (days.size() == 0) {
            output += "We are sorry to inform you weren't allocated in the office on any day for the coming week,\n";
        } else {
            output += "Your were allocated in the office in the coming week on the following days:\n";
            for (Long day : days) {
                output += ("\t* " + getDay(day.intValue()) + "\n");
            }
        }
        output += "Have a good week";
        return output;
    }

    public void notifyAboutAssignment(Map<String, List<Long>> assignment) {
        for (Entry<String, List<Long>> entry : assignment.entrySet()) {
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setTo(entry.getKey());
            msg.setSubject("Next week assignments");
            msg.setText(assignmentText(entry.getValue()));
            javaMailSender.send(msg);
        }
    }
}
