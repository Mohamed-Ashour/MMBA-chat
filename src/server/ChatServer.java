/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import interfaces.IChatClient;
import interfaces.IChatServer;
import java.awt.GraphicsConfiguration;
import java.awt.Rectangle;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bassem
 */
public class ChatServer {
    public static Registry RMI_REGISTRY; 
    
    public static void main(String[] args){
        try {
            RMI_REGISTRY = LocateRegistry.createRegistry(IChatServer.DEFAULT_PORT);
        } catch (RemoteException ex) {
            Logger.getLogger(ChatServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(() -> {
            ServerGUI frame = new ServerGUI();
            GraphicsConfiguration gc = frame.getGraphicsConfiguration();
            Rectangle bounds = gc.getBounds();
            frame.setLocation((int) ((bounds.width / 2) - (frame.getSize().width / 2)),
                    (int) ((bounds.height / 2) - (frame.getSize().height / 2)));
            frame.setVisible(true);
        });
    }

}
