/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import client.ChatClient;
import interfaces.IChatServer;
import interfaces.ISession;
import interfaces.IUser;
import interfaces.Message;
import interfaces.Session;
import interfaces.User;
import java.awt.GraphicsConfiguration;
import java.awt.Rectangle;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author bassem
 */
public class ChatServer extends UnicastRemoteObject implements IChatServer {

    public static Registry RMI_REGISTRY;
    public static ArrayList<IUser> connected = new ArrayList<>(); 
    public static ArrayList<ISession> sessions = new ArrayList<>(); 

    private static ServerGUI gui;
    
    public static boolean isconnected(User s){
        return connected.contains(s);
    }

    public static void main(String[] args) {

        try {

            new ChatServer();

        } catch (RemoteException ex) {
            JOptionPane.showMessageDialog(null, "The port seems to be used by another application!!" + ex.getMessage());
            System.exit(0);
        }
    }

    public ChatServer() throws RemoteException {
        RMI_REGISTRY = LocateRegistry.createRegistry(IChatServer.DEFAULT_PORT);
        RMI_REGISTRY.rebind("server", this);
        Logger.getLogger(ChatServer.class.getName()).log(Level.INFO, "Registered: {0} -> {1}", new Object[]{"Start", this.getClass().getName()});
        ChatClient client = new ChatClient();
        Session session = new Session();
        RMI_REGISTRY.rebind("client", client);
        RMI_REGISTRY.rebind("session", session);
        Logger.getLogger(ChatServer.class.getName()).log(Level.INFO, "Registered: {0} -> {1}", new Object[]{"Start", client.getClass().getName()});

        java.awt.EventQueue.invokeLater(() -> {
            gui = new ServerGUI(this);

            GraphicsConfiguration gc = gui.getGraphicsConfiguration();
            Rectangle bounds = gc.getBounds();
            gui.setLocation((int) ((bounds.width / 2) - (gui.getSize().width / 2)),
                    (int) ((bounds.height / 2) - (gui.getSize().height / 2)));
            gui.setVisible(true);
        });
    }

    @Override
    public void registerClient(IUser s) throws RemoteException {
        connected.add(s);
        System.out.println("Logged in: " + s);
        updateUsersList();
//        updateAwayLabel();
        updateOfflineLabel();
        updateOnlineLabel();
    }

    @Override
    public void removeClient(IUser s) throws RemoteException {
        connected.remove(s);
        System.out.println("Logged out: " + s);
        connected.stream().forEach((user) -> {
            System.out.println(user + "Connected to the server");
        });
        updateUsersList();
//        updateAwayLabel();
        updateOfflineLabel();
        updateOnlineLabel();
    }

    @Override
    public IUser getUser(String email) throws RemoteException {
        System.out.println(connected);
        for (IUser user : connected) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public void updateOnlineLabel() {
        gui.updateOnlineLabel();
    }

//    @Override
//    public void updateAwayLabel() {
//        gui.updateAwayLabel();
//    }
    @Override
    public void updateOfflineLabel() {
        gui.updateOfflineLabel();
    }

    @Override
    public void updateUsersList() throws RemoteException {
        gui.updateUsersList();
    }

    @Override
    public boolean isConnected(IUser s) throws RemoteException {
        for (IUser iUser : connected) {
            if (s.getEmail().equals(iUser.getEmail())) {
                return true;
            }
        }
        return false;
    }
    @Override
    public void newSession(List<String> mailList) throws RemoteException {
        List<IUser> users = new ArrayList<>();
        for (String string : mailList) {
            IUser s =  getUser(string);
            users.add(s);
        }
        Session s =  new Session(users);
        sessions.add(s);
         for (IUser user : users) {
            user.createChatFrame(mailList, s.getSessionId());
        }
        
    }

    @Override
    public ISession getSession(int id) throws RemoteException{
        for (ISession s : sessions) {
            if (s.getSessionId() == id)
                return s;
        }
        return null;
    }
    @Override
    public void updateUserStatus(IUser user, String status) throws RemoteException {
        gui.updateUsersList();
        List<User> contacts = user.getContactList();
        for (IUser myUser : connected) {
            for (IUser contact : contacts) {
                if (myUser.getEmail().equals(contact.getEmail())) {
                    myUser.sendNotifecation("\n" + user.getEmail() + " is ( " + status + " )");
                }
            }
        }

    }
    
    @Override
    public void sendData(String name, byte[] myData, int buffer, String email) {
        for (IUser myUser : connected) {
            
            try {
                if (myUser.getEmail().equals(email)) {
                    myUser.recieveData(name, myData, buffer);
                }
            } catch (RemoteException ex) {
                Logger.getLogger(ChatServer.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        
    }

    void sendMessage(String text) {
        for (IUser iUser : connected) {
            try {
                iUser.getNotified(text);
            } catch (RemoteException ex) {
                Logger.getLogger(ChatServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
