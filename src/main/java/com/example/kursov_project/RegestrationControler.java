package com.example.kursov_project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class RegestrationControler implements Initializable {
    @FXML

    private Button registrairButton;
    @FXML
    private Button changePasswordButton;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField loginField;
    @FXML
    private PasswordField repPasswordField;
    @FXML
    private Button backButton;
    private User user = new User();
    private DataBase bdHandler = new DataBase();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        changePasswordButton.setOnAction(actionEvent -> {
            user.setLogin(loginField.getText());
            user.setPassword(passwordField.getText());
            ResultSet rz = bdHandler.getUser(user);
            try {
                if (rz.next() && Objects.equals(passwordField.getText(), repPasswordField.getText())) { // Если есть пользователь - возвращается true
                    bdHandler.updateUser(user);
                    Stage stage;
                    Parent root;
                    String newWindow = "auteurist.fxml";
                    user.setAdmin(false);
                    user.setPassword("");
                    user.setLogin("");
                    stage = (Stage) changePasswordButton.getScene().getWindow(); // получаем окно этой кнопки
                    try {
                        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(newWindow)));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        registrairButton.setOnAction(actionEvent -> {
            user.setLogin(loginField.getText());
            user.setPassword(passwordField.getText());
            ResultSet rz = bdHandler.getUser(user);
            try {
                if (!rz.next() && Objects.equals(passwordField.getText(), repPasswordField.getText())) { // Если есть пользователь - возвращается true
                    bdHandler.insertUser(user);
                    Stage stage;
                    Parent root;
                    String newWindow = "main.fxml";
                    IngridControler.setUser(user);
                    stage = (Stage) registrairButton.getScene().getWindow(); // получаем окно этой кнопки
                    try {
                        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(newWindow)));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    Scene scene = new Scene(root); // Получаем новое окно
                    stage.setScene(scene); // Ставим новое окно вместо старого
                    stage.show();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        backButton.setOnAction(actionEvent -> {
            Stage stage;
            Parent root;
            String newWindow = "auteurist.fxml";
            stage = (Stage) backButton.getScene().getWindow(); // получаем окно этой кнопки
            try {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(newWindow)));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Scene scene = new Scene(root); // Получаем новое окно
            stage.setScene(scene); // Ставим новое окно вместо старого
            stage.show();});

    }
}
