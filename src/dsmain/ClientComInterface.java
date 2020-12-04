package dsmain;

import java.rmi.Remote;
import java.rmi.RemoteException;

// import dsblockchain.Block;
import java.util.ArrayList;

import dsblockchain.Blockchain;

public interface ClientComInterface extends Remote {

    
    ArrayList<Blockchain> getUpdate() throws RemoteException;

    void Update(ArrayList<Blockchain> chain) throws RemoteException;
    
    void requestVote(Integer ChainId, String name, int clockValue) throws RemoteException;

    void requestaddChain(String name, int clockValue) throws RemoteException;
    
    public void testCom(String message) throws RemoteException;

}