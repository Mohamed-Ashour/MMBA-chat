/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import interfaces.IChatServer;
import static interfaces.IChatServer.connected;
import interfaces.User;
import java.awt.GraphicsConfiguration;
import java.awt.Rectangle;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author bassem
 */



public class ChatServer extends UnicastRemoteObject implements IChatServer{
    public static Registry RMI_REGISTRY;
    public static boolean isconnected(User s){
        return connected.contains(s);
    }
    public static void main(String[] args){
        
        try {
            new ChatServer();
        } catch (RemoteException ex) {
            JOptionPane.showMessageDialog(null, "The port seems to be used by another application!!" + ex.getMessage());
            System.exit(0);        }
    }
    public ChatServer() throws RemoteException {
        RMI_REGISTRY = LocateRegistry.createRegistry(IChatServer.DEFAULT_PORT);
        ChatServer.RMI_REGISTRY.rebind("server", this);
        Logger.getLogger(ChatServer.class.getName()).log(Level.INFO, "Registered: {0} -> {1}", new Object[]{"Start", this.getClass().getName()});
        
        java.awt.EventQueue.invokeLater(() -> {
            ServerGUI frame = new ServerGUI();
            GraphicsConfiguration gc = frame.getGraphicsConfiguration();
            Rectangle bounds = gc.getBounds();
            frame.setLocation((int) ((bounds.width / 2) - (frame.getSize().width / 2)),
                    (int) ((bounds.height / 2) - (frame.getSize().height / 2)));
            frame.setVisible(true);
        });
    }
    
    public  void updateConnectedLabel(int x) {
      gui.updateConnectedLabel(x);
  }  
     public  void updateOnlineLabel(int x){
         gui.updateOnlineLabel(x);
     }
    public  void updateAwayLabel(int x){
        gui.updateAwayLabel(x);
    }
    public  void updateOfflineLabel(int x){
        gui.updateOfflineLabel(x);
    }
}
