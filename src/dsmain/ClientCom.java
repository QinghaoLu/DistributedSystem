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
 
    @Override
    public void requestVote(Integer ChainId, String name, int clockValue) {
        // try{
        if ((tokens.equals("WantV"+ChainId) && clock.getValue() < clockValue) || tokens.equals("Held"+ChainId)) {

            Quene.add(name);
            while ((tokens.equals("WantV"+ChainId) && clock.getValue() < clockValue)
                    || tokens.equals("Held"+ChainId)) {
                System.out.println(name+" Wants to vote I'm "+tokens);
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
        for (Blockchain i : chains) {
            if(!i.isChainValid()){
                return new ArrayList<Blockchain>();
            }

        }
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
        // if(this.chains.size() < chains.size())
        // this.chains = chains;
        if(this.chains.size() < chains.size()){
            for(int i = this.chains.size()-1; i < chains.size(); i++){
                this.chains.add(chains.get(i));
            }
        }
        for (int i = 0; i < this.chains.size(); i++) {
            if(this.chains.get(i).getBlockchain().size() < chains.get(i).getBlockchain().size()){
                this.chains.set(i, chains.get(i));
            }    
        }
        

    }

    // @Override
    // public void UpdateChain(int chainId, Blockchain chain) throws RemoteException {
    //     this.chains.set(chainId, chain);
    // }

    
    
}
