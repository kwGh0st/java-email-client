package app.emailclient.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import javax.mail.Message;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailMessage {

    private final SimpleStringProperty subject;
    private final SimpleStringProperty sender;
    private final SimpleStringProperty recipient;
    private final SimpleObjectProperty<SizeInteger> size;
    private final SimpleObjectProperty<Date> date;
    private boolean isRead;
    private final Message message;

    public EmailMessage(String subject, String sender, String recipient, int size, Date date, boolean isRead, Message message) {
        this.subject = new SimpleStringProperty(subject);
        this.sender = new SimpleStringProperty(sender);
        this.recipient = new SimpleStringProperty(recipient);
        this.size = new SimpleObjectProperty<SizeInteger>(new SizeInteger(size));
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
}
