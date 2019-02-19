/**
 * @author Nils Persson
 * @date 2019-Feb-15 1:00:39 PM 
 */
package RMI;

/**
 * 
 */
public class DemoLauncher {
	public static final String JSON1 = "heart_rate-2019-01-17.json";
	public static final String JSON2 = "heart_rate-2019-01-18.json";
	public static final String JSON3 = "heart_rate-2019-01-19.json";

	public static void main(String[] args){
		ARMILauncher registry = new ARMILauncher();
		ServerGrapher server = new ServerGrapher();
		ClientGrapher client1 = new ClientGrapher("client1", JSON1);
		ClientGrapher client2 = new ClientGrapher("client2", JSON2);
//		ClientGrapher client3 = new ClientGrapher("client3", JSON3);
		
		try {
			new Thread(registry).start();
			Thread.sleep(5000);
			new Thread(server).start();
			Thread.sleep(5000);
			new Thread(client1).start();
			new Thread(client2).start();
//			new Thread(client3).start();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
		
	}
}
