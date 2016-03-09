package interfaces;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import server.DBConnect;

/**
 *
 * @author ashour
 */
public class User extends UnicastRemoteObject implements IUser {

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
    static private HashMap<Integer, Integer> sessions = new HashMap<>();
    private IChatClient client;

    public User(String email, String username, String name, String status,
            String password, String country, String gender) throws RemoteException {
        this.email = email;
        this.username = username;
        this.name = name;
        this.status = status;
        this.password = password;
        this.country = country;
        this.gender = gender;
    }

    @Override
    public void setGui(IChatClient gui) {
        this.client = gui;
        System.out.println(gui + " As GUI");
    }

    public User(String email, String password) throws RemoteException {
        this.email = email;
        this.password = password;
    }

    public User(HashMap<String, String> userInfo) throws RemoteException {
        email = userInfo.get("email");
        username = userInfo.get("username");
        name = userInfo.get("name");
        status = "offline";
        password = userInfo.get("password");
        country = userInfo.get("country");
        gender = userInfo.get("gender");

    }

    public User() throws RemoteException {
    }

    @Override
    public Boolean isExist(String searchEmail) throws RemoteException {
        try {

            stm = db.createStatement();
            query = "select * from User where email = '" + searchEmail + "'";
            ResultSet rs = stm.executeQuery(query);
            return (rs.next());

        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    @Override
    public Boolean login() throws RemoteException {

        try {

            stm = db.createStatement();
            query = "select * from User where email = '" + email + "' and password = '" + password + "'";
            ResultSet rs = stm.executeQuery(query);
            return (rs.next());

        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;

    }

    @Override
    public void logout() throws RemoteException {
        setStatus("offline");
        changeStatus("offline");
    }

    @Override
    public Boolean add() throws RemoteException {

        try {
            // student insertion
            stm = db.createStatement();
            query = "insert into User (name, username, email, password,"
                    + " country, gender, status) values ('"
                    + name + "','" + username + "','" + email + "','" + password
                    + "','" + country + "','" + gender + "','" + status + "')";
            int rs = stm.executeUpdate(query);
            return true;

        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;

    }

    @Override
    public User completeInfo() throws RemoteException {
        changeStatus("online");
        return getUserData(this.email);
    }

    @Override
    public User findUser(String searchEmail) throws RemoteException {
        if (isExist(searchEmail) && !searchEmail.equals(email)) {
            return getUserData(searchEmail);
        }

        return null;
    }

    static public User getUserData(String searchEmail) throws RemoteException {
        try {

            stm = db.createStatement();
            query = "select * from User where email = '" + searchEmail + "'";
            ResultSet userResult = stm.executeQuery(query);
            userResult.next();

            return new User(userResult.getString("email"),
                    userResult.getString("username"),
                    userResult.getString("name"),
                    userResult.getString("status"),
                    userResult.getString("password"),
                    userResult.getString("country"),
                    userResult.getString("gender"));

        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    static public String getUserStatus(String username) throws RemoteException {
        try {

            stm = db.createStatement();
            query = "select status from User where username = '" + username + "'";
            ResultSet userResult = stm.executeQuery(query);
            userResult.next();

            return userResult.getString("status");

        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    static public String getUserEmail(String username) throws RemoteException {
        try {

            stm = db.createStatement();
            query = "select email from User where username = '" + username + "'";
            ResultSet userResult = stm.executeQuery(query);
            userResult.next();

            return userResult.getString("email");

        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    static public List<User> getAllUsers() throws RemoteException {

        try {
            List<User> users = new ArrayList<>();
            stm = db.createStatement();
            query = "select * from User";
            ResultSet userResult = stm.executeQuery(query);
            while (userResult.next()) {
                User retrievedUser = new User(userResult.getString("email"),
                        userResult.getString("username"),
                        userResult.getString("name"),
                        userResult.getString("status"),
                        userResult.getString("password"),
                        userResult.getString("country"),
                        userResult.getString("gender"));
                users.add(retrievedUser);
            }

            return users;

        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    @Override
    public List<User> getContactList() throws RemoteException {
        try {
            List<User> contacts = new ArrayList<>();
            stm = db.createStatement();
            query = "select contact as email from Contact where user = '" + email
                    + "' union select user as email from Contact where contact = '"
                    + email + "'";
            ResultSet userResult = stm.executeQuery(query);

            while (userResult.next()) {

                User retrievedUser = User.getUserData(userResult.getString("email"));
                contacts.add(retrievedUser);

            }
            return contacts;

        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    @Override
    public Boolean isContact(String contactEmail) {

        try {
            // check if user is friend with contact
            query = "select * from Contact";
            ResultSet contactsResult = stm.executeQuery(query);
            while (contactsResult.next()) {
                String contactFound = contactsResult.getString("contact");
                String userFound = contactsResult.getString("user");

                if ((userFound.equals(email) && contactFound.equals(contactEmail))
                        || (userFound.equals(contactEmail) && contactFound.equals(email))) {
                    return true;
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    @Override
    public Boolean addContact(String contactEmail) throws RemoteException {
        if (isExist(contactEmail)) {

            if (!isContact(contactEmail)) {

                try {
                    stm = db.createStatement();
                    query = "insert into Contact (user, contact) values ('"
                            + email + "','" + contactEmail + "')";
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

    @Override
    public String getGender() {
        return gender;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public void setStatus(String status) throws RemoteException {
        this.status = status;
    }

    @Override
    public Boolean changeStatus(String status) throws RemoteException {

        try {
            stm = db.createStatement();
            query = "update User  set status = '" + status
                    + "' where email = '" + email + "'";
            stm.executeUpdate(query);
            return true;

        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    @Override
    public void createChatFrame(List<String> mailList, int newSession) throws RemoteException {
        int chatFrameId = client.createChatFrame(mailList);
        sessions.put(newSession, chatFrameId);
    }

    @Override
    public void sendMessage(String text, ISession s) throws RemoteException {
        Message msg= new Message(text, this);
        s.sendToAll(msg);
    }

    @Override
    public void recieveMessage(ISession s, IMessage msg) throws RemoteException {
        if (sessions.containsKey(s.getSessionId())) {
            client.reciveMessage(msg, sessions.get(s.getSessionId()));
        }
    }

    @Override
    public void addSession(ISession s) {
    }

    @Override
    public void connect(Registry r) throws RemoteException {
        try {
            r.lookup("user");
        } catch (NotBoundException ex) {
            JOptionPane.showMessageDialog(null, "The service can't be located!!");
            System.exit(0);
        }
    }

    @Override
    public int getSessionId(int chatFrameId) throws RemoteException {
        if (sessions.containsValue(chatFrameId)) {
             Set<Integer> keys = sessions.keySet();
             for (Integer key : keys) {
                if (sessions.get(key) == chatFrameId)
                    return key;
            }
        }
        return -1;
    }

   

    public void sendNotifecation(String string) throws RemoteException {
        System.out.println("hello hanafi");

        client.updateNotfication(string);
    }
}
