package app.emailclient.controller.services;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.mail.Folder;
import java.util.List;

public class FolderUpdaterService extends Service {
    private final List<Folder> folderList;

    public FolderUpdaterService(List<Folder> folderList) {
        this.folderList = folderList;
    }

    @Override
    protected Task createTask() {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                while (true) {
                    try {
                        Thread.sleep(1000);
                        for (Folder folder : folderList) {
                            if (folder.getType() != Folder.HOLDS_FOLDERS && folder.isOpen()) {
                                folder.getMessageCount();
                            }
                        }
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        };
    }
}
