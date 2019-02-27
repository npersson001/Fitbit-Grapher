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

/**
 * 
 */
public class ServerConnectingThread implements Runnable{
	private ArrayList<ServerSender> clientList;
	private ServerSocket serverSocket;
	private LinkedBlockingQueue<String> messages;
	
	public ServerConnectingThread(ServerSocket socket, LinkedBlockingQueue<String> m, ArrayList<ServerSender> c){
		serverSocket = socket;
		messages = m;
		clientList = c;
	}
	
	@Override
	public void run() {
		int connected = 0;
		System.out.println("*** Server Connecting Thread Started! ***");
		while(true){
            try{
                Socket socket = serverSocket.accept();
                clientList.add(new AServerSender(socket, messages));
                Thread.sleep(1000); // is this an acceptable way to deal with the last client not being done initializing
                if(++connected == RegistryServer.TOTAL_CLIENTS){
                	for(ServerSender client : clientList){
                		client.sendMessage(RegistryServer.START_SENDING);
                	}
                }
            }
            catch(IOException | InterruptedException e){ e.printStackTrace(); }
        }
	}

}
