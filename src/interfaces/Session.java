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
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;




public class Session implements Serializable {

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
    
    /*
    private static Date getDate()
    {
               Calendar now = Calendar.getInstance();
                int year = now.get(Calendar.YEAR);
                int month = now.get(Calendar.MONTH) + 1; // Note: zero based!
                int day = now.get(Calendar.DAY_OF_MONTH);
                int hour = now.get(Calendar.HOUR_OF_DAY);
                int minute = now.get(Calendar.MINUTE);
                int second = now.get(Calendar.SECOND);
                
                
                    
                System.out.printf("%d-%02d-%02d %02d:%02d:%02d", year, month, day, hour, minute, second);
                
                return myDate;
    }*/
    
    
    public static Boolean initSession( List<String> mails) throws SQLException
    {
        try { 
                Statement stm = null;

                String query;

                Connection db = DBConnect.getConn();    
                //Date d = new Date("FT");
                stm = db.createStatement();
                
                
                Calendar now = Calendar.getInstance();
                int year = now.get(Calendar.YEAR);
                int month = now.get(Calendar.MONTH) + 1; // Note: zero based!
                int day = now.get(Calendar.DAY_OF_MONTH);
                int hour = now.get(Calendar.HOUR_OF_DAY);
                int minute = now.get(Calendar.MINUTE);
                int second = now.get(Calendar.SECOND);
                
                String date  ;
                    
                date =  String.valueOf(year)+"-" + String.valueOf(month) +"-" + String.valueOf(day)+" " + String.valueOf(hour)+":" + String.valueOf(minute)+":" + String.valueOf(second) ;
               
                
                
                
                
                

                //  Get The Last Sessio To Iincrement The Session ID
                query = "select * from Session order by sessionId DESC limit 1";
                ResultSet lastSession = stm.executeQuery(query);
                 lastSession.next();
                 
                 
                 Integer lastSessionIncrement ;
                 
                 
                 if (!lastSession.next() ) {
                      //  System.out.println("no data");
                      lastSessionIncrement = 1;
                    } else {
                     lastSessionIncrement = Integer.parseInt(lastSession.getString("sessionId")) + 1;
            }
                 // Insert To Session Table
                 String sql = "INSERT INTO Session " +
                       "VALUES ('"+ lastSessionIncrement +"','"+date+"', '0000-00-00 00:00:00')";
                 stm.executeUpdate(sql);
                 
                 
                 
                 
                 //int usersSize = mails.size();
                 for(int i=0 ; i < mails.size(); i++)
                 {
                     String insertUser = "INSERT INTO SessionUser " +
                       "VALUES ('"+ lastSessionIncrement +"','"+mails.get(i)+"')";
                     stm.executeUpdate(insertUser);
                 }
             
             } catch (SQLException ex) {
            Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
     public static void main(String[] args){
        Session s = new Session();
        
        //s.initSession(1);
       // Date d = new Date();
/*
                Calendar now = Calendar.getInstance();
                int year = now.get(Calendar.YEAR);
                int month = now.get(Calendar.MONTH) + 1; // Note: zero based!
                int day = now.get(Calendar.DAY_OF_MONTH);
                int hour = now.get(Calendar.HOUR_OF_DAY);
                int minute = now.get(Calendar.MINUTE);
                int second = now.get(Calendar.SECOND);
                
                String date  ;
                    
                date =  String.valueOf(year)+"-" + String.valueOf(month) +"-" + String.valueOf(day)+" " + String.valueOf(hour)+":" + String.valueOf(minute)+":" + String.valueOf(second) ;
               
*/
//System.out.printf("%d-%02d-%02d %02d:%02d:%02d", year, month, day, hour, minute, second);
//System.out.println(date);
         //SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd"); 
        //  System.out.println(d);
        
    }
}
