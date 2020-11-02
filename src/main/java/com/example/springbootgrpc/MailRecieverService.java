package com.example.springbootgrpc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;

@Service
public class MailRecieverService {

//    private List<EmailAction> services;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Bean
    @ServiceActivator(inputChannel = "pop3Channel")
    public MessageHandler processNewEmail() {
        MessageHandler messageHandler = new MessageHandler() {

            @Override
            public void handleMessage(org.springframework.messaging.Message<?> message) throws MessagingException {
                String y=message.toString();
//                System.out.println("New email:" + y);

               Object messagePayload =message.getPayload();
               MimeMessage msg = (MimeMessage)messagePayload;
//                System.out.println(msg);
                try {
                    //get content of the email
                    String result = getTextFromMimeMessage(msg);

                    //get email address of the sender
                    Address[] froms = msg.getFrom();
                    String email = froms == null ? null
                            : ((InternetAddress) froms[0]).getAddress();

//                    System.out.println(email);
//                    System.out.println(result);

                    //saving data into database
                    saveToDB(result, email);
                } catch (javax.mail.MessagingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        return messageHandler;
    }

    public String getTextFromMimeMessage(MimeMessage message) throws javax.mail.MessagingException, IOException {
        String result = "";
        if (message.isMimeType("multipart/*")) {
            MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
            result = getTextFromMimeMultipart(mimeMultipart);
        }
        return result;
    }

    public String getTextFromMimeMultipart(Multipart msg) throws MessagingException, IOException, javax.mail.MessagingException {
        String result = "";
        int count = msg.getCount();
        for (int i = 0; i < count; i++) {
            BodyPart bodyPart = msg.getBodyPart(i);
            if (bodyPart.isMimeType("text/html")) {
                String html = (String) bodyPart.getContent();
                result = result + "\n" + org.jsoup.Jsoup.parse(html).text();
            }
        }
        return result;

    }

    public void saveToDB(String result, String email){

        //remove hidden \n, \r before splitting
        result = result.replaceAll("[\n\r]", "");
        //split the result with space
        //assumptions:
        //email format - firstName lastName departmentName teamName employeeID joinData mobile
        String[] splitted = result.split(" ");

        com.example.springbootgrpc.Employee employee = new Employee();
        employee.setFirstName(splitted[0]);
        employee.setLastName(splitted[1]);
        employee.setDepartmentName(splitted[2]);
        employee.setTeamName(splitted[3]);
        employee.setEmployeeID(Long.parseLong(splitted[4]));
        employee.setJoinDate(splitted[5]);
        employee.setMobile(splitted[6]);

        employee.setEmail(email);
        employeeRepository.save(employee);
    }


}
