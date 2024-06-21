package com.example.kursov_project;

import javafx.event.ActionEvent;
import javafx.event.EventTarget;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
public class AutControler {
    @FXML
    private Button regButton;
    @FXML
    private Button authorization;
    @FXML
    private TextField loginField;
    @FXML
    private TextField passField;
    private static User user = new User();
    DataBase bdHandler = new DataBase();

    static void setUser(User user) {
        AutControler.user = user;
    }
    static User getUser(){
        return user;
    }

    boolean authorize() throws SQLException {
        user.setLogin(loginField.getText());
        user.setPassword(passField.getText());
        ResultSet rz = bdHandler.getUser(user);
        try {
            if (rz.next() && Objects.equals(rz.getString(2), user.getPassword())) {
                if (Objects.equals(rz.getString(3), "yes")) {
                    user.setAdmin(true);
                    return true;
                }
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
//        user.setLogin(loginField.getText()); // Присваиваем user текст из поля логина
//        user.setPassword(passField.getText()); // Присваиваем user текст из поля пароля
////        ResultSet resultSet = db.getUser(user); // Присваиваем resultSet итог выборки данных (пользователя)
////        try {
////            if (resultSet.next()) { // Если есть пользователь - возвращается true
////                return true;
////            }
////        } catch (SQLException e) {
////            throw new RuntimeException(e);
////        }
////        incorrectData.setText("INCORRECT DATA");
//        if (Objects.equals(user.getLogin(), "user") && Objects.equals(user.getPassword(), "0000")){
//            return true;
//        } else if (Objects.equals(user.getLogin(), "admin") && Objects.equals(user.getPassword(), "1234")) {
//            user.setAdmin(true);
//            return true;
//        }
//        return false;
    }

    @FXML
    private void handleButtonAction (ActionEvent event) throws Exception { // Для смены окна
        Stage stage;
        Parent root;
        Button button = (Button) event.getSource(); // Получаем кнопку, которая произвела нажатие
        String newWindow = "";

        // Проверяем, какая кнопка была нажата
        // и ставим разные окна в зависимости от этого
        if (button == authorization && authorize()) {// Попытка авторизации
            newWindow = "main.fxml";
            IngridControler.setUser(user);
            user.setPassword("");
            user.setLogin("");
        } else if (button == regButton) {
            newWindow = "regestation.fxml";
        } else {
            return; // Если не получилось авторизоваться - метод заканчивает работу (во избежание ошибок в консоли)
        }
        stage = (Stage) button.getScene().getWindow(); // получаем окно этой кнопки
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(newWindow)));
        Scene scene = new Scene(root); // Получаем новое окно
        stage.setScene(scene); // Ставим новое окно вместо старого
        stage.show();
    }
}
