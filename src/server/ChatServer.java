/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import client.ChatClient;
import interfaces.IChatServer;
import interfaces.IUser;
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
import javax.sound.midi.SysexMessage;
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
           
             ChatServer server = new ChatServer();
            server.updateConnectedLabel();
            server.updateOnlineLabel();
            server.updateOfflineLabel();
            server.updateAwayLabel();
            
        } catch (RemoteException ex) {
            JOptionPane.showMessageDialog(null, "The port seems to be used by another application!!" + ex.getMessage());
            System.exit(0);        }
    }
    public ChatServer() throws RemoteException {
        RMI_REGISTRY = LocateRegistry.createRegistry(IChatServer.DEFAULT_PORT);
        RMI_REGISTRY.rebind("server", this);
        ChatClient client = new ChatClient();
        RMI_REGISTRY.rebind("client", client);
        Logger.getLogger(ChatServer.class.getName()).log(Level.INFO, "Registered: {0} -> {1}", new Object[]{"Start", this.getClass().getName()});
        
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
        connected.stream().forEach((user) -> {
            System.out.println(user + "Connected to the server");
        });
        
    }
    @Override
    public IUser getUser(String email) throws RemoteException {
        System.out.println(connected);
        for (IUser user : connected)
            if (user.getEmail().equals(email))
                return user;
        return null;
    }
    @Override
    public  void updateConnectedLabel() {
      gui.updateConnectedLabel();
  }  
    @Override
     public  void updateOnlineLabel(){
         gui.updateOnlineLabel();
     }
    @Override
    public  void updateAwayLabel(){
        gui.updateAwayLabel();
    }
    @Override
    public  void updateOfflineLabel(){
        gui.updateOfflineLabel();
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
