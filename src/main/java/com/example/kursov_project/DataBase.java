package com.example.kursov_project;


import java.lang.reflect.InvocationTargetException;
import java.sql.*;

public class DataBase {
        Connection connection;
        ResultSet resSet=null;
        public Connection getDBConnection(){
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

        public ResultSet getIngridients(){
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
        public void insertIngridient(Ingridient ingridient){
            String insertIngridient = "INSERT INTO ingridients (name, unit, count_) VALUES(?, ?, ?)";
            PreparedStatement prSt = null;
            try {
                prSt = getDBConnection().prepareStatement(insertIngridient);
                prSt.setString(1,ingridient.getName());
                prSt.setString(2,ingridient.getUnit());
                prSt.setInt(3, Integer.parseInt(ingridient.getCount_()));
                prSt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        public void deleatIngridient(Ingridient ingridient){
            String insertIngridient = "Delete from ingridients where name = ?";
            PreparedStatement prSt = null;
            try {
                prSt = getDBConnection().prepareStatement(insertIngridient);
                prSt.setString(1,ingridient.getName());
                prSt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    public void updateIngridient(Ingridient ingridient_whatChange, Ingridient ingridient){
        String updateIngridient = "UPDATE ingridients unit = ?, count_ = ? where name = ?";
        PreparedStatement prSt = null;
        try {
            prSt = getDBConnection().prepareStatement(updateIngridient);
            prSt.setString(1,ingridient.getUnit());
            prSt.setInt(2,Integer.parseInt(ingridient.getCount_()));
            prSt.setString(3,ingridient_whatChange.getName());

            prSt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public ResultSet getIngridientsDish(){
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

    public void insertIngridientsDish(INgridDish iNgridDish){
        String insertIngridient = "INSERT INTO ingridients_dishes (ingridient_name, dish_id, count_) VALUES(?, ?, ?)";
        PreparedStatement prSt = null;
        try {
            prSt = getDBConnection().prepareStatement(insertIngridient);
            prSt.setString(1,iNgridDish.getIngridient_name());
            prSt.setInt(2,iNgridDish.getDish_id());
            prSt.setInt(3, iNgridDish.getCount());
            prSt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void deleatIngridientsDish(INgridDish iNgridDish){
        String insertIngridient = "Delete from ingridients_dishes where ingridient_name = ? AND dish_id = ?";
        PreparedStatement prSt = null;
        try {
            prSt = getDBConnection().prepareStatement(insertIngridient);
            prSt.setString(1,iNgridDish.getIngridient_name());
            prSt.setInt(2,iNgridDish.getDish_id());
            prSt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void updarinfIngridientDish(INgridDish iNgridDish_1, INgridDish iNgridDish_2){
        String insertIngridient = "UPDATE ingridients_dishes SET ingridient_name = ?, dish_id = ?, count_ = ? where ingridient_name = ? AND dish_id = ?";
        PreparedStatement prSt = null;
        try {
            prSt = getDBConnection().prepareStatement(insertIngridient);
            prSt.setString(1,iNgridDish_2.getIngridient_name());
            prSt.setInt(2,iNgridDish_2.getDish_id());
            prSt.setInt(3,iNgridDish_2.getCount());
            prSt.setString(4,iNgridDish_1.getIngridient_name());
            prSt.setInt(5,iNgridDish_1.getDish_id());

            prSt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


        public void test() {
            try {
                String url = "jdbc:mysql://localhost/restoran";
                String username = "root";
                String password = "";
                Connection conn = DriverManager.getConnection(url, username, password);
                Statement statement = conn.createStatement();
                String query = "SELECT * from clients";
                ResultSet result = statement.executeQuery(query);

                while (result.next()) {
                    String name = result.getString("name");
                    System.out.println(name);
                }
                conn.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
