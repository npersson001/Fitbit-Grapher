package Sockets;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

import org.json.simple.JSONObject;

public class ServerSender implements Runnable{
	ServerGrapher server; 
	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private LinkedBlockingQueue<JSONObject> messages;
	
	// constructor for serversender which sets up connection with its corresponding socket and starts a reading thread
	public ServerSender(Socket so, LinkedBlockingQueue<JSONObject> m){
		socket = so;
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
	
	// run method for thread to take all incoming messages and put them on the incoming message queue
	public void run(){
		System.out.println("*** Connected: " + socket + " ***");
		try {
            while (true) {
                JSONObject message = (JSONObject) in.readObject();
                messages.put(message);
            }
        } catch (Exception e) {
            System.out.println("*** Error:" + socket + " ***");
        } finally { // close the socket when done
            try { socket.close(); } catch (IOException e) {}
            System.out.println("*** Closed: " + socket + " ***");
        }
	}
	
	// method called to write to the socket
	public void sendMessage(JSONObject message){
		try {
			out.writeObject(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
