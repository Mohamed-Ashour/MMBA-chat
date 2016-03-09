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
public interface ISession extends Remote{

    
    public void sendToAll(IMessage message) throws RemoteException;
    public void setUserList(List<IUser> users) throws RemoteException;
    public int getSessionId() throws RemoteException;
}
