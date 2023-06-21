package app.emailclient.controller.persistence;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PersistenceAccess implements Serializable {
    private final String VALID_ACCOUNTS_LOCATION = System.getProperty("user.home") + File.separator + "validAccounts.ser";
    private Encoder encoder = new Encoder();

    public List<ValidAccount> loadFromPersistence() {
        List<ValidAccount> result = new ArrayList<>();

        try {
            FileInputStream fileInputStream = new FileInputStream(VALID_ACCOUNTS_LOCATION);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            List<ValidAccount> accounts = (List<ValidAccount>) objectInputStream.readObject();
            decodePasswords(accounts);
            result.addAll(accounts);
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        return result;
    }

    private void decodePasswords(List<ValidAccount> persistedList) {
        for (ValidAccount account : persistedList) {
            String originalPassword = account.getPassword();
            account.setPassword(encoder.decode(originalPassword));
        }
    }

    public void saveToPersistence(List<ValidAccount> validAccountList) {
        try {
            File file = new File(VALID_ACCOUNTS_LOCATION);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            encodePasswords(validAccountList);
            objectOutputStream.writeObject(validAccountList);
            fileOutputStream.close();
            objectOutputStream.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void encodePasswords(List<ValidAccount> persistedList) {
        for (ValidAccount account : persistedList) {
            String originalPassword = account.getPassword();
            account.setPassword(encoder.encode(originalPassword));
        }
    }
}
