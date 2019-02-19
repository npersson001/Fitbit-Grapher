package RMI;

import java.beans.PropertyChangeEvent;
import java.nio.ByteBuffer;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ARMIServerObj implements RMIServerObj{
	private List<RMISender> clients = new ArrayList<>();
	ServerGrapher server; 
	
	public ARMIServerObj(ServerGrapher s){
		server = s;
	}
	
	// method called remotely to pass message to the server
	public void receiveMessage(String message) {
		System.out.println("Server received RMI: " + message);
		
		// broadcast method to all clients
		for(RMISender client : clients){
			try {
				client.receiveMessage(message);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}

	public void connect(RMISender client){
		clients.add(client);
		if(clients.size() == server.TOTAL_CLIENTS){
			for(RMISender c: clients){
				try {
					c.startSending();
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
