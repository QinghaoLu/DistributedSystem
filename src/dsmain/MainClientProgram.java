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

import dsblockchain.Blockchain;
import dsserver.directoryInterface;

// import dsblockchain.Block;

public class MainClientProgram {

	public UserInfo user;
	private ClientCom me;
	public Scanner scanner;
	directoryInterface dserver;
	ArrayList<String> peers;
	// ArrayList<Block> votepool;
	// ArrayList<MainClientProgram> voterList;

	public MainClientProgram(UserInfo user, Scanner s) {
		this.scanner = s;
		try {

			this.user = user;
			me = new ClientCom();
			ClientComInterface com = (ClientComInterface) UnicastRemoteObject.exportObject(me,
					Integer.valueOf(user.port));
			Registry reg = LocateRegistry.createRegistry(Integer.valueOf(user.port));
			reg.bind(user.name, com);

			System.out.println("Online connecting to Dserver...");
			dserver = (directoryInterface) Naming.lookup("//192.168.0.31:5555/directoryInterface");
			try {
				Thread t = new Thread(new UI(this));
				t.start();
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		} catch (RemoteException | AlreadyBoundException | MalformedURLException | NotBoundException e) {
			e.printStackTrace();
		}
		System.out.println("You are on port:" + user.port);
		// COM TEST//
		

		
		// if (user.name.equals("tester2")) {
		// 	me.tokens.add("Wanted");
		// 	try {
		// 		ClientComInterface testcom = (ClientComInterface) Naming.lookup("//192.168.0.64:5555/tester1");
				
		// 		testcom.requestVote(0, user.name, me.clockList.get(0).getValue());
		// 		System.out.println("response recived");
		// 	} catch (MalformedURLException | RemoteException | NotBoundException e) {

		// 		e.printStackTrace();
		// 	}
		// }

	}
	public void createChain(Blockchain chain){
		me.tokens = "WantA";
		updatePeers();
		for(int i = 0; i < peers.size(); i++){
			try {
				ClientComInterface peer = (ClientComInterface) Naming.lookup(peers.get(i));
				peer.requestaddChain(user.name, me.clock.getValue());
			} catch (RemoteException | MalformedURLException | NotBoundException e) {
				e.printStackTrace();
			}
		}
		me.chains.add(chain);
		me.tokens = "Releasd";
	}

	public void updatePeers(){
		try {
			peers = dserver.peerAddress(user.name, user.passwd, user.addr, user.port);
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}
	}
	public ArrayList<Blockchain> getPolls(){
		return me.chains;

	}
	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter port: ");
		String port = scanner.nextLine();
		
		
		UserInfo user = RegisterClass.registration(port,scanner);

		if(user != null)
			new MainClientProgram(user,scanner);
		

		

	}

}
