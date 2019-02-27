/**
 * @author Nils Persson
 * @date 2019-Feb-26 1:13:37 PM 
 */
package RMI;

/**
 * 
 */
public interface RegistryServer {
	static int REGISTRY_PORT_NAME = 4999;
	static String REGISTRY_HOST_NAME = "localhost";
	static String SENDER_NAME = "RMISender";
	
	public final int TOTAL_CLIENTS = 2;
	public final String START_SENDING = "start_sending";
}
