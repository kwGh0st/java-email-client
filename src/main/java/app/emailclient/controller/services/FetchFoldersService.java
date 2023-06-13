package app.emailclient.controller.services;

import app.emailclient.model.EmailTreeItem;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.Store;

public class FetchFoldersService extends Service<Void> {
    private final Store store;
    private final EmailTreeItem<String> root;

    public FetchFoldersService(Store store, EmailTreeItem<String> root) {
        this.store = store;
        this.root = root;
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<>() {
            @Override
            protected Void call() throws Exception {
                fetchFolders();
                return null;
            }
        };
    }

    private void fetchFolders() throws MessagingException {
        Folder[] folders = store.getDefaultFolder().list();
        handleFolders(folders, root);
    }

    private void handleFolders(Folder[] folders, EmailTreeItem<String> root) throws MessagingException {
        for (Folder folder : folders) {
            EmailTreeItem<String> item = new EmailTreeItem<>(folder.getName());

            if (folder.getType() == 3 || folder.getType() == 2) {
                Folder[] subFolders = folder.list();
                handleFolders(subFolders, item);
            }
            root.getChildren().add(item);
            root.setExpanded(true);
        }
    }
}
