/**
 * @author Nils Persson
 * @date 2019-Feb-26 3:56:26 PM 
 */
package Sockets;

import java.util.concurrent.LinkedBlockingQueue;

import org.json.simple.JSONObject;

/**
 * 
 */
public class ServerMessageHandlingThread implements Runnable{
	private LinkedBlockingQueue<JSONObject> inMessages;
	private LinkedBlockingQueue<JSONObject> outMessages;
	
	public ServerMessageHandlingThread(LinkedBlockingQueue<JSONObject> in, LinkedBlockingQueue<JSONObject> out){
		inMessages = in;
		outMessages = out;
	}
	
	// run method for thread to take messages from incoming queue and put on outgoing queue
	@Override
	public void run() {
		System.out.println("*** Server Message Handling Thread Started! ***");
		while(true){
            try{
                JSONObject message = inMessages.take();
                System.out.println("*** Server Received: " + message + " ***");
                outMessages.put(message);
            }
            catch(InterruptedException e){ }
        }
	}

}
