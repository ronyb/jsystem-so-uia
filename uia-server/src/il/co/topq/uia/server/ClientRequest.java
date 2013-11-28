package il.co.topq.uia.server;
import java.util.Arrays;

/**
 * @author Rony Byalsky
 */
public class ClientRequest {

	public String methodName;
	public String[] methodParams;

	public ClientRequest(String clientRequestStr, String delimiter) {
		
		String[] requestSplit = clientRequestStr.split(delimiter);
		
		this.methodName = requestSplit[0];
		
		if (requestSplit.length > 1) {
			methodParams = Arrays.copyOfRange(requestSplit, 1, requestSplit.length);
		}
	}
}