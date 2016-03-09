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
 * @author ashour
 */
public interface IMessage extends Remote{

    IUser getFrom()throws RemoteException;
    int getMessageId()throws RemoteException;

    String getMessageText()throws RemoteException;
    String getMessageTime()throws RemoteException;
    Boolean getDelivered()throws RemoteException; 
    
    
}
