package app.emailclient.controller;

import app.emailclient.EmailManager;
import app.emailclient.view.ViewFactory;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeView;
import javafx.scene.web.WebView;

public class MainWindowController extends BaseController {
    @FXML
    private TableView<?> emailTableView;

    @FXML
    private TreeView<?> emailTreeView;

    @FXML
    private WebView emailWebView;

    @FXML
    private MenuBar menuBar;

    public MainWindowController(){}

    public MainWindowController(ViewFactory viewFactory, EmailManager emailManager, String fxmlName) {
        super(viewFactory, emailManager, fxmlName);
    }
}
