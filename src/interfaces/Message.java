
package interfaces;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Message implements IMessage, Serializable{
    
    private int messageId;
    private String messageText;
    private String messageTime;
    private IUser from;
    private int sessionId;
    private Boolean delivered;
    private static int messageCount = 0;
     public Message(){}
    
    public Message(int messageId,String messageText,User from,int sessionId,Boolean delivered){
        this.messageId= messageCount++;
        this.messageText = messageText;
        this.from = from;
        this.sessionId = sessionId;
        this.delivered=delivered;
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        messageTime = sdf.format(dt);
    }

    Message(String text, User aThis) {
        this.messageText = text;
        this.from = aThis;
    }
    
//     public Boolean addMessage() {
//        
//        try {
//            Connection db = DBConnect.getConn();
//            Statement stm = db.createStatement();
//            String query = "insert into Message values ('"+this.sessionId  + "','" + "+this.sessionId  + "','" now() + "','" + from 
//                    + "','" + messageText + "','" + delivered + "')";
//            System.out.println(query);
//            int rs = stm.executeUpdate(query);
//            System.out.println(rs);
//            return true;
//            
//        } catch (SQLException ex) {
//            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//        return false;
//        
//    }
//    
//    
//   static public List<Message> getAllMessage(User user,Session session) throws RemoteException{
//        try {
//            
//            Connection db = DBConnect.getConn();
//            Statement stm;
//            String query;
//            List<Message> msg = new ArrayList<Message>();
//            stm = db.createStatement();
//            query = "select * from Message, Session where Message.sessionId= Session.ssessionId" ;
//            ResultSet rs = stm.executeQuery(query);
//            while( rs.next() ) {
//               Message retrievedMsg = new Message(rs.getInt("id"),rs.getString("message"),
//                                                  User.getUserData(rs.getString("from")),
//                                                  rs.getTime("time"),
//                                                  Session.getSession(rs.getInt("sessionId")),
//                                                  rs.getBoolean("delivered"));
//                                                   
//
//              
//                        msg.add(retrievedMsg);
//            }
//           return msg;
//        } catch (SQLException ex) {
//            Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
//        }
//         return null;
//        
//    }
//   
//    static public Message getMessage(int messageId) throws RemoteException{
//        try {
//            Connection db = DBConnect.getConn();
//            Statement stm;
//            String query;
//            stm = db.createStatement();
//            query = "select * from Message where id= '" + messageId + "'";
//            ResultSet msgResult = stm.executeQuery(query);
//            msgResult.next();
//            return new Message(msgResult.getInt("id"),msgResult.getString("message"),
//                                                  User.getUserData(msgResult.getString("from")),
//                                                  msgResult.getTime("time"),
//                                                  Session.getSession(msgResult.getInt("sessionId")),
//                                                  msgResult.getBoolean("delivered"));
//                                                   
//                    } catch (SQLException ex) {
//            Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return null;
//    }
//    
//    static public Message getMessage(int id,Time time,Session sessionId)throws RemoteException{
//        try {
//            Connection db = DBConnect.getConn();
//            Statement stm;
//            String query;
//            stm = db.createStatement();
//            // query = "select * from Message where id= '" + messageId + "'";
//            query = "select * from Message where id = '" + id + "' and time = '" + time + "' and sessionId = '" + sessionId + "'"   ;
//            
//            ResultSet msgResult = stm.executeQuery(query);
//            msgResult.next();
//            return new Message(msgResult.getInt("id"),msgResult.getString("message"),
//                                                  User.getUserData(msgResult.getString("from")),
//                                                  msgResult.getTime("time"),
//                                                  Session.getSession(msgResult.getInt("sessionId")),
//                                                  msgResult.getBoolean("delivered"));
//                    } catch (SQLException ex) {
//            Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return null;
//    }
//    

    @Override
    public int getMessageId() throws RemoteException{
        return messageId;
    }

    @Override
    public String getMessageText() throws RemoteException{
        return messageText;
    }

    @Override
    public IUser getFrom() throws RemoteException{
        return from;
    }

    public String getMessageTime() throws RemoteException{
        return messageTime;
    }

    public Boolean getDelivered() throws RemoteException{
        return delivered;
    }

}
