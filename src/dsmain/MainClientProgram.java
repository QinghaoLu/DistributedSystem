/**
 * 
 */
package dsmain;

import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Random;

// import dsblockchain.Block;

public class MainClientProgram {

	private String clientName;
	private String clientIP;
	private String clientport;
	// ArrayList<Block> votepool;
	// ArrayList<MainClientProgram> voterList;

	public MainClientProgram(String name) {
		int port = new Random().nextInt(1000)+5000;

		try {
			
			ClientCom com = new ClientCom();
			ClientComInterface ComI = (ClientComInterface) UnicastRemoteObject.exportObject(com, port);
			Registry reg = LocateRegistry.createRegistry(port);
			reg.bind(name, ComI);
		} catch (RemoteException | AlreadyBoundException e) {
			e.printStackTrace();
		}
		System.out.println("server Running"+port);
		
		try{
			if(name.equals("A")){
				
			}
			else 
			{
				ClientComInterface ComI = (ClientComInterface) Naming.lookup("A");
				ComI.testCom("fuck");
				
			}
			
			
			}
		catch(Exception e){
				System.err.println("FileServerexception:"+e.getMessage());
				e.printStackTrace();
			}


	}
	public static void main(String[] args) {
		if(args.length < 1){
			System.out.println("need your name");
			return;
		}
		new MainClientProgram(args[0]);

	}

}
