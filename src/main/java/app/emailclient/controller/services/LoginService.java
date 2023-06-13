package app.emailclient.controller.services;

import app.emailclient.EmailManager;
import app.emailclient.controller.EmailLoginResult;
import app.emailclient.model.EmailAccount;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.mail.*;

public class LoginService extends Service<EmailLoginResult> {
    private final EmailManager emailManager;
    private final EmailAccount emailAccount;

    public LoginService(EmailManager emailManager, EmailAccount emailAccount) {
        this.emailManager = emailManager;
        this.emailAccount = emailAccount;
    }

    private EmailLoginResult login() {
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
            emailManager.addEmailAccount(emailAccount);
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

    @Override
    protected Task<EmailLoginResult> createTask() {
        return new Task<>() {
            @Override
            protected EmailLoginResult call() throws Exception {
                return login();
            }
        };
    }
}
