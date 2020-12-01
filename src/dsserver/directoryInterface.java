package dsserver;

import java.rmi.Remote; 
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface directoryInterface {

	public boolean registerfunc(String name, String ipaddr, String portNum) throws RemoteException;
	
	public ArrayList<String> peerAddress() throws RemoteException;
	
}
