package app.emailclient;

import app.emailclient.view.ViewFactory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Launcher extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        new ViewFactory(new EmailManager()).showLoginWindow();
        new ViewFactory(new EmailManager()).showMainWindow();

    }

    public static void main(String[] args) {
        launch();
    }
}