package app.emailclient.controller.services;

import app.emailclient.model.EmailTreeItem;
import app.emailclient.view.IconResolver;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Store;
import javax.mail.event.MessageCountEvent;
import javax.mail.event.MessageCountListener;
import java.util.List;

public class FetchFoldersService extends Service<Void> {
    private final Store store;
    private final EmailTreeItem<String> root;
    private final List<Folder> folderList;
    private final IconResolver iconResolver = new IconResolver();

    public FetchFoldersService(Store store, EmailTreeItem<String> root, List<Folder> folderList) {
        this.store = store;
        this.root = root;
        this.folderList = folderList;
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
            folderList.add(folder);
            EmailTreeItem<String> item = new EmailTreeItem<>(folder.getName());
            fetchMessagesOnFolder(folder, item);
            addMessagesListenerToFolder(folder, item);
            if (folder.getType() == 3 || folder.getType() == 2) {
                Folder[] subFolders = folder.list();
                handleFolders(subFolders, item);
            }
            item.setGraphic(iconResolver.getIconForFolder(folder.getName()));
            root.getChildren().add(item);
        }
    }

    private void addMessagesListenerToFolder(Folder folder, EmailTreeItem<String> item) {
        folder.addMessageCountListener(new MessageCountListener() {
            @Override
            public void messagesAdded(MessageCountEvent e) {
                for (int i = 0; i < e.getMessages().length; i++) {
                    try {
                        Message message = folder.getMessage(folder.getMessageCount() - i);
                        item.addMessageToTop(message);
                    } catch (MessagingException messagingException) {
                        messagingException.printStackTrace();
                    }
                }
            }

            @Override
            public void messagesRemoved(MessageCountEvent e) {
                System.out.println("Message removed!");
            }
        });
    }

    private void fetchMessagesOnFolder(Folder folder, EmailTreeItem<String> item) {
        Service fetchMessagesService = new Service() {
            @Override
            protected Task createTask() {
                return new Task() {
                    @Override
                    protected Object call() throws Exception {
                        if (folder.getType() == Folder.HOLDS_MESSAGES || folder.getType() == (Folder.HOLDS_MESSAGES + Folder.HOLDS_FOLDERS)) {
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
