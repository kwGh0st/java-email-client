package app.emailclient.controller.services;

import app.emailclient.model.EmailMessage;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.web.WebEngine;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import java.io.IOException;

public class MessageRenderService extends Service {
    private final WebEngine webEngine;
    private EmailMessage emailMessage;
    private final StringBuffer stringBuffer;

    public MessageRenderService(WebEngine webEngine) {
        this.webEngine = webEngine;
        this.stringBuffer = new StringBuffer();
        this.setOnSucceeded(event -> displayMessage());
    }

    public void setEmailMessage(EmailMessage emailMessage) {
        this.emailMessage = emailMessage;
    }

    private void displayMessage() {
        webEngine.loadContent(stringBuffer.toString());
    }


    @Override
    protected Task createTask() {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                loadMessage();
                return null;
            }
        };
    }

    private void loadMessage() throws MessagingException, IOException {
        stringBuffer.setLength(0);
        Message message = emailMessage.getMessage();
        String contentType = message.getContentType();

        if (isSimpleContentType(contentType)) {
            stringBuffer.append(message.getContent().toString());
        } else {
            Multipart multipart =  (Multipart) message.getContent();
            loadMultipart(multipart, stringBuffer);
        }
    }

    private void loadMultipart(Multipart multipart, StringBuffer stringBuffer) throws MessagingException, IOException {
        for (int i = 0; i < multipart.getCount(); i++) {
            BodyPart bodyPart = multipart.getBodyPart(i);

            if (isSimpleContentType(bodyPart.getContentType())) {
                stringBuffer.append(bodyPart.getContent().toString());
            } else {
                Multipart multipart1 = (Multipart) bodyPart.getContent();
                loadMultipart(multipart1, stringBuffer);
            }
        }

    }

    private boolean isSimpleContentType(String contentType) {
        return !contentType.contains("multipart");
    }


}
