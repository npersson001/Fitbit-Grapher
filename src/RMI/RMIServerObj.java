package RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIServerObj extends Remote{
	public void receiveMessage(String message) throws RemoteException;
	public void connect(RMISender r) throws RemoteException;
}
