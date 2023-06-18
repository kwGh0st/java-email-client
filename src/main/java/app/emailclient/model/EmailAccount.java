package app.emailclient.model;

import javax.mail.Store;
import java.util.Properties;

public class EmailAccount {
    private final String name;
    private final String password;
    private final Properties properties;
    private Store store;

    public EmailAccount(String name, String password) {
        this.name = name;
        this.password = password;
        properties = new Properties();

        properties.put("incomingHost", "imap.wp.pl");
        properties.put("mail.store.protocol", "imaps");

        properties.put("mail.transport.protocol", "smtps");
        properties.put("mail.smtps.host", "smtp.wp.pl");
        properties.put("mail.smtps.auth", "true");
        properties.put("outgoingHost", "smtp.wp.pl");
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

    @Override
    public String toString() {
        return name;
    }
}
