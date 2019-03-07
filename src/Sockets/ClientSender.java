package Sockets;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

import org.json.simple.JSONObject;

public class ClientSender implements Runnable{
	public ServerSender server;
	public ClientGrapher client;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private Socket socket;
	private LinkedBlockingQueue<JSONObject> messages;
	
	// constructor for object responsible for capturing incoming messages from server and enqueueing them
	public ClientSender(ClientGrapher c, Socket s, LinkedBlockingQueue<JSONObject> m) {
		client = c;
		socket = s;
		messages = m;
		
		try {
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Thread readingThread = new Thread(this);
		readingThread.start();
	}
	
	// method for sending JSON objects over the socket connection
	public void sendMessage(JSONObject message){
		try {
			out.writeObject(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		System.out.println("*** Connected to Server! ***");
		try {
			// listen continuously for incoming JSON objects over the socket stream
            while (true) {
                JSONObject message = (JSONObject) in.readObject();
                messages.put(message); // put messages on the queue for message handling thread to deal with
            }
		} catch (IOException | InterruptedException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
