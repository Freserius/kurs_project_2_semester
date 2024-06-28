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

public class DishEatingController implements Initializable {
@FXML
    private TableView<dishEating> dishEatongTable;
    @FXML

    private TableColumn<dishEating, Integer> idColum;
    @FXML
    private TableColumn<dishEating, Integer> dishIdColum;
    @FXML
    private TableColumn<dishEating, Integer> eatingIdColum;
    @FXML
    private TextField eatingIdField;
    @FXML
    private TextField idField;
    @FXML
    private TextField dishIdField;
    @FXML
    private Button updateButton;
    @FXML
    private Button addButton;
    @FXML
    private Button delateButton;
    @FXML
    private Button toEatingButton;
    @FXML
    private Button toDishButton;
    @FXML
    private Button exitButton;
    private final ObservableList<dishEating> data = FXCollections.observableArrayList();
    private static User user = new User();
    static void setUser(User user) {
        DishEatingController.user = user;
    }
    static User getUser(){
        return user;
    }
    DataBase bdHandler = new DataBase();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (user.isAdmin()){
            addButton.setOnAction(actionEvent -> {
                try {
                    dishEating eating = new dishEating(Integer.parseInt(idField.getText()), Integer.parseInt(dishIdField.getText()), Integer.parseInt(eatingIdField.getText()));
                    bdHandler.insertDishEating(eating);
                    data.add(eating);
                }catch (Exception e){}
            });
            delateButton.setOnAction(actionEvent -> {
                try {
                    dishEating dish = dishEatongTable.getSelectionModel().getSelectedItem();
                    bdHandler.deleatDishEating(dish);
                    data.remove(dish);
                }catch (Exception e){}

            });
            updateButton.setOnAction(actionEvent -> {
                try {
                    dishEating eating = new dishEating(Integer.parseInt(idField.getText()), Integer.parseInt(dishIdField.getText()), Integer.parseInt(eatingIdField.getText()));
                    bdHandler.updateDishEating(eating);
                    data.set(data.indexOf(dishEatongTable.getSelectionModel().getSelectedItem()), eating);
                }catch (Exception e){}
            });


        }else {
            addButton.setDisable(!user.isAdmin());
            delateButton.setDisable(!user.isAdmin());
            updateButton.setDisable(!user.isAdmin());
            idField.setDisable(!user.isAdmin());
            dishIdField.setDisable(!user.isAdmin());
            eatingIdField.setDisable(!user.isAdmin());
        }
        toEatingButton.setOnAction(actionEvent -> {
            Stage stage;
            Parent root;
            String newWindow = "eatings.fxml";
            EatingsControler.setUser(user);
            stage = (Stage) toEatingButton.getScene().getWindow(); // получаем окно этой кнопки
            try {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(newWindow)));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Scene scene = new Scene(root); // Получаем новое окно
            stage.setScene(scene); // Ставим новое окно вместо старого
            stage.show();
        });

        toDishButton.setOnAction(actionEvent -> {
            Stage stage;
            Parent root;
            String newWindow = "dishs.fxml";
            DishsController.setUser(user);
            stage = (Stage) toDishButton.getScene().getWindow(); // получаем окно этой кнопки
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
            stage = (Stage) exitButton.getScene().getWindow(); // получаем окно этой кнопки
            try {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(newWindow)));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Scene scene = new Scene(root); // Получаем новое окно
            stage.setScene(scene); // Ставим новое окно вместо старого
            stage.show();
        });

        addInformationAboutEatings();
        idColum.setCellValueFactory(new PropertyValueFactory<>("id"));
        dishIdColum.setCellValueFactory(new PropertyValueFactory<>("dish_id"));
        eatingIdColum.setCellValueFactory(new PropertyValueFactory<>("eating_id"));

        dishEatongTable.setItems(data);
    }

    private void addInformationAboutEatings(){
        ResultSet ingrids = bdHandler.getDishEating();
        try {while (ingrids.next()){
            dishEating ingr = new dishEating(ingrids.getInt(1)
                    ,ingrids.getInt(3)
                    ,ingrids.getInt(2));
            data.add(ingr);
        }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
