/**
 * @author Nils Persson
 * @date 2019-Feb-26 4:43:00 PM 
 */
package Sockets;

import java.util.concurrent.LinkedBlockingQueue;

import org.json.simple.JSONObject;

/**
 * 
 */
public class ClientMessageHandlingThread implements Runnable{
	private LinkedBlockingQueue<JSONObject> messages;
	private ClientGrapher client;
	
	// constructor for thread that reads messages incoming from the server
	public ClientMessageHandlingThread(ClientGrapher c, LinkedBlockingQueue<JSONObject> m){
		client = c;
		messages = m;
	}
	
	@Override
	public void run() {
		System.out.println("*** Client Message Handling Thread Started! ***");
		while(true){
            try{
                JSONObject message = messages.take(); // blocking call, waits for actual message to be there
                System.out.println("*** Client Received: " + message + " ***");
                
                // check if first message from server to start sending, otherwise just update the dataset
                if(message.get("server_message") != null && message.get("server_message").equals(RegistryServer.START_SENDING)){
                	client.sendData();
                }
                else{
                	client.updateChart(message);
                }
            }
            catch(InterruptedException e){ }
        }
	}
}
