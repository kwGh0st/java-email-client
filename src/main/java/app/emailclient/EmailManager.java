package app.emailclient;

import app.emailclient.controller.services.FetchFoldersService;
import app.emailclient.controller.services.FolderUpdaterService;
import app.emailclient.model.EmailAccount;
import app.emailclient.model.EmailMessage;
import app.emailclient.model.EmailTreeItem;
import app.emailclient.view.IconResolver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.List;

public class EmailManager {
    private final EmailTreeItem<String> foldersRoot = new EmailTreeItem<>("Accounts: ");
    private final List<Folder> folderList = new ArrayList<>();
    private EmailMessage selectedMessage;
    private EmailTreeItem<String> selectedFolder;
    private final ObservableList<EmailAccount> accounts = FXCollections.observableArrayList();
    private final IconResolver iconResolver = new IconResolver();

    public EmailManager() {
        FolderUpdaterService folderUpdaterService = new FolderUpdaterService(folderList);
        folderUpdaterService.start();
    }

    public ObservableList<EmailAccount> getAccounts() {
        return accounts;
    }

    public void setSelectedMessage(EmailMessage selectedMessage) {
        this.selectedMessage = selectedMessage;
    }

    public void setSelectedFolder(EmailTreeItem<String> selectedFolder) {
        this.selectedFolder = selectedFolder;
    }

    public EmailMessage getSelectedMessage() {
        return selectedMessage;
    }

    public EmailTreeItem<String> getSelectedFolder() {
        return selectedFolder;
    }

    public EmailTreeItem<String> getFoldersRoot(){
        return foldersRoot;
    }

    public List<Folder> getFolderList() {
        return folderList;
    }

    public void addEmailAccount(EmailAccount emailAccount){
        EmailTreeItem<String> treeItem = new EmailTreeItem<>(emailAccount.getName());
        accounts.add(emailAccount);
        treeItem.setGraphic(iconResolver.getIconForFolder(emailAccount.getName()));
        FetchFoldersService fetchFoldersService = new FetchFoldersService(emailAccount.getStore(), treeItem, folderList);
        fetchFoldersService.start();
        foldersRoot.getChildren().add(treeItem);
    }

    public void setRead() throws MessagingException {
        selectedMessage.setRead(true);
        selectedMessage.getMessage().setFlag(Flags.Flag.SEEN, true);
        selectedFolder.decrementMessagesCount();
    }

    public void setUnRead() throws MessagingException {
        selectedMessage.setRead(false);
        selectedMessage.getMessage().setFlag(Flags.Flag.SEEN, false);
        selectedFolder.incrementMessagesCount();
    }

    public void deleteMessage() throws MessagingException {
        selectedMessage.getMessage().setFlag(Flags.Flag.DELETED, true);
        selectedFolder.getEmailMessages().remove(selectedMessage);
    }
}
