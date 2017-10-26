/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RMI;

import java.rmi.Remote; 
import java.rmi.RemoteException;

/**
 *
 * @author nash
 */
public interface ServerMethods extends Remote {
    
    public int getNumberClients() throws RemoteException;
    
    public void move(int row, int column, int typeCell) throws RemoteException;
    
    public void chatTalk(String msg) throws RemoteException;
    
    public void start(int action) throws RemoteException;
    
    public void restart(int action) throws RemoteException;
    
    public void surrender(int action) throws RemoteException;
    
    public void enableGame(boolean isDisable) throws RemoteException;
    
    public void turn(boolean turn) throws RemoteException;

    public void registerClient(String playerName) throws RemoteException;
    
}
