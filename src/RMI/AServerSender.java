package RMI;

import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.json.simple.JSONObject;

public class AServerSender implements ServerSender{
//	private List<RMISender> clients = new ArrayList<>();
	ServerGrapher server; 
	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private LinkedBlockingQueue<JSONObject> messages;
	
	public AServerSender(Socket so, LinkedBlockingQueue<JSONObject> m){
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
	
	public void sendMessage(JSONObject message){
		try {
			out.writeObject(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
