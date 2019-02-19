/**
 * @author Nils Persson
 * @date 2019-Feb-19 12:06:57 PM 
 */
package RMI;

/**
 * 
 */
public class ClientLauncher1 {
	public static final String JSON1 = "heart_rate-2019-01-17.json";
	public static final String JSON2 = "heart_rate-2019-01-18.json";
	public static final String JSON3 = "heart_rate-2019-01-19.json";

	public static void main(String[] args) throws InterruptedException{
		ClientGrapher client1 = new ClientGrapher("client1", JSON1);
		Thread.sleep(5000);
		client1.run();
	}
}
