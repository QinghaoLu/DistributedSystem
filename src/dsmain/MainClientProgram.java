/**
 * 
 */
package dsmain;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

// import dsblockchain.Block;

public class MainClientProgram {

	private String clientName;
	private String clientIP;
	private ClientCom me;
	private int clientport;
	
	// ArrayList<Block> votepool;
	// ArrayList<MainClientProgram> voterList;

	public MainClientProgram(String name, int port) {
		this.clientport = port;
		try {

			me = new ClientCom();
			ClientComInterface com = (ClientComInterface) UnicastRemoteObject.exportObject(me, clientport);
			Registry reg = LocateRegistry.createRegistry(clientport);
			reg.bind(name, com);
			me.Counters.add(0);
			me.clockList.add(new Clock());
			if(name.equals("tester1"))
				me.tokens.add("Held");

					
		} catch (RemoteException | AlreadyBoundException e) {
			e.printStackTrace();
		}
		System.out.println("server Running" + port);
		// COM TEST//
		
		if (name.equals("tester2")) {
			me.tokens.add("Wanted");
			try {
				ClientComInterface testcom = (ClientComInterface) Naming.lookup("//192.168.0.64:5555/tester1");
				
				testcom.requestVote(0, name, me.clockList.get(0).getValue());
				System.out.println("response recived");
			} catch (MalformedURLException | RemoteException | NotBoundException e) {

				e.printStackTrace();
			}
		}

	}
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter username: ");
		String rUsername = scanner.nextLine();
		System.out.println("Enter port: ");
		String port = scanner.nextLine();
		scanner.close();
		new MainClientProgram(rUsername,Integer.valueOf(port));
		
		

	}

}
