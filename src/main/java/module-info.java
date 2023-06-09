module app.emailclient {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;


    opens app.emailclient to javafx.fxml;
    exports app.emailclient;
}