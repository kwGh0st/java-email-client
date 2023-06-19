package app.emailclient.controller;

import app.emailclient.EmailManager;
import app.emailclient.controller.services.MessageRenderService;
import app.emailclient.model.EmailMessage;
import app.emailclient.view.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebView;

import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import java.net.URL;
import java.util.ResourceBundle;

public class MessageDetailsController extends BaseController implements Initializable {
    @FXML
    private HBox attachments;

    @FXML
    private WebView detailsWebView;

    @FXML
    private Label senderLabel;

    @FXML
    private Label subjectLabel;

    private String locationOfDownloads = System.getProperty("user.home") + "/Downloads/";


    public MessageDetailsController(ViewFactory viewFactory, EmailManager emailManager, String fxmlName) {
        super(viewFactory, emailManager, fxmlName);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        EmailMessage emailMessage = emailManager.getSelectedMessage();
        senderLabel.setText(emailMessage.getSender());
        subjectLabel.setText(emailMessage.getSubject());
        try {
            loadAttachments(emailMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        MessageRenderService messageRenderService = new MessageRenderService(detailsWebView.getEngine());
        messageRenderService.setEmailMessage(emailMessage);
        messageRenderService.restart();
    }

    private void loadAttachments(EmailMessage emailMessage) throws MessagingException {

        if (emailMessage.isHasAttachments()) {
            for (MimeBodyPart mimeBodyPart : emailMessage.getAttachments()) {
                Button button = new Button(mimeBodyPart.getFileName());
                attachments.getChildren().add(button);
            }
        }
    }
}
