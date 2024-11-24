module jazba {
    requires javafx.controls;
    requires javafx.fxml;

    opens jazba to javafx.fxml;
    exports jazba;
}
