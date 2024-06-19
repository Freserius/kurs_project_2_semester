package com.example.kursov_project;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class IngrifControler implements Initializable {
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
        IngrifControler.user = user;
    }
    static User getUser(){
        return user;
    }
    DataBase bdHandler = new DataBase();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addButon.setOnAction(actionEvent -> {
            if (user.isAdmin()) {
                Ingridient ingridient = new Ingridient(nameField.getText(), unitField.getText(), countField.getText());
                data.add(ingridient);
                bdHandler.insertIngridient(ingridient);
            }
        });
        deleatButton.setOnAction(actionEvent -> {
            if (user.isAdmin()) {
                Ingridient ingridient = ingridientTable.getSelectionModel().getSelectedItem();
                data.remove(ingridient);
                bdHandler.deleatIngridient(ingridient);
            }
        });

        addInformationAboutIngr();
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        unitColumn.setCellValueFactory(new PropertyValueFactory<>("unit"));
        countColumn.setCellValueFactory(new PropertyValueFactory<>("count_"));
        ingridientTable.setItems(data);
    }

    private void addInformationAboutIngr(){
        ResultSet ingrids = bdHandler.getIngridients();
            try {while (ingrids.next()){
                Ingridient ingr = new Ingridient(ingrids.getString(1)
                        ,ingrids.getString(2),
                        ingrids.getString(3));
                data.add(ingr);
            }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
}
