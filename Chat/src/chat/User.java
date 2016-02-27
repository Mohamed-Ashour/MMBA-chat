/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import java.util.HashMap;

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
    
    public User (HashMap UserInfo) {
        
        email = (String) UserInfo.get("email");
        username = (String) UserInfo.get("username");
        name = (String) UserInfo.get("name");
        status = "online";
        password = (String) UserInfo.get("password");
        country = (String) UserInfo.get("country");
        gender = (String) UserInfo.get("gender");
        
        
        
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
    
    public Boolean isExist() {
        return true;
    }
    
    public void getUser() {
        
    }
    
    public void getAllUsers() {
        
    }
}
