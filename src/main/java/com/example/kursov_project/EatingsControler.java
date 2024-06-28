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

public class EatingsControler implements Initializable {
    @FXML
    private TextField idField;
    @FXML
    private TableColumn<Eating, Integer> idColum;
    @FXML
    private Button toDishsButton;
    @FXML
    private Button toIngridientsButton;
    @FXML
    private TableView<Eating> eatingsTable;
    @FXML
    private TableColumn<Eating, Integer> tableIdColum;
    @FXML
    private TableColumn<Eating,Integer> innColum;
    @FXML
    private TableColumn<Eating, Integer> officiantIdColum;
    @FXML
    private TableColumn<Eating, Integer> dishIdColum;
    @FXML
    private TableColumn<Eating, String> dateColum;
    @FXML
    private TableColumn<Eating, String> timeBeginColum;
    @FXML
    private TableColumn<Eating, String> timeEndColum;
    @FXML
    private TextField tableIdField;
    @FXML
    private TextField innField;
    @FXML
    private TextField officiantIdField;
    @FXML
    private TextField dateField;
    @FXML
    private TextField timeBeginField;
    @FXML
    private Button addButton;
    @FXML
    private Button delateButton;
    @FXML
    private TextField timeEndField;
    @FXML
    private Button updateButton;
    @FXML
    private Button exitButton;
    private final ObservableList<Eating> data = FXCollections.observableArrayList();
    private static User user = new User();
    static void setUser(User user) {
        EatingsControler.user = user;
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
                    Eating eating = new Eating(Integer.parseInt(idField.getText()), Integer.parseInt(tableIdField.getText()), Integer.parseInt(innField.getText()), Integer.parseInt(officiantIdField.getText()), dateField.getText(), timeBeginField.getText(), timeEndField.getText());
                    bdHandler.insertEating(eating);
                    data.add(eating);
                }catch (Exception e){}
            });
            delateButton.setOnAction(actionEvent -> {
                try {
                    Eating eating = eatingsTable.getSelectionModel().getSelectedItem();
                    bdHandler.deleatEating(eating);
                    data.remove(eating);
                }catch (Exception e){}

            });
            updateButton.setOnAction(actionEvent -> {
                try {
                    Eating eating = new Eating(Integer.parseInt(idField.getText()), Integer.parseInt(tableIdField.getText()), Integer.parseInt(innField.getText()), Integer.parseInt(officiantIdField.getText()), dateField.getText(), timeBeginField.getText(), timeEndField.getText());
                    bdHandler.updateEating(eating);
                    data.set(data.indexOf(eatingsTable.getSelectionModel().getSelectedItem()), eating);
                }catch (Exception e){}
            });


        }else {
            addButton.setDisable(!user.isAdmin());
            delateButton.setDisable(!user.isAdmin());
            updateButton.setDisable(!user.isAdmin());
            idField.setDisable(!user.isAdmin());
            tableIdField.setDisable(!user.isAdmin());
            innField.setDisable(!user.isAdmin());
            officiantIdField.setDisable(!user.isAdmin());
            dateField.setDisable(!user.isAdmin());
            timeBeginField.setDisable(!user.isAdmin());
            timeEndField.setDisable(!user.isAdmin());
        }
        toIngridientsButton.setOnAction(actionEvent -> {
            Stage stage;
            Parent root;
            String newWindow = "main.fxml";
            IngridControler.setUser(user);
            stage = (Stage) toDishsButton.getScene().getWindow(); // получаем окно этой кнопки
            try {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(newWindow)));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Scene scene = new Scene(root); // Получаем новое окно
            stage.setScene(scene); // Ставим новое окно вместо старого
            stage.show();
        });

        toDishsButton.setOnAction(actionEvent -> {
            Stage stage;
            Parent root;
            String newWindow = "dishEating.fxml";
            DishEatingController.setUser(user);
            stage = (Stage) toDishsButton.getScene().getWindow(); // получаем окно этой кнопки
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
        tableIdColum.setCellValueFactory(new PropertyValueFactory<>("table_id"));
        innColum.setCellValueFactory(new PropertyValueFactory<>("inn"));
        officiantIdColum.setCellValueFactory(new PropertyValueFactory<>("officiant_id"));
        dateColum.setCellValueFactory(new PropertyValueFactory<>("date_"));
        timeBeginColum.setCellValueFactory(new PropertyValueFactory<>("time_begin"));
        timeEndColum.setCellValueFactory(new PropertyValueFactory<>("time_end"));

        eatingsTable.setItems(data);
    }

    private void addInformationAboutEatings(){
        ResultSet ingrids = bdHandler.getEatings();
        try {while (ingrids.next()){
            Eating ingr = new Eating(ingrids.getInt(1)
                    ,ingrids.getInt(2)
                    ,ingrids.getInt(3)
                    ,ingrids.getInt(4)
                    ,ingrids.getString(5)
                    ,ingrids.getString(6)
                    ,ingrids.getString(7));
            data.add(ingr);
        }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
