/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server;

/**
 *
 * @author ashour
 */


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect 
{
    private static Connection connect;

    private DBConnect() {        
    }
    
    static public Connection getConn() {
        System.out.println("Database");
        if (connect == null) {
            try {
                
                Class.forName("com.mysql.jdbc.Driver");
                connect = DriverManager.getConnection("jdbc:mysql://localhost/Chat?user=mmba&password=iti");

            } catch (ClassNotFoundException | SQLException e) {
                System.out.println(e.getMessage());
            }
            
            return connect;
        }
        return connect;
    }
    
    static public void closeConn() {
        try {
            connect.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        connect = null;
    }
            
}
