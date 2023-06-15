package app.emailclient.controller;

import app.emailclient.EmailManager;
import app.emailclient.model.EmailMessage;
import app.emailclient.model.EmailTreeItem;
import app.emailclient.view.ViewFactory;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class MainWindowController extends BaseController implements Initializable {
    @FXML
    private TableColumn<Date, SimpleObjectProperty<Date>> dateColumn;

    @FXML
    private TableView<EmailMessage> emailTableView;

    @FXML
    private TreeView<String> emailTreeView;

    @FXML
    private WebView emailWebView;

    @FXML
    private MenuBar menuBar;

    @FXML
    private TableColumn<String, SimpleStringProperty> recipientColumn;

    @FXML
    private TableColumn<String, SimpleStringProperty> senderColumn;

    @FXML
    private TableColumn<Integer, SimpleIntegerProperty> sizeColumn;

    @FXML
    private TableColumn<String, SimpleStringProperty> subjectColumn;

    @FXML
    private MenuItem addAccountButton;


    public MainWindowController(ViewFactory viewFactory, EmailManager emailManager, String fxmlName) {
        super(viewFactory, emailManager, fxmlName);
    }

    @FXML
    public void onOptionsButtonAction() {
        viewFactory.showOptionsWindow();
    }

    @FXML
    void onAddAccountAction() {
        viewFactory.showLoginWindow();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUpEmailsTreeView();
        setUpTableView();
        setUpFolderSelection();
    }

    private void setUpTableView() {
        subjectColumn.setCellValueFactory(new PropertyValueFactory<String, SimpleStringProperty>("subject"));
        recipientColumn.setCellValueFactory(new PropertyValueFactory<String, SimpleStringProperty>("recipient"));
        senderColumn.setCellValueFactory(new PropertyValueFactory<String, SimpleStringProperty>("sender"));
        sizeColumn.setCellValueFactory(new PropertyValueFactory<Integer, SimpleIntegerProperty>("size"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<Date, SimpleObjectProperty<Date>>("date"));
    }

    private void setUpFolderSelection() {
        emailTableView.setOnMouseClicked(event -> {
            EmailTreeItem<String> item = (EmailTreeItem<String>) emailTreeView.getSelectionModel().getSelectedItem();

            if (item != null) {
                emailTableView.setItems(item.getEmailMessages());
            }
        });
    }

    private void setUpEmailsTreeView() {
        emailTreeView.setRoot(emailManager.getFoldersRoot());
        emailTreeView.setShowRoot(true);
    }
}
