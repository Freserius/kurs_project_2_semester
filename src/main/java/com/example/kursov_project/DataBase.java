package com.example.kursov_project;


import java.lang.reflect.InvocationTargetException;
import java.sql.*;

public class DataBase {
    Connection connection;
    ResultSet resSet = null;

    public Connection getDBConnection() {
        String connectionString = "jdbc:mysql://localhost:3306/restoran";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                 InvocationTargetException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            connection = DriverManager.getConnection(connectionString, "root", "");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return connection;
    }
    public ResultSet getUser(User user){
        String geterUser = "SELECT * FROM `users` WHERE login = ?";
        PreparedStatement prSt = null;
        try {
            prSt = getDBConnection().prepareStatement(geterUser);
            prSt.setString(1, user.getLogin());
            resSet = prSt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resSet;
    }
    public void insertUser(User user) {
        String insertIngridient = "INSERT INTO users (login, password_, admin) VALUES(?, ?, ?)";
        PreparedStatement prSt = null;
        try {
            prSt = getDBConnection().prepareStatement(insertIngridient);
            prSt.setString(1, user.getLogin());
            prSt.setString(2, user.getPassword());
            prSt.setString(3, "no");
            prSt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateUser(User user) {
        String updateIngridient = "UPDATE users SET password_ = ? where login = ?";
        PreparedStatement prSt = null;
        try {
            prSt = getDBConnection().prepareStatement(updateIngridient);
            prSt.setString(1, user.getPassword());
            prSt.setString(2, user.getLogin());

            prSt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public ResultSet getIngridients() {
        String getIngridients = "SELECT * FROM `ingridients`";
        PreparedStatement prSt = null;
        try {
            prSt = getDBConnection().prepareStatement(getIngridients);
            resSet = prSt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resSet;
    }

    public void insertIngridient(Ingridient ingridient) {
        String insertIngridient = "INSERT INTO ingridients (name, unit, count_) VALUES(?, ?, ?)";
        PreparedStatement prSt = null;
        try {
            prSt = getDBConnection().prepareStatement(insertIngridient);
            prSt.setString(1, ingridient.getName());
            prSt.setString(2, ingridient.getUnit());
            prSt.setInt(3, Integer.parseInt(ingridient.getCount_()));
            prSt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleatIngridient(Ingridient ingridient) {
        String insertIngridient = "Delete from ingridients where name = ?";
        PreparedStatement prSt = null;
        try {
            prSt = getDBConnection().prepareStatement(insertIngridient);
            prSt.setString(1, ingridient.getName());
            prSt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateIngridient(Ingridient ingridient_whatChange, Ingridient ingridient) {
        String updateIngridient = "UPDATE ingridients unit = ?, count_ = ? where name = ?";
        PreparedStatement prSt = null;
        try {
            prSt = getDBConnection().prepareStatement(updateIngridient);
            prSt.setString(1, ingridient.getUnit());
            prSt.setInt(2, Integer.parseInt(ingridient.getCount_()));
            prSt.setString(3, ingridient_whatChange.getName());

            prSt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet getIngridientsDish() {
        String getIngridients = "SELECT * FROM `ingridients_dishes`";
        PreparedStatement prSt = null;
        try {
            prSt = getDBConnection().prepareStatement(getIngridients);
            resSet = prSt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resSet;
    }

    public void insertIngridientsDish(INgridDish iNgridDish) {
        String insertIngridient = "INSERT INTO ingridients_dishes (id, ingridient_name, dish_id, count_) VALUES(?, ?, ?)";
        PreparedStatement prSt = null;
        try {
            prSt = getDBConnection().prepareStatement(insertIngridient);
            prSt.setInt(1,iNgridDish.getId());
            prSt.setString(2, iNgridDish.getIngridient_name());
            prSt.setInt(3, iNgridDish.getDish_id());
            prSt.setInt(4, iNgridDish.getCount());
            prSt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleatIngridientsDish(INgridDish iNgridDish) {
        String insertIngridient = "Delete from ingridients_dishes where ingridient_name = ? AND dish_id = ?";
        PreparedStatement prSt = null;
        try {
            prSt = getDBConnection().prepareStatement(insertIngridient);
            prSt.setString(1, iNgridDish.getIngridient_name());
            prSt.setInt(2, iNgridDish.getDish_id());
            prSt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updarinfIngridientDish(INgridDish iNgridDish_1, INgridDish iNgridDish_2) {
        String insertIngridient = "UPDATE ingridients_dishes SET ingridient_name = ?, dish_id = ?, count_ = ? where ingridient_name = ? AND dish_id = ?";
        PreparedStatement prSt = null;
        try {
            prSt = getDBConnection().prepareStatement(insertIngridient);
            prSt.setString(1, iNgridDish_2.getIngridient_name());
            prSt.setInt(2, iNgridDish_2.getDish_id());
            prSt.setInt(3, iNgridDish_2.getCount());
            prSt.setString(4, iNgridDish_1.getIngridient_name());
            prSt.setInt(5, iNgridDish_1.getDish_id());

            prSt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet getOfficiants() {
        String getIngridients = "SELECT * FROM `oficiant`";
        PreparedStatement prSt = null;
        try {
            prSt = getDBConnection().prepareStatement(getIngridients);
            resSet = prSt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resSet;
    }

    public void insertOfficiant(Officaant officaant) {
        String insertOfficiant = "INSERT INTO oficiant (adress, phone, name) VALUES(?, ?, ?)";
        PreparedStatement prSt = null;
        try {
            prSt = getDBConnection().prepareStatement(insertOfficiant);
            prSt.setString(1, officaant.getAdress());
            prSt.setString(2, officaant.getPhone_number());
            prSt.setString(3, officaant.getName());
            prSt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleatOfficiant(Officaant officaant) {
        String insertIngridient = "Delete from oficiant where id = ?";
        PreparedStatement prSt = null;
        try {
            prSt = getDBConnection().prepareStatement(insertIngridient);
            prSt.setInt(1, officaant.getId());
            prSt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateOfficiant(Officaant officaant) {
        String insertIngridient = "UPDATE oficiant SET phone = ?, adress = ?, name = ? where id = ?";
        PreparedStatement prSt = null;
        try {
            prSt = getDBConnection().prepareStatement(insertIngridient);
            prSt.setString(1, officaant.getPhone_number());
            prSt.setString(2, officaant.getAdress());
            prSt.setString(3, officaant.getName());
            prSt.setInt(4, officaant.getId());

            prSt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public ResultSet getOTables(){
        String getIngridients = "SELECT * FROM `tables_r`";
        PreparedStatement prSt = null;
        try {
            prSt = getDBConnection().prepareStatement(getIngridients);
            resSet = prSt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resSet;
    }

    public void insertTable(Table table){
        String insertOfficiant = "INSERT INTO tables_r (id, max_count_people) VALUES(?, ?)";
        PreparedStatement prSt = null;
        try {
            prSt = getDBConnection().prepareStatement(insertOfficiant);
            prSt.setInt(1,table.getId());
            prSt.setInt(2,table.getMax_count_people());
            prSt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void deleatTable(Table table){
        String insertIngridient = "Delete from tables_r where id = ?";
        PreparedStatement prSt = null;
        try {
            prSt = getDBConnection().prepareStatement(insertIngridient);
            prSt.setInt(1,table.getId());
            prSt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void updateTable(Table table){
        String insertIngridient = "UPDATE tables_r SET max_count_people = ? where id = ?";
        PreparedStatement prSt = null;
        try {
            prSt = getDBConnection().prepareStatement(insertIngridient);
            prSt.setInt(1,table.getMax_count_people());
            prSt.setInt(2,table.getId());

            prSt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public ResultSet getCliets(){
        String getIngridients = "SELECT * FROM `clients`";
        PreparedStatement prSt = null;
        try {
            prSt = getDBConnection().prepareStatement(getIngridients);
            resSet = prSt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resSet;
    }

    public void insertTable(Client client){
        String insertClients = "INSERT INTO clients (inn, name, address) VALUES(?, ?, ?)";
        PreparedStatement prSt = null;
        try {
            prSt = getDBConnection().prepareStatement(insertClients);
            prSt.setInt(1,client.getInn());
            prSt.setString(2,client.getAddress());
            prSt.setString(3,client.getAddress());
            prSt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void deleatClient(Client client){
        String insertClients = "Delete from clients where inn = ?";
        PreparedStatement prSt = null;
        try {
            prSt = getDBConnection().prepareStatement(insertClients);
            prSt.setInt(1,client.getInn());
            prSt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void updateClient(Client client){
        String insertClients = "UPDATE clients SET address = ?, name = ? where inn = ?";
        PreparedStatement prSt = null;
        try {
            prSt = getDBConnection().prepareStatement(insertClients);
            prSt.setString(1,client.getAddress());
            prSt.setString(2,client.getName());
            prSt.setInt(3,client.getInn());

            prSt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public ResultSet getDishs(){
        String getIngridients = "SELECT * FROM `dishs`";
        PreparedStatement prSt = null;
        try {
            prSt = getDBConnection().prepareStatement(getIngridients);
            resSet = prSt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resSet;
    }

    public void insertDish(Dish dish){
        String insertClients = "INSERT INTO dishs (id, name) VALUES(?, ?)";
        PreparedStatement prSt = null;
        try {
            prSt = getDBConnection().prepareStatement(insertClients);
            prSt.setInt(1,dish.getId());
            prSt.setString(2,dish.getName());
            prSt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void deleatDish(Dish dish){
        String insertClients = "Delete from dishs where id = ?";
        PreparedStatement prSt = null;
        try {
            prSt = getDBConnection().prepareStatement(insertClients);
            prSt.setInt(1,dish.getId());
            prSt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void updateDish(Dish dish){
        String insertClients = "UPDATE dishs SET name = ? where id = ?";
        PreparedStatement prSt = null;
        try {
            prSt = getDBConnection().prepareStatement(insertClients);
            prSt.setString(1,dish.getName());
            prSt.setInt(2,dish.getId());

            prSt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet getEatings(){
        String getIngridients = "SELECT * FROM `eatins`";
        PreparedStatement prSt = null;
        try {
            prSt = getDBConnection().prepareStatement(getIngridients);
            resSet = prSt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resSet;
    }

    public void insertEating(Eating eating){
        String insertClients = "INSERT INTO eatins (id, table_id, inn, officiant_id, dish_id, date_, time_begin, time_end) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement prSt = null;
        try {
            prSt = getDBConnection().prepareStatement(insertClients);
            prSt.setInt(1,eating.getId());
            prSt.setInt(2,eating.getTable_id());
            prSt.setInt(3,eating.getInn());
            prSt.setInt(4,eating.getOfficiant_id());
            prSt.setInt(5,eating.getDish_id());
            prSt.setDate(6, Date.valueOf(eating.getDate_()));
            prSt.setTime(7,Time.valueOf(eating.getTime_begin()));
            prSt.setTime(8,Time.valueOf(eating.getTime_end()));

            prSt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void deleatEating(Eating eating){
        String insertClients = "Delete from eatins where id = ?";
        PreparedStatement prSt = null;
        try {
            prSt = getDBConnection().prepareStatement(insertClients);
            prSt.setInt(1,eating.getId());
            prSt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void updateEating(Eating eating){
        String insertClients = "UPDATE dishs SET table_id = ?, inn = ?, officiant_id = ?, dish_id = ?, date_ = ?, time_begin = ?, time_end = ? where id = ?";
        PreparedStatement prSt = null;
        try {
            prSt = getDBConnection().prepareStatement(insertClients);

            prSt.setInt(1,eating.getTable_id());
            prSt.setInt(2,eating.getInn());
            prSt.setInt(3,eating.getOfficiant_id());
            prSt.setInt(4,eating.getDish_id());
            prSt.setString(5,eating.getDate_());
            prSt.setString(6,eating.getTime_begin());
            prSt.setString(7,eating.getTime_end());
            prSt.setInt(8,eating.getId());

            prSt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
