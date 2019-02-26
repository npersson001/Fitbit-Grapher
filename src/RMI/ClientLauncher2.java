/**
 * @author Nils Persson
 * @date 2019-Feb-19 12:07:09 PM 
 */
package RMI;

/**
 * 
 */
public class ClientLauncher2 {
	public static final String JSON1 = "heart_rate-2019-01-17.json";
	public static final String JSON2 = "heart_rate-2019-01-18.json";
	public static final String JSON3 = "heart_rate-2019-01-19.json";

	public static void main(String[] args){
		ClientGrapher client2 = new ClientGrapher("client2", JSON2);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		client2.run();
	}
}
