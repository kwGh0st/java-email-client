package app.emailclient.view;

import app.emailclient.EmailManager;
import app.emailclient.controller.BaseController;
import app.emailclient.controller.LoginWindowController;
import app.emailclient.controller.MainWindowController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewFactory {
    private EmailManager emailManger;

    public ViewFactory(){}

    public ViewFactory(EmailManager emailManger) {
        this.emailManger = emailManger;
    }

    public void showLoginWindow() {
        BaseController controller = new LoginWindowController(this, emailManger, "LoginWindow.fxml");
        initStage(controller);
    }

    public void showMainWindow() {
        BaseController controller = new MainWindowController(this, emailManger, "MainWindow.fxml");
        initStage(controller);
    }

    private void initStage(BaseController controller) {

        FXMLLoader loader = new FXMLLoader(getClass().getResource(controller.getFxmlName()));
        loader.setController(controller);
        Parent parent;

        try {
            parent = loader.load();
        } catch (IOException ex) {
            ex.printStackTrace();
            return;
        }

        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }

    public void closeStage(Stage stageToClose) {
        stageToClose.close();
    }
}
