package app.emailclient.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LoginWindowController {
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
}
