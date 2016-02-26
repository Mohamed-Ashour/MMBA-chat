/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package chat;

/**
 *
 * @author ashour
 */


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DBConnect {
    private Connection connect;

    public DBConnect() {
        
        try {
            
        // This will load the MySQL driver, each DB has its own driver
        Class.forName("com.mysql.jdbc.Driver");
        
        // Setup the connection with the DB
        connect = DriverManager.getConnection("jdbc:mysql://localhost/Chat?" + "user=bears&password=iti");
        
        Statement stm = connect.createStatement();
            
        
        
        }
        
        catch(Exception e) {
            e.getMessage();
        }
        
        
    }
    
    
            
            
            
}
