package app.emailclient.controller.persistence;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PersistenceAccess implements Serializable {
    private final String VALID_ACCOUNTS_LOCATION = System.getProperty("user.home") + File.separator + "validAccounts.ser";

    public List<ValidAccount> loadFromPersistence() {
        List<ValidAccount> result = new ArrayList<>();

        try {
            FileInputStream fileInputStream = new FileInputStream(VALID_ACCOUNTS_LOCATION);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            List<ValidAccount> accounts = (List<ValidAccount>) objectInputStream.readObject();
            result.addAll(accounts);
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        return result;
    }

    public void saveToPersistence(List<ValidAccount> validAccountList) {
        try {
            File file = new File(VALID_ACCOUNTS_LOCATION);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(validAccountList);
            fileOutputStream.close();
            objectOutputStream.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
