/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author bassem
 */
public interface IChatClient extends Remote{

    List<User> getContactList() throws RemoteException;

    String getEmail() throws RemoteException;

    String getGender() throws RemoteException;

    String getPassword() throws RemoteException;

    String getStatus() throws RemoteException;

    String getUsername() throws RemoteException;

    void recieveMessage() throws RemoteException;

    void sendMessage() throws RemoteException;
    
}
