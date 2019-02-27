/**
 * @author Nils Persson
 * @date 2019-Feb-27 11:54:16 AM 
 */
package RMI;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 
 */
public class ServerBroadcastingThread implements Runnable{
private LinkedBlockingQueue<String> outMessages;
private List<ServerSender> clientList;
	
	public ServerBroadcastingThread(LinkedBlockingQueue<String> serverOutMessageQueue, List<ServerSender> clients ){
		outMessages = serverOutMessageQueue;
		clientList = clients;
	}
	
	@Override
	public void run() {
		System.out.println("*** Server Broadcasting Thread Started! ***");
		while(true){
            try{
                String message = outMessages.take();
                for(ServerSender clientConnection : clientList){
                	clientConnection.sendMessage(message);
                }
            }
            catch(InterruptedException e){ }
        }
	}
}
