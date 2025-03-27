package com.example.project2;

import javafx.scene.control.Alert;

public class AlertException extends RuntimeException {
    public AlertException(String message) {
        super(message);
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
