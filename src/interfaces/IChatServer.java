/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author bassem
 */
public interface IChatServer extends Remote{

    public static final int DEFAULT_PORT = 1099;
    
    /**
     *
     * @param s
     * @throws RemoteException
     */
    
    
    
    public void registerClient(IUser s) throws RemoteException;
    public void removeClient(IUser s) throws RemoteException;
    public IUser getUser(String email) throws RemoteException;
    public boolean isConnected(IUser s) throws RemoteException;
    public void updateOnlineLabel() throws RemoteException;
//    public void updateAwayLabel ()throws RemoteException;
    public void updateOfflineLabel() throws RemoteException;
    public void updateUsersList() throws RemoteException;
    public void updateUserStatus(IUser user, String status) throws RemoteException;
}
