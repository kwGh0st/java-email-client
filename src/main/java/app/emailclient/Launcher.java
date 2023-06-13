package app.emailclient;

import app.emailclient.view.ViewFactory;
import javafx.application.Application;
import javafx.stage.Stage;

public class Launcher extends Application {
    @Override
    public void start(Stage stage) {
        new ViewFactory(new EmailManager()).showLoginWindow();

    }

    public static void main(String[] args) {
        launch();
    }
}