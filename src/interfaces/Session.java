/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import server.DBConnect;

public class Session extends UnicastRemoteObject implements ISession{

    private static int sessionCount = 0;
    private int sessionId;
    private String sessionStart;
    private String sessionEnd;
    private List<IUser> users;
   
    public Session() {
        
    }
    
    
    public Session(List<IUser> users) throws RemoteException{
    private List<IMessage> messages;
    static private Statement stm;
    static private String query;
    static private Connection db = DBConnect.getConn();

    
    
    
    Session(List<IUser> users) throws RemoteException{
        messages = new ArrayList<>();
        this.sessionId = sessionCount++;
        this.users = users;
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sessionStart = sdf.format(dt);
    }
    

    int getSessionId() {
        return sessionId;
    }
    
    List<IUser> getUserList() {
        return users;
    }
    
    public void addMessage(IMessage message) {
        messages.add(message);
    }
    
    void saveSession() throws RemoteException, SQLException, FileNotFoundException, IOException {
      
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sessionEnd = sdf.format(dt);
        
        stm = db.createStatement();
        
        // insert session in database
        query = "insert into Session (sessionId, start, end) values ('"
                + this.sessionId + "','" + this.sessionStart + "','" + this.sessionEnd + "')";
        int rs = stm.executeUpdate(query);
        
        // link users to session
        for (IUser user : users) {
            String email = user.getEmail();    
            query = "insert into SessionUser (sessionId, user) values ('"
                    + this.sessionId + "','" + email + "')";
            rs = stm.executeUpdate(query);
        }
        
        String sessionMessages = "";
        
        // insert messages in database
        for (IMessage message : messages) {
            // make string of messages to save it
            sessionMessages += message.getFrom().getEmail() + ": \n" + message.getMessageText() + "\n";
            System.out.println(sessionMessages);
            
            
            
            
            // insert messages in database
            query = "insert into Message (id, sessionId, time, sender, message, delivered) values ('"
                    + message.getMessageId() + "','" + this.sessionId + "','" 
                    + message.getMessageTime() + "','" + message.getFrom().getEmail() + "','"  
                    + message.getMessageText() + "', true)";
            System.out.println(query);
            rs = stm.executeUpdate(query);
        }
        
        FileOutputStream fos = new FileOutputStream("myfile");
        byte[] messageBytes = sessionMessages.getBytes();
        fos.write(messageBytes);
        fos.close();
        
    }
    
//    public static void main(String[] args) throws RemoteException, SQLException, IOException {
//        ArrayList<IUser> users = new ArrayList<>();
//        User ahmed = new User();
//        ahmed.setEmail("e.mohamed@gmail.com");
//        User ali = new User();
//        ali.setEmail("ali@gmail.com");
//        users.add(ahmed);
//        Session s = new Session(users);
//        Message m = new Message( 1, "hello", ali, 5, true ); 
//        s.addMessage(m);
//        m = new Message( 1, "hi", ahmed, 5, true ); 
//        s.addMessage(m);
//        s.saveSession();
//        
//    }
    
   
//    public static Boolean initSession( List<String> mails) throws SQLException
//    {
//        try { 
//                Statement stm = null;
//
//                String query;
//
//                Connection db = DBConnect.getConn();    
//                //Date d = new Date("FT");
//                stm = db.createStatement();
//                
//                
//                Calendar now = Calendar.getInstance();
//                int year = now.get(Calendar.YEAR);
//                int month = now.get(Calendar.MONTH) + 1; // Note: zero based!
//                int day = now.get(Calendar.DAY_OF_MONTH);
//                int hour = now.get(Calendar.HOUR_OF_DAY);
//                int minute = now.get(Calendar.MINUTE);
//                int second = now.get(Calendar.SECOND);
//                
//                String date  ;
//                    
//                date =  String.valueOf(year)+"-" + String.valueOf(month) +"-" + String.valueOf(day)+" " + String.valueOf(hour)+":" + String.valueOf(minute)+":" + String.valueOf(second) ;
//
//                //  Get The Last Sessio To Iincrement The Session ID
//                query = "select * from Session order by sessionId DESC limit 1";
//                ResultSet lastSession = stm.executeQuery(query);
//                 //lastSession.next();
//                 
//                 
//                 Integer lastSessionIncrement  ;
//                 
//                 
//                 if (lastSession.next()) {
//                   //  System.out.println(" data");
//                     lastSessionIncrement = (lastSession.getInt("sessionId")) + 1;
//                 } else {
//                     System.out.println("no data");
//                     lastSessionIncrement = 1;
//                 }
//                 
//                // System.out.println(lastSessionIncrement);
//                 // Insert To Session Table
//                 String sql = "INSERT INTO Session " +
//                       "VALUES ('"+ lastSessionIncrement +"','"+date+"', '0000-00-00 00:00:00')";
//                 stm.executeUpdate(sql);
//                 
//                 
//                 
//                 
//                 //int usersSize = mails.size();
//                 for(int i=0 ; i < mails.size(); i++)
//                 {
//                     String insertUser = "INSERT INTO SessionUser " +
//                       "VALUES ('"+ lastSessionIncrement +"','"+mails.get(i)+"')";
//                     stm.executeUpdate(insertUser);
//                 }
//                 return true;
//             
//             } catch (SQLException ex) {
//            Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return null;
//    }
}
