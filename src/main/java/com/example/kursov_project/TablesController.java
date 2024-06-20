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

public class TablesController implements Initializable {
    @FXML
    private TableView<Table> tablesTable;
    @FXML
    private TableColumn<Table, Integer> idCollum;
    @FXML
    private TableColumn<Table, Integer> maxCountCollum;
    @FXML
    private TextField idField;
    @FXML
    private TextField maxCountField;
    @FXML
    private Button exitButton;
    @FXML
    private Button addButton;
    @FXML
    private Button delateButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button toOfficiantButton;
    @FXML
    private Button toClient;
    private final ObservableList<Table> data = FXCollections.observableArrayList();
    private static User user = new User();


    static void setUser(User user) {
        TablesController.user = user;
    }
    static User getUser(){
        return user;
    }
    DataBase bdHandler = new DataBase();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (user.isAdmin()){
            addButton.setOnAction(actionEvent -> {
                Table table = new Table(Integer.parseInt(idField.getText()),Integer.parseInt(maxCountField.getText()));
                data.add(table);
                bdHandler.insertTable(table);
            });
            delateButton.setOnAction(actionEvent -> {
                Table table = tablesTable.getSelectionModel().getSelectedItem();
                data.remove(table);
                bdHandler.deleatTable(table);
            });
            updateButton.setOnAction(actionEvent -> {
                Table table = new Table(Integer.parseInt(idField.getText()),Integer.parseInt(maxCountField.getText()));
                data.set(data.indexOf(tablesTable.getSelectionModel().getSelectedItem()),table);
                bdHandler.updateTable(table);

            });
            toClient.setOnAction(actionEvent -> {
                Stage stage;
                Parent root;
                String newWindow = "clients.fxml";
                ClientsController.setUser(user);
                stage = (Stage) toClient.getScene().getWindow(); // получаем окно этой кнопки
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
            toClient.setDisable(!user.isAdmin());
            addButton.setDisable(!user.isAdmin());
            delateButton.setDisable(!user.isAdmin());
            updateButton.setDisable(!user.isAdmin());
            idField.setDisable(!user.isAdmin());
            idField.setDisable(!user.isAdmin());
            maxCountField.setDisable(!user.isAdmin());
        }


        toOfficiantButton.setOnAction(actionEvent -> {
            Stage stage;
            Parent root;
            String newWindow = "officiants.fxml";
            OfficiantsController.setUser(user);
            stage = (Stage) toOfficiantButton.getScene().getWindow(); // получаем окно этой кнопки
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

        addInformationAboutTables();
        idCollum.setCellValueFactory(new PropertyValueFactory<>("id"));
        maxCountCollum.setCellValueFactory(new PropertyValueFactory<>("max_count_people"));
        tablesTable.setItems(data);
    }

    private void addInformationAboutTables(){
        ResultSet ingrids = bdHandler.getOTables();
        try {while (ingrids.next()){
            Table ingr = new Table(ingrids.getInt(1)
                    ,ingrids.getInt(2));
            data.add(ingr);
        }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
