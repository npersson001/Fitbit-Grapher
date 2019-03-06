/**
 * @author Nils Persson
 * @date 2019-Feb-06 10:05:44 AM 
 */
package RMI;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import org.json.simple.JSONObject;

/**
 * 
 */
public class ServerGrapher{
	private ArrayList<ServerSender> clientList;
    private LinkedBlockingQueue<JSONObject> inMessages;
    private LinkedBlockingQueue<JSONObject> outMessages;
    private ServerSocket serverSocket;
	
	public ServerGrapher(){
		System.out.println("*** Server Starting! ***");
	}
    
	// method to setup all threads on the server for handling connections and messages
	public void initializeConnections(int aRegistryPort, String aRegistryHost){
		clientList = new ArrayList<ServerSender>();
        inMessages = new LinkedBlockingQueue<JSONObject>();
        outMessages = new LinkedBlockingQueue<JSONObject>();
        try {
			serverSocket = new ServerSocket(aRegistryPort);
		} catch (IOException e) { e.printStackTrace();}
		
        // start thread to accepting incoming connection requests from clients
        Thread acceptingThread = new Thread(new ServerAcceptingThread(serverSocket, inMessages, outMessages, clientList));
		acceptingThread.start();
		
		// start thread for taking messages off of incoming queue and onto outgoing queue
		Thread messageHandler = new Thread(new ServerMessageHandlingThread(inMessages, outMessages));
		messageHandler.start();
		
		// start thread for taking messages off of outgoing queue and broadcasting them to all clients
		Thread broadcastThread = new Thread(new ServerBroadcastingThread(outMessages, clientList));
		broadcastThread.start();
	}
		
	public static void main(String[] args) throws Exception{
		ServerGrapher server = new ServerGrapher();
		server.initializeConnections(RegistryServer.REGISTRY_PORT_NAME, RegistryServer.REGISTRY_HOST_NAME);
	}
	
}
