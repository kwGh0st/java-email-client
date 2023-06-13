package app.emailclient;

import app.emailclient.controller.services.FetchFoldersService;
import app.emailclient.model.EmailAccount;
import app.emailclient.model.EmailTreeItem;
import javafx.scene.control.TreeItem;

public class EmailManager {

    private EmailTreeItem<String> foldersRoot;



    public EmailTreeItem<String> getFoldersRoot() {
        return foldersRoot;
    }

    public void addEmailAccount(EmailAccount emailAccount){
        foldersRoot = new EmailTreeItem<>(emailAccount.getName());
        FetchFoldersService fetchFoldersService = new FetchFoldersService(emailAccount.getStore(), foldersRoot);
        fetchFoldersService.start();
    }
}
