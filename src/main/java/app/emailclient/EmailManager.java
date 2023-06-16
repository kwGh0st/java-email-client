package app.emailclient;

import app.emailclient.controller.services.FetchFoldersService;
import app.emailclient.controller.services.FolderUpdaterService;
import app.emailclient.model.EmailAccount;
import app.emailclient.model.EmailTreeItem;

import javax.mail.Folder;
import java.util.ArrayList;
import java.util.List;

public class EmailManager {
    private final EmailTreeItem<String> foldersRoot = new EmailTreeItem<>("Accounts: ");
    private final List<Folder> folderList = new ArrayList<>();

    public EmailManager() {
        FolderUpdaterService folderUpdaterService = new FolderUpdaterService(folderList);
        folderUpdaterService.start();
    }

    public EmailTreeItem<String> getFoldersRoot(){
        return foldersRoot;
    }

    public List<Folder> getFolderList() {
        return folderList;
    }

    public void addEmailAccount(EmailAccount emailAccount){
        EmailTreeItem<String> treeItem = new EmailTreeItem<>(emailAccount.getName());
        FetchFoldersService fetchFoldersService = new FetchFoldersService(emailAccount.getStore(), treeItem, folderList);
        fetchFoldersService.start();
        foldersRoot.getChildren().add(treeItem);
    }
}
