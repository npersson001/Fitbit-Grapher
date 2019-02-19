/**
 * @author Nils Persson
 * @date 2019-Feb-06 10:05:44 AM 
 */
package RMI;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * 
 */
public class ServerGrapher implements Runnable{
	public RMIServerObj rmiServerObj;
	public final int TOTAL_CLIENTS = 2;
	
	public ServerGrapher(){
		System.out.println("*** Server Started! ***");
	}
    
	// method to set up server for RMI
	public void initializeRMI(int aRegistryPort, String aRegistryHost){
		try {
			Registry rmiRegistry = LocateRegistry.getRegistry(aRegistryHost, aRegistryPort);
			RMIServerObj rmiServ = new ARMIServerObj(this);
			rmiServerObj = rmiServ;
			UnicastRemoteObject.exportObject(rmiServerObj, 0);
			rmiRegistry.rebind(RegistryServer.SENDER_NAME, rmiServerObj);
			System.out.println("*** Remote Object Registered! ***");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void run(){
		this.initializeRMI(RegistryServer.REGISTRY_PORT_NAME, RegistryServer.REGISTRY_HOST_NAME);
	}
		
	public static void main(String[] args){
		ServerGrapher server = new ServerGrapher();
		server.initializeRMI(RegistryServer.REGISTRY_PORT_NAME, RegistryServer.REGISTRY_HOST_NAME);
	}
	
}
