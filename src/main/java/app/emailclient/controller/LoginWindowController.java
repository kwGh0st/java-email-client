package app.emailclient.controller;

import app.emailclient.EmailManager;
import app.emailclient.controller.services.LoginService;
import app.emailclient.model.EmailAccount;
import app.emailclient.view.ViewFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginWindowController extends BaseController {
    @FXML
    private TextField addressField;

    @FXML
    private Label addressLabel;

    @FXML
    private Label errorLabel;

    @FXML
    private Button loginButton;

    @FXML
    private TextField passwordField;

    @FXML
    private Label passwordLabel;


    public LoginWindowController(ViewFactory viewFactory, EmailManager emailManager, String fxmlName) {
        super(viewFactory, emailManager, fxmlName);
    }

    @FXML
    public void onLoginButtonAction() {
        if (areFieldsValid()) {
            EmailAccount emailAccount = new EmailAccount(addressField.getText(), passwordField.getText());
            LoginService loginService = new LoginService(emailManager, emailAccount);
            loginService.start();
            loginService.setOnSucceeded(event -> {
                EmailLoginResult emailLoginResult = loginService.getValue();
                System.out.println(emailLoginResult);
                if (emailLoginResult == EmailLoginResult.SUCCESS) {
                    viewFactory.showMainWindow();
                    Stage stage = (Stage) loginButton.getScene().getWindow();
                    viewFactory.closeStage(stage);
                }
            });

        }
    }

    private boolean areFieldsValid() {
        if (addressField.getText().isEmpty()) {
            errorLabel.setText("Fill address field!");
            return false;
        }

        if (passwordField.getText().isEmpty()) {
            errorLabel.setText("Fill password field!");
            return false;
        }

        return true;
    }
}
