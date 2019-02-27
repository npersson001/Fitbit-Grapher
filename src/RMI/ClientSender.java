package RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientSender extends Runnable{
	public void sendMessage(String message);
}
