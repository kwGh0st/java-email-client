package app.emailclient.controller;

import app.emailclient.EmailManager;
import app.emailclient.controller.services.EmailSenderService;
import app.emailclient.model.EmailAccount;
import app.emailclient.view.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.web.HTMLEditor;

import java.net.URL;
import java.util.ResourceBundle;

public class ComposeMessageController extends BaseController implements Initializable {
    @FXML
    private Label errorLabel;

    @FXML
    private HTMLEditor htmlEditor;

    @FXML
    private TextField recipientTxtField;

    @FXML
    private Button sendButton;

    @FXML
    private TextField subjectTxtField;

    @FXML
    private ChoiceBox<EmailAccount> accountChoiceBx;

    public ComposeMessageController(ViewFactory viewFactory, EmailManager emailManager, String fxmlName) {
        super(viewFactory, emailManager, fxmlName);
    }

    @FXML
    void setOnSendButtonAction() {
        EmailSenderService emailSenderService = new EmailSenderService(
                accountChoiceBx.getValue(),
                subjectTxtField.getText(),
                recipientTxtField.getText(),
                htmlEditor.getHtmlText()
        );

        emailSenderService.start();
        emailSenderService.setOnSucceeded(e -> {
            EmailSendingResult emailSendingResult = emailSenderService.getValue();

            switch (emailSendingResult) {
                case SUCCESS -> errorLabel.setText("Message Send!");
                case FAILED_BY_PROVIDER -> errorLabel.setText("Sending message failed by provider.");
                case FAILED_BY_UNEXPECTED_ERROR -> errorLabel.setText("An unexpected error occurred while sending");

            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        accountChoiceBx.setItems(emailManager.getAccounts());
        accountChoiceBx.setValue(emailManager.getAccounts().get(0));
    }
}
