package dsmain;

// import dsblockchain.Block;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class ClientCom implements ClientComInterface {

    ArrayList<Integer> Counters = new ArrayList<Integer>();
    ArrayList<String> tokens = new ArrayList<String>();
    ArrayList<String> Quene = new ArrayList<String>();
    ArrayList<Clock> clockList = new ArrayList<Clock>();

    public ClientCom() throws RemoteException {
        super();
        // System.out.println(tokens.get(0));
    }
    // @Override
    // public ArrayList<Block> getUpdate() {

    // return null;
    // }
    @Override
    public void requestVote(Integer ChainId, String name, int clockValue) {
        // try{
        if ((tokens.get(ChainId).equals("Wanted") && clockList.get(ChainId).getValue() > clockValue) || tokens.get(ChainId).equals("Held")) {

            Quene.add(name);
            while ((tokens.get(ChainId).equals("Wanted") && clockList.get(ChainId).getValue() > clockValue)|| tokens.get(ChainId).equals("Held")) {
                    System.out.println("someMofo Invoking me"+tokens.get(ChainId));    
                    try {
                            Thread.sleep(5000);
                    } catch (InterruptedException e) {
                                e.printStackTrace();
                    }
                    // tokens.get(ChainId) = "shit";
                    tokens.set(ChainId, "Released");
                   
                };
                
                clockList.get(ChainId).receiveEvent(clockValue);
                
                while(Quene.size() > 0){
                    if(Quene.get(0).equals(name)){
                        Quene.remove(name);
                        clockList.get(ChainId).sendEvent();
                        return;
                    }
                }
                
            }else{
                clockList.get(ChainId).receiveEvent(clockValue);
                clockList.get(ChainId).sendEvent();
                return;   
            }

        // }catch(Exception e){
        //     System.out.println("mofo just disappeared");
        // }      
        
    }

    @Override
    public void testCom(String message){
        System.out.println(message);

    }

    
    
}
