module app.emailclient {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires activation;
    requires java.mail;


    opens app.emailclient to javafx.fxml;
    opens app.emailclient.controller to javafx.fxml;
    exports app.emailclient;
}