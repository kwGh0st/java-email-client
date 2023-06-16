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
            fetchMessagesOnFolder(folder, item);
            if (folder.getType() == 3 || folder.getType() == 2) {
                Folder[] subFolders = folder.list();
                handleFolders(subFolders, item);
            }
            root.getChildren().add(item);
        }
    }

    private void fetchMessagesOnFolder(Folder folder, EmailTreeItem<String> item) {
        Service fetchMessagesService = new Service() {
            @Override
            protected Task createTask() {
                return new Task() {
                    @Override
                    protected Object call() throws Exception {
                        if (folder.getType() == Folder.HOLDS_MESSAGES) {
                            folder.open(Folder.READ_WRITE);
                            int messagesCount = folder.getMessageCount();

                            for (int i = messagesCount; i > 0; i--) {
                                item.addMessage(folder.getMessage(i));
                            }
                        }
                        return null;
                    }
                };
            }
        };
        fetchMessagesService.start();
    }
}
