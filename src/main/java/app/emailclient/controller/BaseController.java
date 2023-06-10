package app.emailclient.controller;

import app.emailclient.EmailManager;
import app.emailclient.view.ViewFactory;

public abstract class BaseController {
    private ViewFactory viewFactory;
    private EmailManager emailManager;
    private String fxmlName;

    public BaseController(){}

    public BaseController(ViewFactory viewFactory, EmailManager emailManager, String fxmlName) {
        this.viewFactory = viewFactory;
        this.emailManager = emailManager;
        this.fxmlName = fxmlName;
    }

    public String getFxmlName() {
        return fxmlName;
    }
}
