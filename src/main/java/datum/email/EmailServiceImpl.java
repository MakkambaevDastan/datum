package datum.email;

// Java Program to Illustrate Creation Of
// Service implementation class

//package com.SpringBootEmail.service;

// Importing required classes
//import com.SpringBootEmail.Entity.EmailDetails;

import java.io.File;
//import javax.mail.MessagingException;
//import javax.mail.internet.MimeMessage;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.*;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

// Annotation
@Service
//@AllArgsConstructor
//@NoArgsConstructor
// Class
// Implementing EmailService interface
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private TemplateEngine templateEngine;
    @Value("${spring.mail.username}")
    private String sender;

    public String sendSimpleMail(EmailDetails details, String mainLink, String link) {

        try {
//            Context context = new Context();
//            context.setVariable("mainLink", mainLink);
//            context.setVariable("link", link);
//
//            String process = templateEngine.process("message", context);
//            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
//        helper.setSubject("Welcome " + user.getName());
//        helper.setText(process, true);
//        helper.setTo(user.getEmail());
//            javaMailSender.send(mimeMessage);
//            return "Sent";

            var mailMessage = new SimpleMailMessage();

            mailMessage.setFrom(sender);
            mailMessage.setTo(details.getRecipient());
            mailMessage.setText(link);
            mailMessage.setSubject(details.getSubject());

            javaMailSender.send(mailMessage);
            return "Успешно отправлен на почту "+details.getRecipient();
//        } catch (MailException e) {
//            return "Ощибка отправки" + e;
        } catch (MailParseException e){
            return "сбой при разборе сообщения "+e;
        }catch (MailAuthenticationException e){
            return "сбой аутентификации "+e;
        }catch (MailSendException e){
            return "сбой при отправке сообщения "+e;
        }
//        MailParseException – в случае сбоя при разборе сообщения
//        MailAuthenticationException – в случае сбоя аутентификации
//        MailSendException – в случае сбоя при отправке сообщения


    }


    public String
    sendMailWithAttachment(EmailDetails details) {
        // Creating a mime message
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;

        try {

            // Setting multipart as true for attachments to
            // be send
            mimeMessageHelper
                    = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(details.getRecipient());
            mimeMessageHelper.setText(details.getMsgBody());
            mimeMessageHelper.setSubject(
                    details.getSubject());

            // Adding the attachment
            FileSystemResource file
                    = new FileSystemResource(
                    new File(details.getAttachment()));

            mimeMessageHelper.addAttachment(
                    file.getFilename(), file);

            // Sending the mail
            javaMailSender.send(mimeMessage);
            return "Mail sent Successfully";
        }

        // Catch block to handle MessagingException
        catch (MessagingException e) {

            // Display message when exception occurred
            return "Error while sending mail!!!";
        }
    }

//    public String buildConfirmMessage(String mainLink, String link) {
//        return "k";
//    }
}
