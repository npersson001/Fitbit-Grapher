/**
 * @author Nils Persson
 * @date 2019-Feb-26 3:56:26 PM 
 */
package RMI;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * 
 */
public class ServerMessageHandlingThread implements Runnable{
	private LinkedBlockingQueue<String> inMessages;
	private LinkedBlockingQueue<String> outMessages;
	
	public ServerMessageHandlingThread(LinkedBlockingQueue<String> in, LinkedBlockingQueue<String> out){
		inMessages = in;
		outMessages = out;
	}
	
	@Override
	public void run() {
		System.out.println("*** Server Message Handling Thread Started! ***");
		while(true){
            try{
                String message = inMessages.take();
                System.out.println("*** Server Received: " + message + " ***");
                outMessages.put(message);
            }
            catch(InterruptedException e){ }
        }
	}

}
