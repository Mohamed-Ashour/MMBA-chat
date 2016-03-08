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


    public int createChatFrame(List<String> mailList) throws RemoteException;
    public void getSession(List<String> mailList) throws RemoteException;
    public IUser getUser(String email)throws RemoteException;
    public void registerClient(IUser user) throws RemoteException;
    public void removeClient(IUser user) throws RemoteException;
    public boolean isConnected(IUser user) throws RemoteException;
}
