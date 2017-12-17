package com.mysapp;
import java.sql.*;

public class SQLConnection {
    private Connection DBconnect;

    public Connection connect(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch(ClassNotFoundException ex) {
            System.out.println("Error: unable to load driver class!");
            System.exit(1);
        }
        String URL = "jdbc:mysql://localhost:3306/mys_data_au";
        String USER = "root";
        String PASS = "";
        try {
            DBconnect = DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException ex){
            System.out.println(ex);
        }
        return DBconnect;
    }
}
