/**
 * 
 */
package dsserver;

import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * @author zwmsc
 *
 */
public class directoryImp implements directoryInterface{

// those are parallel array
	ArrayList<UserInfo> users=new ArrayList<UserInfo>();
	
	protected directoryImp(String string) throws RemoteException{
		// TODO Auto-generated constructor stub
		super();
	}

	@Override
	public boolean registerfunc(String name, String passwd, String ipaddr, String portNum) throws RemoteException {
		// TODO Auto-generated method stub
		for(int i=0;i<users.size();i++)//check if user exist
		{
			if(users.get(i).name.equals(name)) {
				return false;
			}
		}
		
		users.add(new UserInfo(name,passwd,ipaddr,portNum));
		
		return true;
	}

	@Override
	public boolean loginfunc(String name, String passwd, String ipaddr, String portNum) throws RemoteException {
		
		for(int i=0; i<users.size();i++) {
			if(users.get(i).name.equals(name) && users.get(i).passwd.equals(passwd)) {
				
				users.get(i).addr=ipaddr;
				users.get(i).port=portNum;
				
				return true;
			}
		}
		
		return false;
	}

	@Override
	public ArrayList<String> peerAddress(String name, String passwd, String ipaddr, String portNum)
			throws RemoteException {
		
		ArrayList<String> peerlist=new ArrayList<String>();
		
		for(int i=0; i<users.size();i++) {
			if(users.get(i).name.equals(name) && users.get(i).passwd.equals(passwd)) {
				
				users.get(i).addr=ipaddr;
				users.get(i).port=portNum;
				
				for(int j=0;j<users.size();j++) {
					if(j!=i)
						peerlist.add("//"+users.get(j).addr+":"+users.get(j).port+"/"+users.get(j).name);
				}
				return peerlist;
				
			}
		}
		
		return null;
	}

}
