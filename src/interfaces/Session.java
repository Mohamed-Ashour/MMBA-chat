/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class Session extends UnicastRemoteObject{

    private static int sessionCount = 0;
    private int sessionId;
    private List<IUser> users;
   
    
    
    
    Session(List<IUser> users) throws RemoteException{
        this.sessionId = sessionCount++;
        this.users = users;   
    }
    

    int getSessionId() {
        return sessionId;
    }
    List<IUser> getUserList() {
        return users;
    }
    
   
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
