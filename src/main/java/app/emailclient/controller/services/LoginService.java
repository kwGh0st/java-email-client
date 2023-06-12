package app.emailclient.controller.services;

import app.emailclient.EmailManager;
import app.emailclient.model.EmailAccount;

public class LoginService {
    private EmailManager emailManager;
    private EmailAccount emailAccount;

    public LoginService(EmailManager emailManager, EmailAccount emailAccount) {
        this.emailManager = emailManager;
        this.emailAccount = emailAccount;
    }

}
