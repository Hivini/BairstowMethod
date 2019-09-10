package com.hiviniquintero.java.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import com.hiviniquintero.java.helpers.Utils;

public class MainController {
    @FXML
    private Button doButton;

    @FXML
    private TextField formula;

    @FXML
    public void initialize() {}

    @FXML
    public void handleButtonPressed(ActionEvent e) {
        if (e.getSource() == doButton) {
            System.out.println(Utils.formatPolynomial(formula.getText()));
        }
    }
}
