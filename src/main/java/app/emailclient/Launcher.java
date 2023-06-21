package app.emailclient;

import app.emailclient.controller.persistence.PersistenceAccess;
import app.emailclient.controller.persistence.ValidAccount;
import app.emailclient.controller.services.LoginService;
import app.emailclient.model.EmailAccount;
import app.emailclient.view.ViewFactory;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Launcher extends Application {
    private PersistenceAccess persistenceAccess = new PersistenceAccess();
    private EmailManager emailManager = new EmailManager();
    @Override
    public void start(Stage stage) {
        ViewFactory viewFactory = new ViewFactory(emailManager);
        List<ValidAccount> persistence = persistenceAccess.loadFromPersistence();
        if (persistence.size() > 0) {
            for (ValidAccount account : persistence) {
                viewFactory.showMainWindow();
                EmailAccount emailAccount = new EmailAccount(account.getAddress(), account.getPassword());
                LoginService loginService = new LoginService(emailManager, emailAccount);
                loginService.start();
            }
        } else {
            viewFactory.showLoginWindow();
        }
    }

    @Override
    public void stop() throws Exception {
        List<ValidAccount> validAccountList = new ArrayList<>();
        for (EmailAccount account : emailManager.getAccounts()) {
            validAccountList.add(new ValidAccount(account.getName(), account.getPassword()));
        }

        persistenceAccess.saveToPersistence(validAccountList);
    }

    public static void main(String[] args) {
        launch();
    }
}