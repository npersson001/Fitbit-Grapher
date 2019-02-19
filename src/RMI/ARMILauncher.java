package RMI;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class ARMILauncher implements RegistryServer, Runnable{
	public static void main(String args[]) {
		try {
			LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
			System.out.println("*** Registry Started! ***");
			Scanner scanner = new Scanner(System.in);
			scanner.nextLine();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public void run(){
		try {
			LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
			System.out.println("*** Registry Started! ***");
			Scanner scanner = new Scanner(System.in);
			scanner.nextLine();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
}
