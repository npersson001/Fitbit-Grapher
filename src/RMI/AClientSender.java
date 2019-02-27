package RMI;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class AClientSender implements ClientSender {
	public ServerSender server;
	public ClientGrapher client;
	private Scanner in;
	private PrintWriter out;
	private Socket socket;
	private LinkedBlockingQueue<String> messages;
	
	public AClientSender(ClientGrapher c, Socket s, LinkedBlockingQueue<String> m) {
		client = c;
		socket = s;
		messages = m;
		
		Thread readingThread = new Thread(this);
		readingThread.start();
	}
	
	public void sendMessage(String message){
		out.println(message);
	}
	
	@Override
	public void run() {
		System.out.println("*** Connected to Server! ***");
		try {
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream(), true);
            while (true) {
                String message = in.nextLine();
                messages.put(message);
            }
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
}
