/**
 * 
 */
package dsserver;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author zwmsc
 *
 */
public class SupportServer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.setProperty("java.rmi.server.hostname", "192.168.0.31");
		
		try{
			
			directoryInterface fi=new directoryImp("directoryServer");
			
			directoryInterface stub= (directoryInterface) UnicastRemoteObject.exportObject(fi,5555);
			
			Registry regist=LocateRegistry.createRegistry(5555);
			regist.bind("directoryInterface", stub);
			
			System.out.println("ready, server started");
			
			//Naming.rebind("//127.0.0.1/FileServer",fi);
			
		}catch(Exception e){
			
			System.out.println("directoryServer:"+e.getMessage());
			e.printStackTrace();
			
		}
	}

}
