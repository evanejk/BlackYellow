module com.happynicetime.blackyellow {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.swing;
    opens com.happynicetime.blackyellow to javafx.fxml;
    exports com.happynicetime.blackyellow;

}
