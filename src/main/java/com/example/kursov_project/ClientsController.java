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

public class ClientsController implements Initializable {
    public Button toTablesButton;
    public Button toDishButton;
    public Button updateButton;
    public Button exitButton;
    @FXML
    private TableView<Client> clientsTable;
    @FXML
    private TableColumn<Client, Integer> innColum;
    @FXML
    private TableColumn<Client, String> nameColum;
    @FXML
    private TableColumn<Client, String> addressColum;
    @FXML
    private TextField innField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField addressField;
    @FXML
    private Button addButton;
    @FXML
    private Button delateButton;
    private final ObservableList<Client> data = FXCollections.observableArrayList();
    private static User user = new User();


    static void setUser(User user) {
        ClientsController.user = user;
    }
    static User getUser(){
        return user;
    }
    DataBase bdHandler = new DataBase();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (user.isAdmin()){
            addButton.setOnAction(actionEvent -> {
                Client client = new Client(Integer.parseInt(innField.getText()),nameField.getText(), addressField.getText());
                bdHandler.insertTable(client);
                data.add(client);

            });
            delateButton.setOnAction(actionEvent -> {
                Client client = clientsTable.getSelectionModel().getSelectedItem();
                bdHandler.deleatClient(client);
                data.remove(client);

            });
            updateButton.setOnAction(actionEvent -> {
                Client client = new Client(Integer.parseInt(innField.getText()),nameField.getText(), addressField.getText());
                bdHandler.updateClient(client);
                data.set(data.indexOf(clientsTable.getSelectionModel().getSelectedItem()),client);


            });


        }else {
            addButton.setDisable(!user.isAdmin());
            delateButton.setDisable(!user.isAdmin());
            updateButton.setDisable(!user.isAdmin());
            innField.setDisable(!user.isAdmin());
            nameField.setDisable(!user.isAdmin());
            addressField.setDisable(!user.isAdmin());
        }
        toDishButton.setOnAction(actionEvent -> {

        });

        toTablesButton.setOnAction(actionEvent -> {
            Stage stage;
            Parent root;
            String newWindow = "tables.fxml";
            TablesController.setUser(user);
            stage = (Stage) toTablesButton.getScene().getWindow(); // получаем окно этой кнопки
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
        innColum.setCellValueFactory(new PropertyValueFactory<>("inn"));
        nameColum.setCellValueFactory(new PropertyValueFactory<>("name"));
        addressColum.setCellValueFactory(new PropertyValueFactory<>("address"));
        clientsTable.setItems(data);
    }

    private void addInformationAboutClients(){
        ResultSet ingrids = bdHandler.getCliets();
        try {while (ingrids.next()){
            Client ingr = new Client(ingrids.getInt(1)
                    ,ingrids.getString(2)
                    ,ingrids.getString(3));
            data.add(ingr);
        }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
