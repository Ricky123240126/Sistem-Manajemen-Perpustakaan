/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package connection;

import java.sql.*;

/**
 *
 * @author Carbon
 */
public class connector {
    String jdbc_driver = "com.mysql.cj.jdbc.driver";
    String nama_db = "perpustakaan_db";
    String url_db = "jdbc:mysql://localhost:3306/" + nama_db;
    String username_db = "root";
    String password_db = "";
    
    Connection conn;
    
    public connector(){
        try {
            System.out.println("Mencoba connect ke: "+  url_db);
            Class.forName(jdbc_driver);
            conn = DriverManager.getConnection(url_db, username_db, password_db);
            System.out.println("Connection succes");
        } catch (ClassNotFoundException | SQLException exception){
            System.out.println("Connection gagal" + exception.getLocalizedMessage());
        }

    }
}
