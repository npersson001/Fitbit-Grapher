package RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMISender extends Remote {
//	public void rmiMessage(RMISender client, String message) throws RemoteException;
	public void receiveMessage(String message) throws RemoteException;
	public void startSending() throws RemoteException;
}
