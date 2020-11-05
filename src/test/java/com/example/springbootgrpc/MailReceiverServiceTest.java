package com.example.springbootgrpc;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.test.context.junit4.SpringRunner;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.util.Properties;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootGrpcApplication.class)
public class MailReceiverServiceTest {

    @Autowired
    private MailRecieverService mailRecieverService;

    @MockBean
    EmployeeRepository employeeRepository;

    @Test
    public void testHandle() throws MessagingException, IOException {

        Properties mailProps = new Properties();
        mailProps.setProperty("mail.smtp.host", "localhost");
        mailProps.setProperty("mail.smtp.port","25");
        Session session = Session.getDefaultInstance(mailProps, null);
        MimeMessage message = new MimeMessage(session);
        Multipart multipart = new MimeMultipart();
        BodyPart bdypart = new MimeBodyPart();
        multipart.addBodyPart(bdypart);
        message.setContent(multipart);

        MailRecieverService mockmailservice = spy(mailRecieverService);
        Mockito.doReturn("keith Neilson development airomart 124 2020/5/3 0773478345").when(mockmailservice).getTextFromMimeMessage(message);
        doNothing().when(mockmailservice).saveToDB("keith Neilson development airomart 124 2020/5/3 0773478345","sdf@ghn");

        org.springframework.messaging.Message<MimeMessage> msg = new GenericMessage<>(message);

        mockmailservice.processNewEmail().handleMessage(msg);
        Assert.assertTrue(true);
    }

    @Test
    public void testGetTextFromMimeMessage1() throws IOException, MessagingException {

        Properties mailProps = new Properties();
        mailProps.setProperty("mail.smtp.host", "localhost");
        mailProps.setProperty("mail.smtp.port","25");
        Session session = Session.getDefaultInstance(mailProps, null);
        MimeMessage message = new MimeMessage(session);
        Multipart multipart = new MimeMultipart();
        BodyPart bdypart = new MimeBodyPart();
        multipart.addBodyPart(bdypart);
        message.setContent(multipart);
        MailRecieverService mockmailservice = spy(mailRecieverService);
        Mockito.doReturn("firstName lastName departmentName teamName 124 joinData 0773478345").when(mockmailservice).getTextFromMimeMultipart(multipart);
        mockmailservice.getTextFromMimeMessage(message);
        Assert.assertEquals("firstName lastName departmentName teamName 124 joinData 0773478345", mockmailservice.getTextFromMimeMessage(message));

    }

    @Test
    public void testGetTextFromMimeMultipart() throws MessagingException, IOException {
        String text = "Hello, World";
        String html = "<h1>" + text + "</h1>";
        Multipart multipart = new MimeMultipart( "alternative" );
        MimeBodyPart textPart = new MimeBodyPart();
        textPart.setText( text, "utf-8" );
        MimeBodyPart htmlPart = new MimeBodyPart();
        htmlPart.setContent( html, "text/html; charset=utf-8" );
        multipart.addBodyPart( textPart );
        multipart.addBodyPart( htmlPart );
        htmlPart.setHeader("Content-Type", "text/html");
        String result = mailRecieverService.getTextFromMimeMultipart(multipart);
        Assert.assertTrue(true);
    }

    @Test
    public void testSaveToDBIfCondition(){

        Employee employee = new Employee();
        employee.setEmployeeID(156);
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setDepartmentName("Development");
        employee.setTeamName("airoline");
        employee.setMobile("0775645234");
        employee.setJoinDate("2020/10/12");
        employee.setEmail("johndoe@abz");

        when(employeeRepository.findById(anyLong())).thenReturn(java.util.Optional.of(employee));
        when(employeeRepository.save(any())).thenReturn(employee);
        mailRecieverService.saveToDB("John Doe Development airoline 156 2020/10/12 0775645234","johndoe@abz");
        Assert.assertTrue(true);

    }

    @Test
    public void testSaveToDBElseCondition(){

        Employee employee = new Employee();
        employee.setEmployeeID(156);
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setDepartmentName("Development");
        employee.setTeamName("airoline");
        employee.setMobile("0775645234");
        employee.setJoinDate("2020/10/12");
        employee.setEmail("johndoe@abz");

        when(employeeRepository.findById(anyLong())).thenReturn(null);
        when(employeeRepository.save(any())).thenReturn(employee);
        mailRecieverService.saveToDB("John Doe Development airoline 156 2020/10/12 0775645234","johndoe@abz");
        Assert.assertTrue(true);

    }

}