package app.emailclient.controller;

import app.emailclient.EmailManager;
import app.emailclient.view.ColorTheme;
import app.emailclient.view.ViewFactory;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

import java.net.URL;
import java.util.ResourceBundle;

public class OptionsWindowController extends BaseController implements Initializable {

    @FXML
    private Button ApplyButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Label fontLabel;

    @FXML
    private Slider fontSlider;

    @FXML
    private Label themeLabel;

    @FXML
    private ChoiceBox<ColorTheme> themePicker;

    @FXML
    private Label titleLabel;

    public OptionsWindowController(ViewFactory viewFactory, EmailManager emailManager, String fxmlName) {
        super(viewFactory, emailManager, fxmlName);
    }

    @FXML
    void onApplyButtonAction() {

    }

    @FXML
    void onCancelButtonAction() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUpThemePicker();
    }

    private void setUpThemePicker() {
        themePicker.setItems(FXCollections.observableArrayList(ColorTheme.values()));
        themePicker.setValue(viewFactory.getColorTheme());
    }
}
