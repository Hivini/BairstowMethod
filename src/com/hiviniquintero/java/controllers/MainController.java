package com.hiviniquintero.java.controllers;

import com.hiviniquintero.java.method.BairstowMethod;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;

public class MainController {
    @FXML
    private Button doButton;

    @FXML
    private TextField formula;

    @FXML
    private TextField variableR;

    @FXML
    private TextField variableS;

    @FXML
    private TextField variableDeltaR;

    @FXML
    private TextField variableDeltaS;

    @FXML
    private TextField maxIter;

    @FXML
    private ListView<String> roots;

    @FXML
    private ListView<String> complexRoots;

    @FXML
    public void initialize() {}

    @FXML
    public void handleButtonPressed(ActionEvent e) {
        if (e.getSource() == doButton) {
            BairstowMethod bairstowMethod = new BairstowMethod(Integer.parseInt(maxIter.getText()));
            bairstowMethod.findRoots(formula.getText(), Float.parseFloat(variableR.getText()),
                    Float.parseFloat(variableS.getText()), Float.parseFloat(variableDeltaR.getText()),
                    Float.parseFloat(variableDeltaS.getText()));
            ObservableList<String> rootsList = FXCollections.observableArrayList(bairstowMethod.getRoots());
            ObservableList<String> complexRootsList =
                    FXCollections.observableArrayList(bairstowMethod.getComplexRoots());
            roots.setItems(rootsList);
            complexRoots.setItems(complexRootsList);
            System.out.println(bairstowMethod.getRoots());
            System.out.println(bairstowMethod.getComplexRoots());
        }
    }
}
