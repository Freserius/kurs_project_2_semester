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

public class OfficiantsController implements Initializable {
    @FXML
    private TableView<Officaant> officiantsTable;
    @FXML
    private TextField idField;
    @FXML
    private TextField adressField;
    @FXML
    private TextField telephonField;
    @FXML
    private TextField nameField;
    @FXML
    private TableColumn<Officaant, Integer> idColum;
    @FXML
    private TableColumn<Officaant, String> adressColum;
    @FXML
    private TableColumn<Officaant, String> nameColum;
    @FXML
    private TableColumn<Officaant, String> telehoneColum;
    @FXML
    private Button ExitButton;
    @FXML
    private Button addButton;
    @FXML
    private Button DelateButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button toIngridientDishsButton;
    @FXML
    private Button toTablesButton;
    private final ObservableList<Officaant> data = FXCollections.observableArrayList();
    private static User user = new User();


    static void setUser(User user) {
        OfficiantsController.user = user;
    }
    static User getUser(){
        return user;
    }
    DataBase bdHandler = new DataBase();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (user.isAdmin()){
            addButton.setOnAction(actionEvent -> {
                Officaant officaant = new Officaant(Integer.parseInt(idField.getText()),adressField.getText(),telephonField.getText(),nameField.getText());
                data.add(officaant);
                bdHandler.insertOfficiant(officaant);
            });
            DelateButton.setOnAction(actionEvent -> {
                Officaant officaant = officiantsTable.getSelectionModel().getSelectedItem();
                data.remove(officaant);
                bdHandler.deleatOfficiant(officaant);
            });
            updateButton.setOnAction(actionEvent -> {
                Officaant officaant = new Officaant(Integer.parseInt(idField.getText()),adressField.getText(), telephonField.getText(), nameField.getText());
                data.set(data.indexOf(officiantsTable.getSelectionModel().getSelectedItem()),officaant);
                bdHandler.updateOfficiant(officaant);
            });

        }else {
            addButton.setDisable(!user.isAdmin());
            DelateButton.setDisable(!user.isAdmin());
            updateButton.setDisable(!user.isAdmin());
            idField.setDisable(!user.isAdmin());
            adressField.setDisable(!user.isAdmin());
            nameField.setDisable(!user.isAdmin());
            telephonField.setDisable(!user.isAdmin());
        }

        toTablesButton.setOnAction(actionEvent -> {
            Stage stage;
            Parent root;
            String newWindow = "tables.fxml";
            TablesController.setUser(user);
            stage = (Stage) toIngridientDishsButton.getScene().getWindow(); // получаем окно этой кнопки
            try {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(newWindow)));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Scene scene = new Scene(root); // Получаем новое окно
            stage.setScene(scene); // Ставим новое окно вместо старого
            stage.show();
        });
        toIngridientDishsButton.setOnAction(actionEvent -> {
            Stage stage;
            Parent root;
            String newWindow = "ingridientDish.fxml";
            IngrDishControler.setUser(user);
            stage = (Stage) toIngridientDishsButton.getScene().getWindow(); // получаем окно этой кнопки
            try {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(newWindow)));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Scene scene = new Scene(root); // Получаем новое окно
            stage.setScene(scene); // Ставим новое окно вместо старого
            stage.show();
        });
        ExitButton.setOnAction(actionEvent -> {
            Stage stage;
            Parent root;
            String newWindow = "auteurist.fxml";
            stage = (Stage) ExitButton.getScene().getWindow(); // получаем окно этой кнопки
            try {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(newWindow)));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Scene scene = new Scene(root); // Получаем новое окно
            stage.setScene(scene); // Ставим новое окно вместо старого
            stage.show();
        });

        addInformationAboutOfficiants();
        idColum.setCellValueFactory(new PropertyValueFactory<>("id"));
        adressColum.setCellValueFactory(new PropertyValueFactory<>("adress"));
        telehoneColum.setCellValueFactory(new PropertyValueFactory<>("phone_number"));
        nameColum.setCellValueFactory(new PropertyValueFactory<>("name"));
        officiantsTable.setItems(data);
    }

    private void addInformationAboutOfficiants(){
        ResultSet ingrids = bdHandler.getOfficiants();
        try {while (ingrids.next()){
            Officaant ingr = new Officaant(ingrids.getInt(1)
                    ,ingrids.getString(2),
                    ingrids.getString(3),
                    ingrids.getString(4));
            data.add(ingr);
        }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
