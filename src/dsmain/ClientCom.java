package dsmain;


// import dsblockchain.Block;

import java.rmi.RemoteException;
import java.util.ArrayList;


public class ClientCom implements ClientComInterface {
    public ClientCom() throws RemoteException{
        super();
    }
    
    // @Override
    // public ArrayList<Block> getUpdate() {
        
    //     return null;
    // }

    // @Override
    // public String requestVote(Block b) {
    //     return null;
    // }

    @Override
    public void testCom(String message){
        System.out.println(message);

    }

    
    
}
