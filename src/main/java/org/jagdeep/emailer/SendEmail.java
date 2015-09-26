/**
 * @author jagdeepjain
 *
 */
package org.jagdeep.emailer;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@SuppressWarnings("serial")
public class SendEmail extends HttpServlet {
    
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                
        EmailerProperties emailerProperties = new EmailerProperties();
        final HashMap<String, String> properties = emailerProperties.getProperties();
        PrintWriter out = response.getWriter();
        Properties props = new Properties();
        
        File emailList = new File(properties.get("emailReceiver"));
        File validEmails = new File(properties.get("emailValid"));
        File invalidEmails = new File(properties.get("emailInvalid"));

        props.put("mail.smtp.host", properties.get("mailSmtpHost"));
        props.put("mail.smtp.socketFactory.port", properties.get("mailSmtpSocketFactoryPort"));
        props.put("mail.smtp.socketFactory.class", properties.get("mailSmtpSocketFactoryClass"));
        props.put("mail.smtp.auth", properties.get("mailSmtpAuth"));
        props.put("mail.smtp.port", properties.get("mailSmtpPort"));
        
        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(properties.get("userName"),
                                properties.get("password"));
                    }
                });
        
        response.setContentType("text/html");
        
        try {
            Message message = new MimeMessage(session);
            Multipart multipart = new MimeMultipart();
            
            message.setFrom(new InternetAddress(properties.get("sentFrom")));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("makaanhunt@yahoo.com"));
            message.setSubject(properties.get("emailSubject"));
            
            // body message
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(properties.get("emailBodyMessage"), "text/html");
            multipart.addBodyPart(messageBodyPart);
            
            // body image
            messageBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(properties.get("emailBannerPath"));
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(properties.get("emailBannerPath"));
            multipart.addBodyPart(messageBodyPart);
            
            // body footer
            messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(properties.get("emailFooter"), "text/html");
            multipart.addBodyPart(messageBodyPart);
            
            // setting content of email
            message.setContent(multipart);
            // sending the message
            Transport.send(message);
            out.println("\n");
            out.println("Message sent successfully !");
            
        } catch (MessagingException me) {
            me.printStackTrace();
        }
    }
    
}
