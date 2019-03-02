/**
 * @author Nils Persson
 * @date 2019-Feb-26 4:43:00 PM 
 */
package RMI;

import java.util.concurrent.LinkedBlockingQueue;

import org.json.simple.JSONObject;

/**
 * 
 */
public class ClientMessageHandlingThread implements Runnable{
	private LinkedBlockingQueue<JSONObject> messages;
	private ClientGrapher client;
	
	public ClientMessageHandlingThread(ClientGrapher c, LinkedBlockingQueue<JSONObject> m){
		client = c;
		messages = m;
	}
	
	@Override
	public void run() {
		System.out.println("*** Client Message Handling Thread Started! ***");
		while(true){
            try{
                JSONObject message = messages.take();
                System.out.println("*** Client Received: " + message + " ***");
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
