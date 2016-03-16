/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import interfaces.IChatClient;
import interfaces.IChatServer;
import interfaces.IMessage;
import interfaces.ISession;
import interfaces.IUser;
import interfaces.User;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
    private List<ChatFrame> cf = new ArrayList<>();
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
            
            System.out.println(server);
            registry.lookup("client");
            User client = new User();
            client.connect(registry);
            return client;
        } catch (RemoteException | NotBoundException ex) {
                JOptionPane.showMessageDialog(null, "The server can't be located!");
                System.exit(0);       
        }
        return null;
    }

    /**
     *
     * @param mailList
     * @throws RemoteException
     */
    @Override
    public void getSession(List<String> mailList) throws RemoteException{
        
         server.newSession(mailList);
       
        
    }
    @Override
    public void removeClient(IUser user) throws RemoteException {
        server.removeClient(user);
    }

    @Override
    public boolean isConnected(IUser user) throws RemoteException {
        return server.isConnected(user);
    }
    
    @Override
    public void updateStateInServer(IUser user, String status) throws RemoteException {
        server.updateUserStatus(user, status);
    }

    @Override
    public void updateNotfication(String string) throws RemoteException {
        System.out.println("hello zaza");

        gui.updateNotficationArea(string);
    }

    @Override
    public void reciveMessage(IMessage msg, Integer get) throws RemoteException{
        for (ChatFrame chatFrame : cf) {
            if (chatFrame.getChatFrameId() == get) {
                chatFrame.displayMsg(msg.getFrom().getUsername() + ": " + msg.getMessageText());
            }
        }
    }
    
    @Override
    public void addChatFrame(ChatFrame frame) {
        cf.add(frame);
    }
    
  
    @Override
    public ISession getRemoteSession(int x) throws RemoteException{
        try {
            return server.getSession(x);
        } catch (RemoteException ex) {
                JOptionPane.showMessageDialog(null, "The server can't be located!");    
        }
        return null;
    }
    
    @Override
    public void sendData(String attachPath, String email) {
        FileInputStream in = null;
        try {
            File f1 = new File(attachPath);
            in = new FileInputStream(f1);
            byte [] myData= new byte[1024*1024];
            int buffer = in.read(myData);
            while(buffer>0) {
                server.sendData(f1.getName(), myData, buffer, email);
                buffer = in.read(myData);
            }
            System.out.println("File Sent Successfully");
 
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
                Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @Override
    public void recieveDate(String name, byte[] myData, int buffer){
        try {
            File f1 = new File(name);
            f1.createNewFile();
            FileOutputStream out = new FileOutputStream(f1, true);
            out.write(myData, 0, buffer);
            System.out.println("File Recieved Successfully");
            out.flush();
            out.close();
        } catch (IOException ex) {
            Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
        }
                
    }

    @Override
    public void getNotification(String text) throws RemoteException {
        gui.showNotification(text);
    }
}
