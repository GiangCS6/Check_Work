module com.example.auc88 {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.auc88 to javafx.fxml;
    exports com.example.auc88;
}