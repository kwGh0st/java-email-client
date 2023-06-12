package app.emailclient.controller.services;

import app.emailclient.EmailManager;
import app.emailclient.controller.EmailLoginResult;
import app.emailclient.model.EmailAccount;

import javax.mail.*;

public class LoginService {
    private EmailManager emailManager;
    private EmailAccount emailAccount;

    public LoginService(EmailManager emailManager, EmailAccount emailAccount) {
        this.emailManager = emailManager;
        this.emailAccount = emailAccount;
    }

    public EmailLoginResult login() {
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailAccount.getName(), emailAccount.getPassword());
            }
        };

        try {
            Session session = Session.getInstance(emailAccount.getProperties(), authenticator);
            Store store = session.getStore("imaps");
            store.connect(emailAccount.getProperties().getProperty("incomingHost"), emailAccount.getName(), emailAccount.getPassword());
            emailAccount.setStore(store);
        } catch (AuthenticationFailedException ex) {
            ex.printStackTrace();
            return EmailLoginResult.FAILED_BY_CREDENTIALS;
        } catch (NoSuchProviderException ex) {
            ex.printStackTrace();
            return EmailLoginResult.FAILED_BY_NETWORK;
        } catch (Exception ex) {
            ex.printStackTrace();
            return EmailLoginResult.UNEXPECTED_ERROR;
        }
        return EmailLoginResult.SUCCESS;
    }
}
