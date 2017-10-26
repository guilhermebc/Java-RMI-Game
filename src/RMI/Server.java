/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RMI;

import java.awt.HeadlessException;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import ludusbraille.LudusBrailleGame;

/**
 *
 * @author guilhermecosta
 */
public class Server extends UnicastRemoteObject implements ServerMethods {

    private static final long serialVersionUID = 1L;
    private Registry registry;
    private List<ClientMethods> listClients;

    public Server() throws RemoteException {
        super();
        this.listClients = new ArrayList<ClientMethods>();
    }

    @Override
    public int getNumberClients() throws RemoteException {
        return listClients.size();
    }

    @Override
    public void chatTalk(String msg) throws RemoteException {
        for (ClientMethods c : listClients) {
            c.chatTalk(msg);
        }
    }

    @Override
    public void move(int row, int column, int typeCell) throws RemoteException {
        for (ClientMethods c : listClients) {
            c.move(row, column, typeCell);
        }
    }

    @Override
    public void start(int action) throws RemoteException {
        for (ClientMethods c : listClients) {
            c.start(action);
        }
    }

    @Override
    public void restart(int action) throws RemoteException {
        for (ClientMethods c : listClients) {
            c.restart(action);
        }
    }

    @Override
    public void surrender(int action) throws RemoteException {
        for (ClientMethods c : listClients) {
            c.restart(action);
        }
    }
    
    @Override
    public void enableGame(boolean isDisable) throws RemoteException {
        for (ClientMethods c : listClients) {
            c.enableGame(isDisable);
        }
    }

    @Override
    public void turn(boolean turn) throws RemoteException {
        for (ClientMethods c : listClients) {
            c.turn(turn);
        }
    }

    public static void main(String[] args) {
        try {
            String serverName = JOptionPane.showInputDialog(null, "Bem vindo Servidor! Digite o nome do servidor:");
            String port = JOptionPane.showInputDialog(null, "Digite a porta:");
            int portNumber = Integer.parseInt(port);

            System.out.println("Iniciando o servidor de nomes...");
            Server obj = new Server();
            obj.registry = LocateRegistry.createRegistry(portNumber);
            obj.registry.rebind(serverName, obj);
            System.out.println("Sucesso! Servidor: " + serverName + " registrado na porta: " + portNumber + "!");
        } catch (HeadlessException | NumberFormatException | RemoteException e) {
        }
    }

    @Override
    public void registerClient(String playerName) throws RemoteException, AccessException {
        try {
            ClientMethods clientt = (ClientMethods) registry.lookup(playerName);
            if (listClients.size() < 2) {
            listClients.add(clientt);
            System.out.println("Cliente registrado! Quantidade: " + listClients.size());
        } else {
            System.out.println("Capacidade máxima atingida... tente mais tarde.");
        }
        } catch (NotBoundException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
}
