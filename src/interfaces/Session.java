/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import server.DBConnect;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;




class Session implements Serializable {

    private int sessionId;
    private List<User> users;
    private Time start;
    private Time end;
    
    
    public Session(){}
    
    public Session(int sessionId , List<User> users , Time start ){
        this.sessionId = sessionId;
        this.users = users;
        this.start = start;
    }
    
    public static Session getSession(int aInt) throws RemoteException{
        
        try {
            Connection db = DBConnect.getConn();
            Statement stm;
            String query;
            String query1;
//            String[] mailsArr ;
             //List<User> users = new ArrayList<User>();
            List<User> usersList = new ArrayList<>();
             
             
             
             stm = db.createStatement();
            
           // sessionResult.next();
            
            
            
            query1 = "select * from SessionUser where sessionId= '" + aInt + "'";
            ResultSet sessionResult1 = stm.executeQuery(query1);
            //sessionResult1.next();
            int index=0;
             while( sessionResult1.next() ) {
                User myuser = User.getUserData(sessionResult1.getString("user"));
                 
                 usersList.add(myuser);
                
            
             }
            
             query = "select * from Session where sessionId= '" + aInt + "'";
            ResultSet sessionResult = stm.executeQuery(query);
             sessionResult.next();
             
            Session retriveSession = new Session(sessionResult.getInt("sessionId") , usersList , sessionResult.getTime("start"));
           return retriveSession;  
           
                                                   
                    } catch (SQLException ex) {
            Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    
    }

    String getSessionId() {
        return sessionId+"";
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
