package app.emailclient.model;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EmailMessage {

    private final SimpleStringProperty subject;
    private final SimpleStringProperty sender;
    private final SimpleStringProperty recipient;
    private final SimpleObjectProperty<SizeInteger> size;
    private final SimpleObjectProperty<Date> date;
    private boolean isRead;
    private final Message message;
    private final List<MimeBodyPart> attachments = new ArrayList<>();
    private boolean hasAttachments = false;

    public EmailMessage(String subject, String sender, String recipient, int size, Date date, boolean isRead, Message message) {
        this.subject = new SimpleStringProperty(subject);
        this.sender = new SimpleStringProperty(sender);
        this.recipient = new SimpleStringProperty(recipient);
        this.size = new SimpleObjectProperty<>(new SizeInteger(size));
        this.date = new SimpleObjectProperty<>(date);
        this.isRead = isRead;
        this.message = message;
    }

    public String getSubject() {
        return this.subject.get();
    }

    public String getSender() {
        return this.sender.get();
    }

    public String getRecipient() {
        return this.recipient.get();
    }

    public SizeInteger getSize() {
        return this.size.get();
    }

    public Date getDate() {
        return this.date.get();
    }

    public boolean isRead() {
        return this.isRead;
    }

    public Message getMessage() {
        return this.message;
    }

    public void setRead(boolean isRead) {
        this.isRead = isRead;
    }

    public List<MimeBodyPart> getAttachments() {
        return attachments;
    }

    public boolean isHasAttachments() {
        return hasAttachments;
    }

    public void addAttachment(MimeBodyPart mbp) throws MessagingException {
        attachments.add(mbp);
        hasAttachments = true;
        System.out.println("Added attachment: " + mbp.getFileName());
    }
}
