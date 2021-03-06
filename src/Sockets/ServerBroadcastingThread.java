/**
 * @author Nils Persson
 * @date 2019-Feb-27 11:54:16 AM 
 */
package Sockets;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import org.json.simple.JSONObject;

/**
 * 
 */
public class ServerBroadcastingThread implements Runnable{
private LinkedBlockingQueue<JSONObject> outMessages;
private List<ServerSender> clientList;
	
	public ServerBroadcastingThread(LinkedBlockingQueue<JSONObject> serverOutMessageQueue, List<ServerSender> clients ){
		outMessages = serverOutMessageQueue;
		clientList = clients;
	}
	
	// run method for the thread to broadcast all messages on the outgoing queue to all clients
	@Override
	public void run() {
		System.out.println("*** Server Broadcasting Thread Started! ***");
		while(true){
            try{
                JSONObject message = outMessages.take();
                for(ServerSender clientConnection : clientList){
                	clientConnection.sendMessage(message);
                }
            }
            catch(InterruptedException e){ }
        }
	}
}
