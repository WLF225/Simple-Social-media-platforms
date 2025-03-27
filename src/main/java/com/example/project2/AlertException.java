package com.example.project2;

import javafx.scene.control.Alert;

public class AlertException extends RuntimeException {
    public AlertException(String message) {
        super(message);
    }
}
