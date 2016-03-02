/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import client.ChatClient;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author bassem
 */
public interface IChatServer extends Remote{

    public static final int DEFAULT_PORT = 1099;

    Boolean add() throws RemoteException;

    Boolean addContact(String contactEmail) throws RemoteException;

    Boolean changeStatus(String status) throws RemoteException;

    User completeInfo() throws RemoteException;

    User findUser(String searchEmail) throws RemoteException;

    List<User> getContactList() throws RemoteException;

    void initSession(List<User> users) throws RemoteException;

    Boolean isContact(String contactEmail) throws RemoteException;

    Boolean isExist(String searchEmail) throws RemoteException;

    Boolean login() throws RemoteException;

    void logout() throws RemoteException;

    void setStatus(String status) throws RemoteException;

    public void register(ChatClient aThis) throws RemoteException;
    
}
