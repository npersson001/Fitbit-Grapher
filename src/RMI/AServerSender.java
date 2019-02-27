package RMI;

import java.beans.PropertyChangeEvent;
import java.io.IOException;
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

public class AServerSender implements ServerSender{
//	private List<RMISender> clients = new ArrayList<>();
	ServerGrapher server; 
	private Socket socket;
	private Scanner in;
	private PrintWriter out;
	private LinkedBlockingQueue<String> messages;
	
	public AServerSender(Socket so, LinkedBlockingQueue<String> m){
		socket = so;
		messages = m;
		
		Thread readingThread = new Thread(this);
		readingThread.start();
	}
	
	public void run(){
		System.out.println("*** Connected: " + socket + " ***");
		try {
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream(), true);
            while (true) {
                String message = in.nextLine();
                messages.put(message);
            }
        } catch (Exception e) {
            System.out.println("*** Error:" + socket + " ***");
        } finally { // close the socket when done
            try { socket.close(); } catch (IOException e) {}
            System.out.println("*** Closed: " + socket + " ***");
        }
	}
	
	public void sendMessage(String message){
		out.println(message);
	}
}
