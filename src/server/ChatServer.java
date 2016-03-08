/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import client.ChatClient;
import interfaces.IChatServer;
import interfaces.IUser;
import interfaces.Session;
import interfaces.User;
import java.awt.GraphicsConfiguration;
import java.awt.Rectangle;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author bassem
 */



public class ChatServer extends UnicastRemoteObject implements IChatServer{
    public static Registry RMI_REGISTRY;
    public static ArrayList<IUser> connected = new ArrayList<>(); 
    private static ServerGUI gui;
    public static boolean isconnected(User s){
        return connected.contains(s);
    }
    public static void main(String[] args){
        
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
        RMI_REGISTRY.rebind("client", client);
        Logger.getLogger(ChatServer.class.getName()).log(Level.INFO, "Registered: {0} -> {1}", new Object[]{"Start", client.getClass().getName()});
        Session session = new Session();
        RMI_REGISTRY.rebind("session", session);
        Logger.getLogger(ChatServer.class.getName()).log(Level.INFO, "Registered: {0} -> {1}", new Object[]{"Start", session.getClass().getName()});

        java.awt.EventQueue.invokeLater(() -> {
            gui = new ServerGUI();
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
    }
    
    @Override
    public void removeClient(IUser s) throws RemoteException {
        connected.remove(s);
        System.out.println("Logged out: " + s);
    }
    @Override
    public IUser getUser(String email) throws RemoteException {
        for (IUser user : connected)
            if (user.getEmail().equals(email))
                return user;
        return null;
    }
    @Override
    public  void updateConnectedLabel(int x) {
      gui.updateConnectedLabel(x);
  }  
    @Override
     public  void updateOnlineLabel(int x){
         gui.updateOnlineLabel(x);
     }
    @Override
    public  void updateAwayLabel(int x){
        gui.updateAwayLabel(x);
    }
    @Override
    public  void updateOfflineLabel(int x){
        gui.updateOfflineLabel(x);
    }

    @Override
    public boolean isConnected(IUser s) throws RemoteException {
        for (IUser iUser : connected) {
            if (s.getEmail().equals(iUser.getEmail()))
                return true;
        }
        return false;
    }
}
