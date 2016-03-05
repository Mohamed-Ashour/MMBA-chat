/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import interfaces.IChatServer;
import interfaces.User;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author bassem
 */
public class ChatClient implements Serializable{
    private static Registry registry;
    private ClientGUI gui;
    
    public static void main(String[] args){   
        new ChatClient();
    }

    /**
     *
     * @throws HeadlessException
     */
    public ChatClient() throws HeadlessException {
        try {
            registry = LocateRegistry.getRegistry("localhost", IChatServer.DEFAULT_PORT);
            
            registry.lookup("server");
            //System.out.println("Connected to server :)");
            User client = new User();
            client.connect("localhost",registry);
            //System.out.println("Obtained Remote User  :)");

            /* Set the Nimbus look and feel */
            //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
            /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
            * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
            */
            try {
                for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
                java.util.logging.Logger.getLogger(ChatClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
            //</editor-fold>
        
            //Create and display the form
            java.awt.EventQueue.invokeLater(() -> {
                try {
                    gui = new ClientGUI(client);
                    GraphicsConfiguration gc = gui.getGraphicsConfiguration();
                    Rectangle bounds = gc.getBounds();
                    gui.setLocation((int) ((bounds.width / 2) - (gui.getSize().width / 2)),
                            (int) ((bounds.height / 2) - (gui.getSize().height / 2)));
                    gui.setVisible(true);
                } catch (RemoteException ex) {
                    JOptionPane.showMessageDialog(null, "The server can't be located!");
                    System.exit(0);
                }
            });
        } catch (RemoteException ex) {
            JOptionPane.showMessageDialog(null, "The server can't be located!");
            System.exit(0);
        } catch (NotBoundException ex) {
            Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
