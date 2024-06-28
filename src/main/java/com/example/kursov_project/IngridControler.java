package com.example.kursov_project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class IngridControler implements Initializable {
    @FXML
    private Button updateButton;
    @FXML
    private Button exitButton;
    @FXML
    private Button toEatingsButton;
    @FXML
    private Button toIngridientsDishes;
    @FXML
    private TableView<Ingridient> ingridientTable;
    @FXML
    private TableColumn<Ingridient, String> nameColumn;
    @FXML
    private TableColumn<Ingridient, String> unitColumn;
    @FXML
    private TableColumn<Ingridient, Integer> countColumn;
    private final ObservableList<Ingridient> data = FXCollections.observableArrayList();
    @FXML
    private Button addButon;
    @FXML
    private Button deleatButton;
    @FXML
    private TextField nameField;
    @FXML
    private TextField unitField;
    @FXML
    private TextField countField;
    private static User user;
    static void setUser(User user) {
        IngridControler.user = user;
    }
    static User getUser(){
        return user;
    }
    DataBase bdHandler = new DataBase();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       toIngridientsDishes.setOnAction(actionEvent -> {
           Stage stage;
           Parent root;
           String newWindow = "ingridientDish.fxml";
           IngrDishControler.setUser(user);
           stage = (Stage) toIngridientsDishes.getScene().getWindow(); // получаем окно этой кнопки
           try {
               root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(newWindow)));
           } catch (IOException e) {
               throw new RuntimeException(e);
           }
           Scene scene = new Scene(root); // Получаем новое окно
           stage.setScene(scene); // Ставим новое окно вместо старого
           stage.show();
       });
       toEatingsButton.setOnAction(actionEvent -> {
           Stage stage;
           Parent root;
           String newWindow = "eatings.fxml";
           EatingsControler.setUser(user);
           stage = (Stage) toEatingsButton.getScene().getWindow(); // получаем окно этой кнопки
           try {
               root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(newWindow)));
           } catch (IOException e) {
               throw new RuntimeException(e);
           }
           Scene scene = new Scene(root); // Получаем новое окно
           stage.setScene(scene); // Ставим новое окно вместо старого
           stage.show();
       });
       exitButton.setOnAction(actionEvent -> {
           Stage stage;
           Parent root;
           String newWindow = "auteurist.fxml";
           stage = (Stage) toIngridientsDishes.getScene().getWindow(); // получаем окно этой кнопки
           try {
               root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(newWindow)));
           } catch (IOException e) {
               throw new RuntimeException(e);
           }
           Scene scene = new Scene(root); // Получаем новое окно
           stage.setScene(scene); // Ставим новое окно вместо старого
           stage.show();
       });
       if (user.isAdmin()) {
           addButon.setOnAction(actionEvent -> {
               try {
                   Ingridient ingridient = new Ingridient(nameField.getText(), unitField.getText(), countField.getText());
                   data.add(ingridient);
                   bdHandler.insertIngridient(ingridient);
               }catch (Exception ignored){}
           });
           deleatButton.setOnAction(actionEvent -> {
               try {
                   Ingridient ingridient = ingridientTable.getSelectionModel().getSelectedItem();
                   data.remove(ingridient);
                   bdHandler.deleatIngridient(ingridient);
               }catch (Exception ingnored){}
           });
           updateButton.setOnAction(actionEvent -> {
               try {
                   Ingridient ingridient = new Ingridient(nameField.getText(), unitField.getText(), countField.getText());
                   data.set(data.indexOf(ingridientTable.getSelectionModel().getSelectedItem()), ingridient);
                   bdHandler.updateIngridient(ingridientTable.getSelectionModel().getSelectedItem(), ingridient);
               }catch (Exception e){}
           });
       }else {
           deleatButton.setDisable(true);
           addButon.setDisable(true);
           updateButton.setDisable(true);
           nameField.setDisable(true);
           unitField.setDisable(true);
           countField.setDisable(true);
       }

        addInformationAboutIngr();
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        unitColumn.setCellValueFactory(new PropertyValueFactory<>("unit"));
        countColumn.setCellValueFactory(new PropertyValueFactory<>("count_"));
        ingridientTable.setItems(data);
    }

    private void addInformationAboutIngr() {
        ResultSet ingrids = bdHandler.getIngridients();
        try {
            while (ingrids.next()) {
                Ingridient ingr = new Ingridient(ingrids.getString(1)
                        , ingrids.getString(2),
                        ingrids.getString(3));
                data.add(ingr);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
