/**
 * @author Nils Persson
 * @date 2019-Feb-06 10:05:53 AM 
 */
package RMI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.FileReader;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * 
 */
public class ClientGrapher extends JPanel implements Runnable{
	private RMIServerObj rmiServerObj;
	private RMISender rmiSender;
	public String clientName;
	private String jsonFile;
	private Map<String, List<Integer>> heartBeats = new HashMap<>();
	final JScrollPane pane;
	final Color[] clientColors = {Color.RED, Color.BLACK, Color.GREEN, Color.BLUE, Color.YELLOW, Color.ORANGE};
	
	public ClientGrapher(String name, String json) {
		clientName = name;
		jsonFile = json;
		System.out.println("*** Client " + clientName + " Started! ***");
		JFrame frame = new JFrame("Heart Beat Data");
	    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    pane = new JScrollPane();
	    frame.add(pane);
	    pane.setViewportView(this);
	    frame.setSize(1600, 800);
	    frame.setVisible(true);
		pane.revalidate();
	}
	
	public void updateChart(String message){
		String[] messageArr = message.split(":");
		String sendingClient = messageArr[0];
		int heartBeat = Integer.parseInt(messageArr[1]);
		if(!heartBeats.containsKey(sendingClient)){
			heartBeats.put(sendingClient, new ArrayList<Integer>());
		}
		heartBeats.get(sendingClient).add(heartBeat);
		repaint();
	}
	
	public int adjustY(int y, int numY){
		return (2 * this.getHeight() / 3) - y;
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(6));
        
        int maxWidth = 0;
        int colorIdx = 0;
        for(Map.Entry<String, List<Integer>> heartBeatsList : heartBeats.entrySet()){
        	g2.setColor(clientColors[colorIdx]);
        	int prevX = 0, prevY = 0;
    		for(int i = 0; i < heartBeatsList.getValue().size(); i++){
    			int x = i;
    			int y = adjustY(heartBeatsList.getValue().get(i), heartBeatsList.getValue().size());
    			prevX = i == 0 ? i : prevX;
    			prevY = i == 0 ? y : prevY;
    			g2.drawLine(prevX, prevY, x, y); 
    			maxWidth = x;
    			prevX = x;
    			prevY = y;
    		} 
    		colorIdx = (colorIdx + 1) % clientColors.length;
        }
		
		this.setPreferredSize(new Dimension(maxWidth > 1600 ? maxWidth : 1600, 800));
		pane.revalidate();
	}
	
	private List<Long> createDataset(){
		// get json information 
		JSONParser parser = new JSONParser();
		JSONArray array = null;
		try {
			array = (JSONArray) parser.parse(new FileReader(jsonFile));
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		List<Long> list = new ArrayList<>();
		for (Object o : array){
			JSONObject dataPoint = (JSONObject) o;
			String dateTime = (String) dataPoint.get("dateTime");
			JSONObject value = (JSONObject) dataPoint.get("value");
			long bpm = (long) value.get("bpm");
			list.add(bpm);
		}
		
		return list;
	}
	
	// method that sets up simulation and connection to server using RMI
	public void initializeRMI(String aRegistryHost, int aRegistryPort){
		try {
			Registry rmiRegistry = LocateRegistry.getRegistry(aRegistryHost, aRegistryPort);
			rmiServerObj = (RMIServerObj) rmiRegistry.lookup(RegistryServer.SENDER_NAME);
			rmiSender = new ARMIClient(this);
			UnicastRemoteObject.exportObject(rmiSender, 0);
			rmiServerObj.connect(rmiSender);
			System.out.println("*** Connected to Server! ***");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void sendData(){
		List<Long> data = this.createDataset();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		for(Long l : data){
			try {
				this.rmiServerObj.receiveMessage(clientName + ":" + l);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void run(){
		this.initializeRMI(RegistryServer.REGISTRY_HOST_NAME, RegistryServer.REGISTRY_PORT_NAME);
	}

	public static void main(String[] args) {
		ClientGrapher client = new ClientGrapher("client_default", "heart_rate-2019-01-19.json");
		List<Long> data = client.createDataset();
		client.initializeRMI(RegistryServer.REGISTRY_HOST_NAME, RegistryServer.REGISTRY_PORT_NAME);
		
		for(Long l : data){
			try {
				client.rmiServerObj.receiveMessage("client_default" + ":" + l);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}
}
