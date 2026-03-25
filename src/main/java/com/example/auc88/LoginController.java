package com.example.auc88;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label statusLabel;

    @FXML
    protected void onLoginClicked() {
        String user = usernameField.getText().trim();
        String pass = passwordField.getText();

        // Simple demo validation (in real app: check against DB or file)
        if (user.isEmpty() || pass.isEmpty()) {
            statusLabel.setText("Please fill all fields");
            return;
        }

        if ((user.equals("admin") && pass.equals("1234")) ||
                (user.equals("demo") && pass.equals("demo"))) {

            loadMainWindow(user);
        } else {
            statusLabel.setText("Invalid username or password");
        }
    }

    @FXML
    protected void onSwitchToSignup() {
        try {
            Stage stage = (Stage) usernameField.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("signup-view.fxml"));
            Parent root = loader.load();
            stage.setScene(new Scene(root, 460, 520));
            stage.setTitle("Auction88 - Sign Up");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadMainWindow(String username) {
        try {
            Stage stage = (Stage) usernameField.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            Parent root = loader.load();

            // Pass username to main controller
            HelloController mainController = loader.getController();
            mainController.setLoggedInUser(username);

            Scene scene = new Scene(root, 820, 680);
            stage.setScene(scene);
            stage.setTitle("Auction88 – Live Auctions");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}