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

public class SignupController {

    @FXML private TextField fullNameField;
    @FXML private TextField emailField;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private Label statusLabel;

    @FXML
    protected void onSignupClicked() {
        String fullName = fullNameField.getText().trim();
        String email = emailField.getText().trim();
        String username = usernameField.getText().trim();
        String pass = passwordField.getText();
        String confirm = confirmPasswordField.getText();

        if (fullName.isEmpty() || email.isEmpty() || username.isEmpty() || pass.isEmpty()) {
            statusLabel.setText("All fields are required");
            return;
        }

        if (!pass.equals(confirm)) {
            statusLabel.setText("Passwords do not match");
            return;
        }

        // Demo: auto-accept any valid signup
        statusLabel.setText("✅ Account created successfully!");
        statusLabel.setStyle("-fx-text-fill: green;");

        // Auto login after signup
        javafx.animation.PauseTransition pause = new javafx.animation.PauseTransition(javafx.util.Duration.seconds(1.2));
        pause.setOnFinished(e -> switchToLoginAndAutoLogin(username));
        pause.play();
    }

    private void switchToLoginAndAutoLogin(String username) {
        try {
            Stage stage = (Stage) usernameField.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login-view.fxml"));
            Parent root = loader.load();

            LoginController loginCtrl = loader.getController();
            // In a real app you would pre-fill or auto-login here

            Scene scene = new Scene(root, 420, 380);
            stage.setScene(scene);
            stage.setTitle("Auction88 - Login");

            // Simulate successful login right after signup
            javafx.application.Platform.runLater(() -> {
                try {
                    FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
                    Parent mainRoot = mainLoader.load();
                    HelloController mainCtrl = mainLoader.getController();
                    mainCtrl.setLoggedInUser(username);

                    Scene mainScene = new Scene(mainRoot, 820, 680);
                    stage.setScene(mainScene);
                    stage.setTitle("Auction88 – Live Auctions");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    protected void onSwitchToLogin() {
        try {
            Stage stage = (Stage) usernameField.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login-view.fxml"));
            Parent root = loader.load();
            stage.setScene(new Scene(root, 420, 380));
            stage.setTitle("Auction88 - Login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}