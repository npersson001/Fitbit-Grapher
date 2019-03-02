/**
 * @author Nils Persson
 * @date 2019-Feb-26 3:56:26 PM 
 */
package RMI;

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
