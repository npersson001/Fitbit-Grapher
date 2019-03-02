/**
 * @author Nils Persson
 * @date 2019-Feb-26 3:59:25 PM 
 */
package RMI;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

import org.json.simple.JSONObject;

/**
 * 
 */
public class ServerAcceptingThread implements Runnable{
	private ArrayList<ServerSender> clientList;
	private ServerSocket serverSocket;
	private LinkedBlockingQueue<JSONObject> inMessages;
	private LinkedBlockingQueue<JSONObject> outMessages;
	
	public ServerAcceptingThread(ServerSocket socket, LinkedBlockingQueue<JSONObject> inM, LinkedBlockingQueue<JSONObject> outM, ArrayList<ServerSender> c){
		serverSocket = socket;
		inMessages = inM;
		outMessages = outM;
		clientList = c;
	}
	
	@Override
	public void run() {
		int connected = 0;
		System.out.println("*** Server Connecting Thread Started! ***");
		while(true){
            try{
                Socket socket = serverSocket.accept();
                clientList.add(new AServerSender(socket, inMessages));
                if(++connected == RegistryServer.TOTAL_CLIENTS){
            		JSONObject json = new JSONObject();
            		json.put("server_message", RegistryServer.START_SENDING);
                	outMessages.put(json);
                }
            }
            catch(IOException | InterruptedException e){ e.printStackTrace(); }
        }
	}

}
