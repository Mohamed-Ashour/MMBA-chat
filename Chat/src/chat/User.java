
package chat;

import java.sql.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ashour
 */
public class User 
{
    private String email;
    private String username;
    private String name;
    private String status;
    private String password;
    private String country;
    private String gender;
    static private Connection db = DBConnect.getConn();
    static private Statement stm;
    static private String query; 

    
    public User(String email, String username, String name, String status,
            String password, String country, String gender) {
        
        this.email = email;
        this.username = username;
        this.name = name;
        this.status = status;
        this.password = password;
        this.country = country;
        this.gender = gender;
    
    }

    
    public User(String email, String password) {
    
        this.email = email;
        this.password = password;
    
    }
    
    
    public User ( HashMap<String, String> userInfo) {
        
        email = userInfo.get("email");
        username = userInfo.get("username");
        name = userInfo.get("name");
        status = "online";
        password = userInfo.get("password");
        country = userInfo.get("country");
        gender = userInfo.get("gender");
        
    }
    
    
    public Boolean login() {
    
        try {
            
            stm = db.createStatement();
            query = "select * from User where email = " + email + " and password = " + password ;
            ResultSet rs = stm.executeQuery(query);
            return (rs.next());
        
        } catch (SQLException ex) { }
        
        return false;

    }
    
    
    public Boolean add() {
        
        try {
            // student insertion
            stm = db.createStatement();
            query = "insert into User (name, username, email, password,"
                    + " country, gender, status) values ("
                    + "," + name + "," + username + "," + email + "," + password +
                    "," + country + "," + gender + "," + status + ")";
            int rs = stm.executeUpdate(query);
            System.out.println(rs);
            return true;
            
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
        
    }
    

    public String getGender() {
        return gender;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getStatus() {
        return status;
    }

    public String getPassword() {
        return password;
    }    

    public User findUser(String searchEmail) {
        if ( isExist(searchEmail) ){
            try {
     
                stm = db.createStatement();
                query = "select * from User where email = " + searchEmail ;
                ResultSet userResult = stm.executeQuery(query);
                
                name = userResult.getString("name");
                email = userResult.getString("email");
                username = userResult.getString("username");
                password = userResult.getString("password");
                country = userResult.getString("country");
                gender = userResult.getString("gender");
                status = userResult.getString("status");
                
                return new User(email, username, name, status, password, country, gender);
        
            } catch (SQLException ex) {
                Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return null;
    }
    
    
    public User completeInfo() {
        return findUser(this.email);
    }
    
    
    static public String[] getAllUsers() {
        
        try {
     
            stm = db.createStatement();
            query = "select email from User" ;
            ResultSet userResult = stm.executeQuery(query);
            Array a = userResult.getArray("email");
            return (String[])a.getArray();

        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
        
    }
    
    public String[] getContactList(String searchEmail) {
        try {
     
            stm = db.createStatement();
            query = "select user as email user from Contact where user = " + searchEmail + 
                    " union select contact as email from Contact where contact = " +
                    searchEmail ;
            ResultSet userResult = stm.executeQuery(query);
            Array a = userResult.getArray("email");
            return (String[])a.getArray();
                
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    
    public Boolean isContact(String contactEmail) {
        
        try {
                // check if user is friend with contact
                query = "select * from Contact";
                ResultSet contactsResult = stm.executeQuery(query);
                while ( contactsResult.next() ) {
                    String contactFound = contactsResult.getString("contact");
                    String userFound = contactsResult.getString("user");
                    
                    if( (userFound.equals(email) && contactFound.equals(contactEmail)) ||
                        (userFound.equals(contactEmail) && contactFound.equals(email))    ) {
                        return true;
                    }
                }
                
            } catch (SQLException ex) {
                Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        return false;
    
    }
    
    
    public Boolean addContact(String contactEmail) {
        if( isExist(contactEmail) ) {
            
            if( !isContact(contactEmail) ) {
            
                try {
                    stm = db.createStatement();
                    query = "insert into Contact (user, contact) values ("
                            + email + "," + contactEmail + ")";
                    int rs = stm.executeUpdate(query);
                    System.out.println(rs);
                    return true;

                } catch (SQLException ex) {
                    Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
            
        }
        
        return false;
    }
    
    
    public Boolean isExist(String searchEmail) {
        try {
            
            stm = db.createStatement();
            query = "select * from User where email = " + searchEmail ;
            ResultSet rs = stm.executeQuery(query);
            return (rs.next());
        
        } catch (SQLException ex) { }
        
        return false;
    }
    
}
