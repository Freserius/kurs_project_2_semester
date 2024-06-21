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

public class DishsController implements Initializable {
    @FXML

    private TableView<Dish> dishsTable;
    @FXML
    private TableColumn<Dish, String> nameColum;
    @FXML
    private TableColumn<Dish, Integer> idColum;
    @FXML
    private TextField nameField;
    @FXML
    private TextField idField;
    @FXML
    private Button toClientsButton;
    @FXML
    private Button toEatingsButton;
    @FXML
    private Button addButton;
    @FXML
    private Button delateButton;
    @FXML
    private Button exitButton;
    @FXML
    private Button updateButton;
    private final ObservableList<Dish> data = FXCollections.observableArrayList();
    private static User user = new User();


    static void setUser(User user) {
        DishsController.user = user;
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
                    Dish dish = new Dish(Integer.parseInt(idField.getText()), nameField.getText());
                    bdHandler.insertDish(dish);
                    data.add(dish);
                }catch (Exception e){}

            });
            delateButton.setOnAction(actionEvent -> {
                try {
                    Dish dish = dishsTable.getSelectionModel().getSelectedItem();
                    bdHandler.deleatDish(dish);
                    data.remove(dish);
                }catch (Exception e){}

            });
            updateButton.setOnAction(actionEvent -> {
                try {
                    Dish dish = new Dish(Integer.parseInt(idField.getText()), nameField.getText());
                    bdHandler.updateDish(dish);
                    data.set(data.indexOf(dishsTable.getSelectionModel().getSelectedItem()), dish);
                }catch (Exception e){}
            });
            toClientsButton.setOnAction(actionEvent -> {
                Stage stage;
                Parent root;
                String newWindow = "clients.fxml";
                ClientsController.setUser(user);
                stage = (Stage) toClientsButton.getScene().getWindow(); // получаем окно этой кнопки
                try {
                    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(newWindow)));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Scene scene = new Scene(root); // Получаем новое окно
                stage.setScene(scene); // Ставим новое окно вместо старого
                stage.show();
            });

        }else {
            toClientsButton.setDisable(!user.isAdmin());
            addButton.setDisable(!user.isAdmin());
            delateButton.setDisable(!user.isAdmin());
            updateButton.setDisable(!user.isAdmin());
            idField.setDisable(!user.isAdmin());
            nameField.setDisable(!user.isAdmin());
        }
        toEatingsButton.setOnAction(actionEvent -> {
            Stage stage;
            Parent root;
            String newWindow = "eatings.fxml";
            EatingsControler.setUser(user);
            stage = (Stage) toClientsButton.getScene().getWindow(); // получаем окно этой кнопки
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

        addInformationAboutClients();
        idColum.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColum.setCellValueFactory(new PropertyValueFactory<>("name"));
        dishsTable.setItems(data);
    }

    private void addInformationAboutClients(){
        ResultSet ingrids = bdHandler.getDishs();
        try {while (ingrids.next()){
            Dish ingr = new Dish(ingrids.getInt(2)
                    ,ingrids.getString(1));
            data.add(ingr);
        }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
