/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import client.ChatFrame;
import java.rmi.Remote;
import java.util.List;

/**
 *
 * @author bassem
 */
public interface IChatClient extends Remote{

    void createChatFrame(ChatFrame chatFrame, List<String> mailList);
    
}
