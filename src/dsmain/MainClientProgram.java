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

import java.util.Scanner;


import dsblockchain.Blockchain;
import dsserver.directoryInterface;



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
		// me.tokens.add("Wanted");
		// try {
		// ClientComInterface testcom = (ClientComInterface)
		// Naming.lookup("//192.168.0.64:5555/tester1");

		// testcom.requestVote(0, user.name, me.clockList.get(0).getValue());
		// System.out.println("response recived");
		// } catch (MalformedURLException | RemoteException | NotBoundException e) {

		// e.printStackTrace();
		// }
		// }

	}

	// update peer list
	public void updatePeers() {
		try {
			peers = dserver.peerAddress(user.name, user.passwd, user.addr, user.port);
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}
	}

	public ArrayList<Blockchain> getPolls() {
		updatePeers();
		// thread pool to getpolls
		ArrayList<Thread> pool = new ArrayList<Thread>();
		for (int i = 0; i < peers.size(); i++) {
			pool.add(new Thread(new Multicast(peers.get(i),1)));
			pool.get(i).start();
		}
		// wait for threads join
		for (Thread i : pool) {
			try {
				i.join();
			} catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
		return me.chains;
	}

	public void createChain(Blockchain chain) {
		me.tokens = "WantA";
		long start = System.currentTimeMillis();
		updatePeers();
		// thread pool to request chain creation
		int count = 0;
		ArrayList<Thread> pool = new ArrayList<Thread>();
		for (int i = 0; i < peers.size(); i++) {
			pool.add(new Thread(new Multicast(peers.get(i),2)));
			pool.get(i).start();
			
		}
		for (Thread i : pool) {
			try {
				i.join();
				count++;
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		me.tokens = "Held";
		long end = System.currentTimeMillis();
		System.out.println(count+" calls were made. Total Duration of "+(end-start)+" ms");
		getPolls();
		me.chains.add(chain);
		
		// ArrayList<Thread> pool2 = new ArrayList<Thread>();
		// for (int i = 0; i < peers.size(); i++) {
		// 	pool2.add(new Thread(new Multicast(peers.get(i),3)));
		// 	pool2.get(i).start();
		// }
		// for (Thread i : pool2) {
		// 	try {
		// 		i.join();
		// 	} catch (InterruptedException e) 
		// 	{
		// 		e.printStackTrace();
		// 	}
		// }
		me.tokens = "Releasd";
	}

	public void vote(int chainID, int[] selection) {
		me.tokens = "WantV" + chainID;
		updatePeers();
		ArrayList<Thread> pool = new ArrayList<Thread>();
		for (int i = 0; i < peers.size(); i++) {
			pool.add(new Thread(new Multicast(peers.get(i),4,chainID)));
			pool.get(i).start();
		}
		for (Thread i : pool) {
			try {
				i.join();
			} catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
		me.tokens = "Held"+chainID;
		getPolls();
		
		me.chains.get(chainID).addBlock(user.name, user.addr, selection);

		// ArrayList<Thread> pool2 = new ArrayList<Thread>();
		// for (int i = 0; i < peers.size(); i++) {
		// 	pool2.add(new Thread(new Multicast(peers.get(i),3)));
		// 	pool2.get(i).start();
		// }
		// for (Thread i : pool2) {
		// 	try {
		// 		i.join();
		// 	} catch (InterruptedException e) 
		// 	{
		// 		e.printStackTrace();
		// 	}
		// }
		if(user.name.equals("mu")){
			try {
			Thread.sleep(30000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		me.tokens = "Releasd";

	}

	

	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter port: ");
		String port = scanner.nextLine();
		
		
		UserInfo user = RegisterClass.registration(port,scanner);

		if(user != null)
			new MainClientProgram(user,scanner);
		
		scanner.close();
		

	}
	public class Multicast implements Runnable{
		String peerinfo;
		int function;
		int chainID;
		public Multicast(String p,int function){
			this.peerinfo = p;
			this.function = function;
		}

		public Multicast(String p,int function,int chainID){
			this.peerinfo = p;
			this.function = function;
			this.chainID = chainID;
		}

		@Override
		public void run() {
			if(function == 1){
				updateChain();
			}
			else if(function == 2){
				requestAddChain();
			}
			else if(function == 3){
				update();
			}
			else if(function == 4){
				requestVote();
			}
			
		}
		// 1
		private void updateChain() {
			try {
				// System.out.println(peerinfo);
				ClientComInterface peer = (ClientComInterface) Naming.lookup(peerinfo);
				if (peer.getUpdate().size() != 0){
					ArrayList<Blockchain> temp = new ArrayList<Blockchain>();
					temp = peer.getUpdate();
					if(me.chains.size() == 0){
						me.chains= temp;
					}
					else{
						if(me.chains.size() < temp.size()){
							for(int i = me.chains.size(); i < temp.size(); i++){
								me.chains.add(temp.get(i));
							}
						}
						for (int i = 0; i < temp.size(); i++) {
							if(me.chains.get(i).getBlockchain().size() < temp.get(i).getBlockchain().size() && me.chains.get(i).getBlockchain().get(me.chains.get(i).getBlockchain().size()-1).hash.equals(temp.get(i).getBlockchain().get(me.chains.get(i).getBlockchain().size()-1).hash)){
								me.chains.set(i, temp.get(i));
							}    
						}
					}
					
					
				}
			} catch (RemoteException | MalformedURLException | NotBoundException e) {
				// e.printStackTrace();
			}
		}
		// 2
		private void requestAddChain() {
			try {
				ClientComInterface peer = (ClientComInterface) Naming.lookup(peerinfo);
				peer.requestaddChain(user.name, me.clock.getValue());
			} catch (RemoteException | MalformedURLException | NotBoundException e) {
				// e.printStackTrace();
			}
		}
		// 3
		private void update() {
			for (int i = 0; i < peers.size(); i++) {
				try {
					ClientComInterface peer = (ClientComInterface) Naming.lookup(peerinfo);
					peer.Update(me.chains);
				} catch (RemoteException | MalformedURLException | NotBoundException e) {
					// e.printStackTrace();
				}
			}
		}
		// 4
		private void requestVote(){
			System.out.println(peerinfo);
			try {
				ClientComInterface peer = (ClientComInterface) Naming.lookup(peerinfo);
				peer.requestVote(chainID, user.name, me.clock.getValue());
			} catch (RemoteException | MalformedURLException | NotBoundException e) {
				// e.printStackTrace();
			}
		}

	}
}
