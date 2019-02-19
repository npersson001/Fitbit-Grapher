package RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ARMIClientObj implements RMISender {
	public RMIServerObj server;
	public ClientGrapher client;
	
	public ARMIClientObj(ClientGrapher c) {
		client = c;
	}
	
	public void receiveMessage(String message) {
		System.out.println("Client " + client.clientName + " received RMI: " + message);
		client.updateChart(message);
	}
	
	public void startSending(){
		System.out.println("*** Begin Transmitting Data! ***");
//		client.sendData();
		client.startSending = true;
	}
}
