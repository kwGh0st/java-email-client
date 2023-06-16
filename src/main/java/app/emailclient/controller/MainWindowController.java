package app.emailclient.controller;

import app.emailclient.EmailManager;
import app.emailclient.controller.services.MessageRenderService;
import app.emailclient.model.EmailMessage;
import app.emailclient.model.EmailTreeItem;
import app.emailclient.model.SizeInteger;
import app.emailclient.view.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.WebView;
import javafx.util.Callback;

import javax.mail.MessagingException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class MainWindowController extends BaseController implements Initializable {
    @FXML
    private TableColumn<EmailMessage, Date> dateColumn;

    @FXML
    private TableView<EmailMessage> emailTableView;

    @FXML
    private TreeView<String> emailTreeView;

    @FXML
    private WebView emailWebView;

    @FXML
    private MenuBar menuBar;

    @FXML
    private TableColumn<EmailMessage, String> recipientColumn;

    @FXML
    private TableColumn<EmailMessage, String> senderColumn;

    @FXML
    private TableColumn<EmailMessage, SizeInteger> sizeColumn;

    @FXML
    private TableColumn<EmailMessage, String> subjectColumn;

    @FXML
    private MenuItem addAccountButton;

    private MessageRenderService messageRenderService;


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
        setUpBoldRows();
        setUpMessageRenderService();
        setUpMessageSelection();

    }

    private void setUpMessageSelection() {
        emailTableView.setOnMouseClicked(event -> {
            EmailMessage item = emailTableView.getSelectionModel().getSelectedItem();

            if (item != null) {
                emailManager.setSelectedMessage(item);
                if (!item.isRead()) {
                    try {
                        emailManager.setRead();
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                }
                messageRenderService.setEmailMessage(item);
                messageRenderService.restart();
            }
        });
    }

    private void setUpMessageRenderService() {
        messageRenderService = new MessageRenderService(emailWebView.getEngine());
    }

    private void setUpBoldRows() {
        emailTableView.setRowFactory(new Callback<>() {
            @Override
            public TableRow<EmailMessage> call(TableView<EmailMessage> param) {
                return new TableRow<>() {
                    @Override
                    protected void updateItem(EmailMessage item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            if (item.isRead()) {
                                setStyle("");
                            } else {
                                setStyle("-fx-font-weight: bold");
                            }
                        }
                    }
                };
            }
        });
    }

    private void setUpFolderSelection() {
        emailTreeView.setOnMouseClicked(event -> {
            EmailTreeItem<String> item = (EmailTreeItem<String>) emailTreeView.getSelectionModel().getSelectedItem();

            if (item != null) {
                emailManager.setSelectedFolder(item);
                emailTableView.setItems(item.getEmailMessages());
            }
        });
    }

    private void setUpTableView() {
        subjectColumn.setCellValueFactory(new PropertyValueFactory<>("subject"));
        recipientColumn.setCellValueFactory(new PropertyValueFactory<>("recipient"));
        senderColumn.setCellValueFactory(new PropertyValueFactory<>("sender"));
        sizeColumn.setCellValueFactory(new PropertyValueFactory<>("size"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
    }

    private void setUpEmailsTreeView() {
        emailTreeView.setRoot(emailManager.getFoldersRoot());
        emailTreeView.setShowRoot(true);
    }
}
