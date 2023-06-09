package app.emailclient.view;

import app.emailclient.EmailManager;

public class ViewFactory {
    private EmailManager emailManger;

    public ViewFactory(){}

    public ViewFactory(EmailManager emailManger) {
        this.emailManger = emailManger;
    }

    public void showLoginWindow() {
        System.out.println("Showing login window!");
    }
}
