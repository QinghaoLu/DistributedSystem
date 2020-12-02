package dsmain;

import java.rmi.Remote;
import java.rmi.RemoteException;

// import dsblockchain.Block;
import java.util.ArrayList;

public interface ClientComInterface extends Remote {

    
    // ArrayList<Block> getUpdate() throws RemoteException;
    
    void requestVote(Integer ChainId, String name, int clockValue) throws RemoteException;
    
    public void testCom(String message) throws RemoteException;

}