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

public class IngrDishControler implements Initializable {
    @FXML
    public TableView<INgridDish> ingridientDishTable;
    @FXML
    public TableColumn<INgridDish, Integer> idColum;
    @FXML
    public TableColumn<INgridDish, String> ingridDishColum;
    @FXML
    public TableColumn<INgridDish, Integer> dishIdColum;
    @FXML
    public TableColumn<INgridDish, Integer> ingridCountColum;
    @FXML
    public TextField idField;
    @FXML
    public TextField ingridNameField;
    public TextField dishIdField;
    @FXML
    public TextField ingridCountField;
    @FXML
    public Button delateButton;
    @FXML
    public Button addButton;
    @FXML
    public Button toIngridientsButton;
    @FXML
    public Button toOfficiantsButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button exitButton;

    private final ObservableList<INgridDish> data = FXCollections.observableArrayList();
    private static User user = new User();


    static void setUser(User user) {
        IngrDishControler.user = user;
    }
    static User getUser(){
        return user;
    }
    DataBase bdHandler = new DataBase();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (user.isAdmin()){
        addButton.setOnAction(actionEvent -> {
                INgridDish ingridient = new INgridDish(Integer.parseInt(idField.getText()),ingridNameField.getText(), Integer.parseInt(dishIdField.getText()), Integer.parseInt(ingridCountField.getText()));
                data.add(ingridient);
                bdHandler.insertIngridientsDish(ingridient);
        });
        delateButton.setOnAction(actionEvent -> {
                INgridDish ingridient = ingridientDishTable.getSelectionModel().getSelectedItem();
                data.remove(ingridient);
                bdHandler.deleatIngridientsDish(ingridient);
        });
        updateButton.setOnAction(actionEvent -> {
            INgridDish ingridient = new INgridDish(Integer.parseInt(idField.getText()),ingridNameField.getText(), Integer.parseInt(dishIdField.getText()), Integer.parseInt(ingridCountField.getText()));
            data.set(data.indexOf(ingridientDishTable.getSelectionModel().getSelectedItem()),ingridient);
            bdHandler.updarinfIngridientDish(ingridientDishTable.getSelectionModel().getSelectedItem(),ingridient);
        });
        toOfficiantsButton.setOnAction(actionEvent -> {

        });
        toIngridientsButton.setOnAction(actionEvent -> {
            Stage stage;
            Parent root;
            String newWindow = "main.fxml";
            stage = (Stage) toIngridientsButton.getScene().getWindow(); // получаем окно этой кнопки
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
        }else {
            addButton.setDisable(!user.isAdmin());
            delateButton.setDisable(!user.isAdmin());
            updateButton.setDisable(!user.isAdmin());
            idField.setDisable(!user.isAdmin());
            ingridNameField.setDisable(!user.isAdmin());
            dishIdField.setDisable(!user.isAdmin());
            ingridCountField.setDisable(!user.isAdmin());
        }

        addInformationAboutIngrDish();
        idColum.setCellValueFactory(new PropertyValueFactory<>("id"));
        ingridDishColum.setCellValueFactory(new PropertyValueFactory<>("ingridient_name"));
        dishIdColum.setCellValueFactory(new PropertyValueFactory<>("id"));
        ingridCountColum.setCellValueFactory(new PropertyValueFactory<>("count_"));
        ingridientDishTable.setItems(data);
    }

    private void addInformationAboutIngrDish(){
        ResultSet ingrids = bdHandler.getIngridientsDish();
        try {while (ingrids.next()){
            INgridDish ingr = new INgridDish(ingrids.getInt(1)
                    ,ingrids.getString(2),
                    ingrids.getInt(3),
                    ingrids.getInt(4));
            data.add(ingr);
        }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
