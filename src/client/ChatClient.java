/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import interfaces.IChatServer;
import interfaces.User;
import java.awt.GraphicsConfiguration;
import java.awt.Rectangle;
import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bassem
 */
public class ChatClient implements Serializable{
    private Registry registry;

    public void connect(String host) throws RemoteException{
        this.registry = LocateRegistry.getRegistry(host, IChatServer.DEFAULT_PORT);
        try {
            ((IChatServer) this.registry.lookup("client")).register(this);
        } catch (NotBoundException ex) {
            Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void main(String[] args){
        try {
            final User client = new User();
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
            java.awt.EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        ClientGUI frame = new ClientGUI(client);
                        GraphicsConfiguration gc = frame.getGraphicsConfiguration();
                        Rectangle bounds = gc.getBounds();
                        frame.setLocation((int) ((bounds.width / 2) - (frame.getSize().width / 2)),
                                (int) ((bounds.height / 2) - (frame.getSize().height / 2)));
                        frame.setVisible(true);
                    } catch (RemoteException ex) {
                        Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
        } catch (RemoteException ex) {
            Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
