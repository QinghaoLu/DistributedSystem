package dsmain;

import java.rmi.Remote;
import java.rmi.RemoteException;

// import dsblockchain.Block;
import java.util.ArrayList;

public interface ClientComInterface extends Remote {

    
    // ArrayList<Block> getUpdate() throws RemoteException;
    
    // String requestVote(Block b) throws RemoteException;
    
    public void testCom(String message) throws RemoteException;

}