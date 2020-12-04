package dsmain;

// import dsblockchain.Block;

import java.rmi.RemoteException;
import java.util.ArrayList;

import dsblockchain.Blockchain;

public class ClientCom implements ClientComInterface {

    ArrayList<Blockchain> chains = new ArrayList<Blockchain>();
    String tokens = "Released";   
    Clock clock = new Clock();


    ArrayList<String> Quene = new ArrayList<String>();

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
        if ((tokens.equals("WantV"+ChainId) && clock.getValue() > clockValue) || tokens.equals("Held"+ChainId)) {

            Quene.add(name);
            while ((tokens.equals("WantV"+ChainId) && clock.getValue() > clockValue)
                    || tokens.equals("Held"+ChainId)) {
                System.out.println("someMofo Invoking me" + tokens);
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            ;

            clock.receiveEvent(clockValue);

            while (Quene.size() > 0) {
                if (Quene.get(0).equals(name)) {
                    Quene.remove(name);
                    clock.sendEvent();
                    return;
                }
            }

        } else {
            clock.receiveEvent(clockValue);
            clock.sendEvent();
            return;
        }

        // }catch(Exception e){
        // System.out.println("mofo just disappeared");
        // }

    }

    @Override
    public void testCom(String message) {
        System.out.println(message);

    }

    @Override
    public ArrayList<Blockchain> getUpdate(){
        return chains;
    }

    @Override
    public void requestaddChain(String name, int clockValue) throws RemoteException {
        if ((tokens.equals("WantA") && clock.getValue() > clockValue) || tokens.equals("Held")) {

            Quene.add(name);
            while ((tokens.equals("WantA") && clock.getValue() > clockValue) || tokens.equals("Held")) {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

            }
            

            clock.receiveEvent(clockValue);

            while (Quene.size() > 0) {
                if (Quene.get(0).equals(name)) {
                    Quene.remove(name);
                    clock.sendEvent();
                    return;
                }
            }

        } else {
            clock.receiveEvent(clockValue);
            clock.sendEvent();
            return;
        }
        

    }

    @Override
    public void Update(ArrayList<Blockchain> chains) throws RemoteException {
        this.chains = chains;

    }

    
    
}
