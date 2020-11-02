package com.example.springbootgrpc;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.integration.mail.AbstractMailReceiver;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.MalformedInputException;
import java.util.Properties;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.spy;

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
        message.setContent(multipart);

        MailRecieverService mockmailservice = spy(mailRecieverService);
        Mockito.doReturn("firstName lastName departmentName teamName 124 joinData 0773478345").when(mockmailservice).getTextFromMimeMessage(message);
        Mockito.doNothing().when(mockmailservice).saveToDB("firstName lastName departmentName teamName 123 joinData 0773478345","sdf@ghn");

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

        MailRecieverService mockmailservice = spy(mailRecieverService);
        Mockito.doReturn("firstName lastName departmentName teamName 124 joinData 0773478345").when(mockmailservice).getTextFromMimeMessage(message);
        mockmailservice.getTextFromMimeMessage(message);
    }

    @Test
    public void testGetTextFromMimeMultipart(){
        Properties mailProps = new Properties();
        mailProps.setProperty("mail.smtp.host", "localhost");
        mailProps.setProperty("mail.smtp.port","25");
        Session session = Session.getDefaultInstance(mailProps, null);
        MimeMessage message = new MimeMessage(session);

    }
}
