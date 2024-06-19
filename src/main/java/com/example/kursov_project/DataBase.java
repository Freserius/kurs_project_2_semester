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
