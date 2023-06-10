package app.emailclient.controller;

import app.emailclient.EmailManager;
import app.emailclient.view.ViewFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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

    public LoginWindowController(){}

    public LoginWindowController(ViewFactory viewFactory, EmailManager emailManager, String fxmlName) {
        super(viewFactory, emailManager, fxmlName);
    }
}
