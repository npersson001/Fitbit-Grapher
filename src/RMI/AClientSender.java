package RMI;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.json.simple.JSONObject;

public class AClientSender implements ClientSender {
	public ServerSender server;
	public ClientGrapher client;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private Socket socket;
	private LinkedBlockingQueue<JSONObject> messages;
	
	public AClientSender(ClientGrapher c, Socket s, LinkedBlockingQueue<JSONObject> m) {
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
            while (true) {
                JSONObject message = (JSONObject) in.readObject();
                messages.put(message);
            }
		} catch (IOException | InterruptedException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
