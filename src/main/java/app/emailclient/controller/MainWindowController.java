package app.emailclient.controller;

import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeView;
import javafx.scene.web.WebView;

public class MainWindowController {
    @FXML
    private TableView<?> emailTableView;

    @FXML
    private TreeView<?> emailTreeView;

    @FXML
    private WebView emailWebView;

    @FXML
    private MenuBar menuBar;

    public MainWindowController(){}
}
