module jazba {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens jazba to javafx.fxml;
    exports jazba;
}
