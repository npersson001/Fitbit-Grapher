package RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

import org.json.simple.JSONObject;

public interface ServerSender extends Runnable{
	public void sendMessage(JSONObject message);
}