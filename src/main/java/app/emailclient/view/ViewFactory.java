package app.emailclient.view;

import app.emailclient.EmailManager;
import app.emailclient.controller.BaseController;
import app.emailclient.controller.LoginWindowController;
import app.emailclient.controller.MainWindowController;
import app.emailclient.controller.OptionsWindowController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ViewFactory {
    private final EmailManager emailManger;
    private ColorTheme colorTheme = ColorTheme.DEFAULT;
    private FontSize fontSize = FontSize.MEDIUM;
    private final List<Stage> activeStages;
    private boolean mainWindowInitialized = false;

    public ViewFactory(EmailManager emailManger) {
        this.emailManger = emailManger;
        this.activeStages = new ArrayList<>();
    }

    public void showLoginWindow() {
        BaseController controller = new LoginWindowController(this, emailManger, "LoginWindow.fxml");
        initStage(controller);
    }

    public void showMainWindow() {
        BaseController controller = new MainWindowController(this, emailManger, "MainWindow.fxml");
        initStage(controller);
        mainWindowInitialized = true;
    }

    public void showOptionsWindow() {
        BaseController controller = new OptionsWindowController(this, emailManger, "OptionsWindow.fxml");
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
        activeStages.add(stage);
        updateStyles();
    }

    public void closeStage(Stage stageToClose) {
        stageToClose.close();
        activeStages.remove(stageToClose);
    }

    public ColorTheme getColorTheme() {
        return colorTheme;
    }

    public FontSize getFontSize() {
        return fontSize;
    }

    public void setColorTheme(ColorTheme colorTheme) {
        this.colorTheme = colorTheme;
    }

    public void setFontSize(FontSize fontSize) {
        this.fontSize = fontSize;
    }

    public boolean isMainWindowInitialized() {
        return mainWindowInitialized;
    }

    public void updateStyles() {
        for (Stage stage : activeStages) {
            Scene scene = stage.getScene();
            scene.getStylesheets().clear();
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(ColorTheme.getCssStyle(colorTheme))).toExternalForm());
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(FontSize.getCssStyle(fontSize))).toExternalForm());
        }
    }
}
