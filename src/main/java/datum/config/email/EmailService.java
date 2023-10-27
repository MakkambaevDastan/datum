package datum.config.email;

import jakarta.mail.MessagingException;

public interface EmailService {
    Boolean sendSimpleMail(EmailDetails details);
    Boolean sendMailWithAttachment(EmailDetails details) throws MessagingException;
}