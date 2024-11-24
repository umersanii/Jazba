module jazba {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.graphics;

    opens jazba to javafx.fxml;
    exports jazba;
}
