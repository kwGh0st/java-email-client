package app.emailclient.model;

import javax.mail.Store;
import java.util.Properties;

public class EmailAccount {
    private String name;
    private String password;
    private Properties properties;
    private Store store;

    public EmailAccount(String name, String password) {
        this.name = name;
        this.password = password;
        properties = new Properties();

        properties.put("incomingHost", "imap.gmail.com");
        properties.put("mail.store.protocol", "imaps");

        properties.put("mail.transport.protocol", "smtps");
        properties.put("mail.smtps.host", "smtp.gmail.com");
        properties.put("mail.smtps.auth", "true");
        properties.put("outgoingHost", "smtp.gmail.com");
    }

    public String getName() {
        return name;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public String getPassword() {
        return password;
    }

    public Properties getProperties() {
        return properties;
    }

    public Store getStore() {
        return store;
    }
}
