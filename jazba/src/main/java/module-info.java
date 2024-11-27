open module jazba {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.graphics;
    requires javafx.base;
    requires java.desktop;  // Add this for AWT and ImageIO
    exports jazba;
}
