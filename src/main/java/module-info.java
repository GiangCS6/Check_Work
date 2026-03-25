module com.example.structor {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.structor to javafx.fxml;
    exports com.example.structor;
}