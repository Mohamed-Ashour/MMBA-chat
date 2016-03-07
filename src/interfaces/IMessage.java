/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

/**
 *
 * @author ashour
 */
public interface IMessage {

    User getFrom();

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
    int getMessageId();

    String getMessageText();
    String getMessageTime();
    Boolean getDelivered(); 
    
    
}
