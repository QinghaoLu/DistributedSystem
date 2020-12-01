/**
 * 
 */
package dsmain;

import java.rmi.AlreadyBoundException;
/**
 * @author zwmsc
 *
 */
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import dsblockchain.Block;

public class MainClientProgram {

	private String clientName;
	private String clientIP;
	private String clientport;
	ArrayList<Block> votepool;
	ArrayList<MainClientProgram> voterList;

	public MainClientProgram(String name) {

		try {
			ClientCom com = new ClientCom();
			ClientComInterface ComI = (ClientComInterface) UnicastRemoteObject.exportObject(com, 0);
			Registry reg = LocateRegistry.getRegistry();
			reg.bind(name, ComI);
		} catch (RemoteException | AlreadyBoundException e) {
			e.printStackTrace();
		}


	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
