package app.emailclient.controller.services;

import app.emailclient.controller.EmailSendingResult;
import app.emailclient.model.EmailAccount;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.util.List;

public class EmailSenderService extends Service<EmailSendingResult> {
    private final EmailAccount emailAccount;
    private final String subject;
    private final String recipient;
    private final String content;
    private final List<File> attachments;

    public EmailSenderService(EmailAccount emailAccount, String subject, String recipient, String content, List<File> attachments) {
        this.emailAccount = emailAccount;
        this.subject = subject;
        this.recipient = recipient;
        this.content = content;
        this.attachments = attachments;
    }

    @Override
    protected Task<EmailSendingResult> createTask() {
        return new Task<EmailSendingResult>() {
            @Override
            protected EmailSendingResult call() {
                try {
                    MimeMessage message = new MimeMessage(emailAccount.getSession());
                    message.setFrom(emailAccount.getName());
                    message.addRecipients(Message.RecipientType.TO, recipient);
                    message.setSubject(subject);
                    //Set the content
                    Multipart multipart = new  MimeMultipart();
                    BodyPart bodyPart = new MimeBodyPart();
                    bodyPart.setContent(content, "text/html");
                    multipart.addBodyPart(bodyPart);
                    message.setContent(multipart);
                    //Adding attachments
                    if (attachments.size() > 0) {
                        for (File file : attachments) {
                            MimeBodyPart mimeBodyPart = new MimeBodyPart();
                            DataSource dataSource = new FileDataSource(file.getAbsolutePath());
                            mimeBodyPart.setDataHandler(new DataHandler(dataSource));
                            mimeBodyPart.setFileName(file.getName());
                            multipart.addBodyPart(mimeBodyPart);
                        }
                    }
                    //Sending the message
                    Transport transport = emailAccount.getSession().getTransport();
                    transport.connect(
                            emailAccount.getProperties().getProperty("outgoingHost"),
                            emailAccount.getName(),
                            emailAccount.getPassword());
                    transport.sendMessage(message, message.getAllRecipients());
                    transport.close();
                    return EmailSendingResult.SUCCESS;

                } catch (MessagingException e) {
                    return EmailSendingResult.FAILED_BY_PROVIDER;
                } catch (Exception ex) {
                    return EmailSendingResult.FAILED_BY_UNEXPECTED_ERROR;
                }
            }
        };
    }
}
