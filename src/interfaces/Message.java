
package interfaces;

import interfaces.Session;
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
import server.DBConnect;


public class Message implements Serializable{
    
    private int messageId;
    private String messageText;
    private User from;
    private Time time;
    private Session session;
    private Boolean delivered;
    
     public Message(){}
    
    public Message(int messageId,String messageText,User from,Time time,Session session,Boolean delivered){
        this.messageId=messageId;
        this.messageText = messageText;
        this.from = from;
        this.time = time;
        this.session = session;
        this.delivered=delivered;
    }
    
     public Boolean addMessage() {
        
        try {
            Connection db = DBConnect.getConn();
            Statement stm;
            String query;
            stm = db.createStatement();
            query = "insert into Message ( sessionId, time,from,"
                    + " message, delivered) values ('"
                    + session.getSessionId() + "','" + time + "','" + from 
                    + "','" + messageText + "','" + delivered + "')";
            System.out.println(query);
            int rs = stm.executeUpdate(query);
            System.out.println(rs);
            return true;
            
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
        
    }
    
    
   static public List<Message> getAllMessage(User user,Session session) throws RemoteException{
        try {
            
            Connection db = DBConnect.getConn();
            Statement stm;
            String query;
            List<Message> msg = new ArrayList<Message>();
            stm = db.createStatement();
            query = "select * from Message, Session where Message.sessionId= Session.ssessionId" ;
            ResultSet rs = stm.executeQuery(query);
            while( rs.next() ) {
               Message retrievedMsg = new Message(rs.getInt("id"),rs.getString("message"),
                                                  User.getUserData(rs.getString("from")),
                                                  rs.getTime("time"),
                                                  Session.getSession(rs.getInt("sessionId")),
                                                  rs.getBoolean("delivered"));
                                                   

              
                        msg.add(retrievedMsg);
            }
           return msg;
        } catch (SQLException ex) {
            Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
        }
         return null;
        
    }
   
    static public Message getMessage(int messageId) throws RemoteException{
        try {
            Connection db = DBConnect.getConn();
            Statement stm;
            String query;
            stm = db.createStatement();
            query = "select * from Message where id= '" + messageId + "'";
            ResultSet msgResult = stm.executeQuery(query);
            msgResult.next();
            return new Message(msgResult.getInt("id"),msgResult.getString("message"),
                                                  User.getUserData(msgResult.getString("from")),
                                                  msgResult.getTime("time"),
                                                  Session.getSession(msgResult.getInt("sessionId")),
                                                  msgResult.getBoolean("delivered"));
                                                   
                    } catch (SQLException ex) {
            Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    static public Message getMessage(int id,Time time,Session sessionId)throws RemoteException{
        try {
            Connection db = DBConnect.getConn();
            Statement stm;
            String query;
            stm = db.createStatement();
            // query = "select * from Message where id= '" + messageId + "'";
            query = "select * from Message where id = '" + id + "' and time = '" + time + "' and sessionId = '" + sessionId + "'"   ;
            
            ResultSet msgResult = stm.executeQuery(query);
            msgResult.next();
            return new Message(msgResult.getInt("id"),msgResult.getString("message"),
                                                  User.getUserData(msgResult.getString("from")),
                                                  msgResult.getTime("time"),
                                                  Session.getSession(msgResult.getInt("sessionId")),
                                                  msgResult.getBoolean("delivered"));
                    } catch (SQLException ex) {
            Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
