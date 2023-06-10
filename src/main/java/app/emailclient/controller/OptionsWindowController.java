package app.emailclient.controller;

import app.emailclient.EmailManager;
import app.emailclient.view.ViewFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

public class OptionsWindowController extends BaseController {

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
    private ChoiceBox<?> themePicker;

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
}
