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
		// TODO Auto-generated method stub
		try{
			
			directoryInterface fi=new directoryImp("directoryServer");
			
			directoryInterface stub= (directoryInterface) UnicastRemoteObject.exportObject(fi,0);
			
			Registry regist=LocateRegistry.getRegistry();
			regist.bind("directoryInterface", stub);
			
			System.out.println("ready, server started");
			
			//Naming.rebind("//127.0.0.1/FileServer",fi);
			
		}catch(Exception e){
			
			System.out.println("directoryServer:"+e.getMessage());
			e.printStackTrace();
			
		}
	}

}
