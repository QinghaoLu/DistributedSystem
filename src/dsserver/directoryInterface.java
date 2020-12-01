package dsserver;

import java.rmi.Remote; 
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface directoryInterface extends Remote {

	public boolean registerfunc(String name, String passwd,String ipaddr, String portNum) throws RemoteException;
	
	public boolean loginfunc(String name, String passwd,String ipaddr, String portNum) throws RemoteException;
	
	public ArrayList<String> peerAddress(String name, String passwd,String ipaddr, String portNum) throws RemoteException;
	
}
