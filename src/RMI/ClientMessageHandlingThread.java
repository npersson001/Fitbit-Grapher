/**
 * @author Nils Persson
 * @date 2019-Feb-26 4:43:00 PM 
 */
package RMI;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * 
 */
public class ClientMessageHandlingThread implements Runnable{
	private LinkedBlockingQueue<String> messages;
	private ClientGrapher client;
	
	public ClientMessageHandlingThread(ClientGrapher c, LinkedBlockingQueue<String> m){
		client = c;
		messages = m;
	}
	
	@Override
	public void run() {
		System.out.println("*** Client Message Handling Thread Started! ***");
		while(true){
            try{
                String message = messages.take();
                System.out.println("*** Client Received: " + message + " ***");
                if(message.equals(RegistryServer.START_SENDING)){
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
