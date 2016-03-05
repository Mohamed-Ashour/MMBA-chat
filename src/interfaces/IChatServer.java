/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 *
 * @author bassem
 */
public interface IChatServer extends Remote{

    public static final int DEFAULT_PORT = 1099;
    public static ArrayList<User> connected = new ArrayList<>(); 
    
    /**
     *
     * @param s
     * @throws RemoteException
     */
    public static void registerClient(User s) throws RemoteException {
            connected.add(s);
            System.out.println(s.getEmail() + "Connected to the server");
    }
}
