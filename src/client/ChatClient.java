/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import interfaces.IChatClient;
import interfaces.IChatServer;
import interfaces.ISession;
import interfaces.IUser;
import interfaces.Session;
import interfaces.User;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.io.Serializable;
import java.rmi.NotBoundException;
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
public class ChatClient extends UnicastRemoteObject implements Serializable, IChatClient{
    private static Registry registry;
    private ClientGUI gui;
    private IChatServer server;
    public static void main(String[] args){
        try {
            ChatClient cs = new ChatClient();
            cs.showGui();
        } catch (RemoteException ex) {
            Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Override
    public int createChatFrame(List<String> mailList) throws RemoteException {
        return gui.addChatFrame(mailList);
    }
    @Override
    public IUser getUser(String email)throws RemoteException{
        return server.getUser(email);
    }
    @Override
    public void registerClient(IUser user) throws RemoteException {
        server.registerClient(user);
    }
    /**
     *
     * @throws java.rmi.RemoteException
     * @throws HeadlessException
     */
    public ChatClient() throws RemoteException {
    }

    private void showGui() {
        User client = getRemoteObjects();   
        //Create and display the form
        java.awt.EventQueue.invokeLater(() -> {
            try {
                gui = new ClientGUI(client, this);
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
    }

    private User getRemoteObjects() {
        try {
            registry = LocateRegistry.getRegistry("localhost", IChatServer.DEFAULT_PORT);
            server = (IChatServer) registry.lookup("server");
            registry.lookup("client");
            User client = new User();
            client.connect(registry);
            return client;
        } catch (RemoteException | NotBoundException ex) {
                JOptionPane.showMessageDialog(null, "The server can't be located!");
                System.exit(0);        }
        return null;
    }

    /**
     *
     * @param users
     * @throws RemoteException
     */
    @Override
    public void getSession(List<String> mailList) throws RemoteException{
        List<IUser> users = new ArrayList<>();
        for (String string : mailList) {
            IUser s =  getUser(string);
            users.add(s);
        }
        try {
            ISession newSession = (ISession) registry.lookup("session");

            for (IUser user : users) {
                user.createChatFrame(mailList, newSession);
            }
        } catch (NotBoundException ex) {
            JOptionPane.showMessageDialog(null, "Can't get a session!!");
            System.exit(0);
        }
        
    }
    @Override
    public void removeClient(IUser user) throws RemoteException {
        server.removeClient(user);
    }

    @Override
    public boolean isConnected(IUser user) throws RemoteException {
        return server.isConnected(user);
    }
}
