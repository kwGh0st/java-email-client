package app.emailclient.view;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class IconResolver {

    public Node getIconForFolder(String folderName) {
        String nameToLowerCase = folderName.toLowerCase();
        ImageView imageView;

        try {
            if(nameToLowerCase.contains("@")){
                imageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("email.png"))));
            } else if (nameToLowerCase.contains("inbox")){
                imageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("inbox.png"))));
            } else if (nameToLowerCase.contains("sent")){
                imageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("sent2.png"))));
            } else if (nameToLowerCase.contains("spam")){
                imageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("spam.png"))));
            } else {
                imageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("folder.png"))));
            }

        } catch ( Exception e) {
            e.printStackTrace();
            return null;
        }
        imageView.setFitWidth(16);
        imageView.setFitHeight(16);
        return imageView;

    }
}
