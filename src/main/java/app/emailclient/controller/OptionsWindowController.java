package app.emailclient.controller;

import app.emailclient.EmailManager;
import app.emailclient.view.ColorTheme;
import app.emailclient.view.FontSize;
import app.emailclient.view.ViewFactory;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.stage.Stage;
import javafx.util.StringConverter;

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
        viewFactory.setColorTheme(themePicker.getValue());
        viewFactory.setFontSize(FontSize.values()[(int) fontSlider.getValue()]);
    }

    @FXML
    void onCancelButtonAction() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        viewFactory.closeStage(stage);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUpThemePicker();
        setUpFontSlider();
    }

    private void setUpFontSlider() {
        fontSlider.setMin(0);
        fontSlider.setMax(FontSize.values().length - 1);
        fontSlider.setValue(viewFactory.getFontSize().ordinal());
        fontSlider.setMajorTickUnit(1);
        fontSlider.setMinorTickCount(0);
        fontSlider.setBlockIncrement(1);
        fontSlider.setSnapToTicks(true);
        fontSlider.setShowTickMarks(true);
        fontSlider.setShowTickLabels(true);
        fontSlider.setLabelFormatter(new StringConverter<>() {
            @Override
            public String toString(Double object) {
                int i = object.intValue();
                return FontSize.values()[i].toString();
            }

            @Override
            public Double fromString(String string) {
                return null;
            }
        });

        fontSlider.valueProperty().addListener((obs, oldVal, newVal) -> fontSlider.setValue(newVal.intValue()));
    }

    private void setUpThemePicker() {
        themePicker.setItems(FXCollections.observableArrayList(ColorTheme.values()));
        themePicker.setValue(viewFactory.getColorTheme());
    }
}
