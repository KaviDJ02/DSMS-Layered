package com.drivesmart.dsms.util;

import io.github.cdimascio.dotenv.Dotenv;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

public class JavaMailUtil {

    private static final Dotenv dotenv = Dotenv.load();
    private static final String from = "kdjtec02@gmail.com"; // Sender's email address

    public static void sendMail(String recipient, String subject, String htmlContent) throws MessagingException {
        // Set up mail server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.mailgun.org");
        properties.put("mail.smtp.port", "587");

        // Load email and password from environment variables
        String myAccountEmail = dotenv.get("EMAIL_USERNAME");
        String password = dotenv.get("EMAIL_PASSWORD");

        // Create a session with an authenticator
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountEmail, password);
            }
        });

        // Compose the HTML message
        Message message = prepareMessage(session, from, recipient, subject, htmlContent);

        // Send the message
        Transport.send(message);
        System.out.println("Email sent successfully");
    }

    private static Message prepareMessage(Session session, String from, String recipient, String subject, String htmlContent) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject(subject);

            // Set the content as HTML
            message.setContent(htmlContent, "text/html; charset=utf-8");

            return message;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void sendMailWithAttachment(String recipient, String subject, String htmlContent, String attachmentPath) throws MessagingException {
        // Set up mail server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.mailgun.org");
        properties.put("mail.smtp.port", "587");

        // Load email and password from environment variables
        String myAccountEmail = dotenv.get("EMAIL_USERNAME");
        String password = dotenv.get("EMAIL_PASSWORD");

        // Create a session with an authenticator
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountEmail, password);
            }
        });

        try {
            // Create a new email message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject(subject);

            // Create a multipart message for attachment
            Multipart multipart = new MimeMultipart();

            // Part one is the HTML content
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(htmlContent, "text/html; charset=utf-8");
            multipart.addBodyPart(messageBodyPart);

            // Part two is the attachment
            messageBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(attachmentPath);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(attachmentPath);
            multipart.addBodyPart(messageBodyPart);

            // Set the complete message parts
            message.setContent(multipart);

            // Send the message
            Transport.send(message);
            System.out.println("Email sent successfully with attachment");
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new MessagingException("Failed to send email with attachment", e);
        }
    }
}