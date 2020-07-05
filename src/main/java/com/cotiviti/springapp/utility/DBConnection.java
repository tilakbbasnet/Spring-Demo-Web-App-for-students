package com.cotiviti.springapp.utility;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    public Connection getDbConnection() {
        Connection con = null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/springservice","root","Stilak1@");
        }catch(Exception e){
            e.printStackTrace();
        }
        return  con;
    }
}
