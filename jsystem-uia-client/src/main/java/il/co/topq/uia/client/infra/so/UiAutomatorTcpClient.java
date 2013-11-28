package il.co.topq.uia.client.infra.so;

import il.co.topq.uia.client.infra.enums.UiSelectorType;
import il.co.topq.uia.client.infra.enums.WhenFalse;
import il.co.topq.uia.client.infra.utils.Regex;
import il.co.topq.uia.client.infra.utils.WindowsCommandPrompt;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Arrays;

import jsystem.framework.report.Reporter;
import jsystem.framework.system.SystemObjectImpl;

/**
 * @author Rony Byalsky
 */
public class UiAutomatorTcpClient extends SystemObjectImpl {
	
	String uiaServerIpAddress;
	int uiaServerPort;
	String uiaServerCommandDelimiter;

	Socket clientSocket;
	DataOutputStream outToServer;
	BufferedReader inFromServer;
	
	public void init() throws Exception {
		super.init();
		findAndroidDeviceIp();
	}
	
	private String findAndroidDeviceIp() throws Exception {
		String result = new WindowsCommandPrompt("adb shell netcfg").execute().replace("\n", "");
		String ipAddressLineRegex = ".*wlan0\\s+UP\\s+(\\d+\\.\\d+\\.\\d+\\.\\d+).*";
		String ipAddress = Regex.getGroup(result, ipAddressLineRegex, 1);
		uiaServerIpAddress = ipAddress;
		return ipAddress;
	}
	
	public void openSocket() throws Exception {
		report("Connecting to UIAutomator server @ IP: " + uiaServerIpAddress + "; PORT: " + uiaServerPort);
		clientSocket = new Socket(uiaServerIpAddress, uiaServerPort);
		outToServer = new DataOutputStream(clientSocket.getOutputStream());
		inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		report("Connection successful!");
	}
	
	public void closeSocket() throws Exception {
		clientSocket.close();	
		report("Connection to server closed");
	}
	
	public String sendCommandToServer(String command) throws Exception {
		outToServer.writeBytes(command + '\n');
		
		//TODO: implement a timeout mechanism to set the maximum time to wait for a response from the server 
		
		String responseFromServer = inFromServer.readLine();
		outToServer.flush(); // needed?
		reportResponseFromServer(responseFromServer);
		return responseFromServer;
	}
	
	private String buildCommandString(String... commandWords) {
		StringBuilder sb = new StringBuilder();
		for (int i=0; i<commandWords.length; i++) {
			if (i<commandWords.length-1) {
				sb.append(commandWords[i] + uiaServerCommandDelimiter);
			}
			else {
				sb.append(commandWords[i]);
			}
		}
		return sb.toString();
	}
	
	
	
	/* CALLS TO UIAUTOMATOR WRAPPER METHODS ON THE SERVER*/
	
	// PRESS HOME
	public void pressHome() throws Exception {
		report("Request: press <HOME>");
		sendCommandToServer("pressHome");
	}

	// PRESS BACK
	public void pressBack() throws Exception {
		report("Request: press <BACK>");
		sendCommandToServer("pressBack");
	}

	// PRESS MENU
	public void pressMenu() throws Exception {
		report("Request: press <MENU>");
		System.out.println(sendCommandToServer("pressMenu"));
	}
	
	// PRESS LEFT
	public void pressDPadLeft() throws Exception {
		report("Request: press <LEFT>");
		sendCommandToServer("pressDPadLeft");
	}
	
	// PRESS RIGHT
	public void pressDPadRight() throws Exception {
		report("Request: press <RIGHT>");
		sendCommandToServer("pressDPadRight");
	}

	// PRESS UP
	public void pressDPadUp() throws Exception {
		report("Request: press <UP>");
		sendCommandToServer("pressDPadUp");
	}

	// PRESS DOWN
	public void pressDPadDown() throws Exception {
		report("Request: press <DOWN>");
		sendCommandToServer("pressDPadDown");
	}

	// PRESS CENTER
	public void pressDPadCenter() throws Exception {
		report("Request: press <CENTER>");
		sendCommandToServer("pressDPadCenter");
	}
	
	// PRESS ENTER
	public void pressDPadEnter() throws Exception {
		report("Request: press <ENTER>");
		sendCommandToServer("pressDPadEnter");
	}
	
	// PRESS DELETE
	public void pressDPadDelete() throws Exception {
		report("Request: press <DELETE>");
		sendCommandToServer("pressDPadDelete");
	}
	
	// PRESS WAKE UP
	public void wakeUp() throws Exception {
		report("Request: WAKE UP");
		sendCommandToServer("wakeUp");
		Thread.sleep(500);
	}
	
	// PRESS KEY CODE
	public void pressKeyCode(int keyCode) throws Exception {
		report("Request: press key code: " + keyCode);
		String command = buildCommandString("pressKeyCode", keyCode+"");
		sendCommandToServer(command);
	}
	
	public void unlockScreen() throws Exception {
		report("unlock screen");
		int x1 = 242;
		int y1 = 637;
		int x2 = 445;
		int y2 = 638;
		swipe(x1, y1, x2, y2, 5, WhenFalse.FAIL);
	}
	

	
	//PRESS BACK ON GATEWAY APP
	public boolean pressGatewayBack() throws Exception{
		int[] path = {0,0,0,0,0};
		return clickUiObjectByPath
				(path,UiSelectorType.INDEX, "0", WhenFalse.FAIL);
	}

	// SWIPE
	public boolean swipe(int startX, int startY, int endX, int endY, int steps, WhenFalse whenFalse) throws Exception {
		report("Request: swipe from (" + startX + "," + startY + ") to (" + endX + "," + endY + ") in " + steps + " steps");
		String command = buildCommandString("swipe", startX+"", startY+"", endX+"", endY+"", steps+"");
		String response = sendCommandToServer(command);

		if (response.contains("[TRUE]")) {
			return true;
		}
		else {
			reportWhenFalse(response, whenFalse);
			return false;
		}
	}
	
	// CLICK UI OBJECT BY UI SELECTOR
	public boolean clickUiObjectByUiSelector(UiSelectorType selectorType, String selectorString, WhenFalse whenFalse) throws Exception {
		report("Request: click on element with " + selectorType + " = \"" + selectorString + "\"");
		String command = buildCommandString("clickUiObjectByUiSelector", selectorType.toString(), selectorString);
		String response = sendCommandToServer(command);
		
		if (response.contains("[TRUE]")) {
			return true;
		}
		else {
			reportWhenFalse(response, whenFalse);
			return false;
		}
	}
	
	// CLICK X,Y
	public boolean clickXY(int x, int y, WhenFalse whenFalse) throws Exception {
		report("Request: click at (" + x + "," + y + ")");
		String command = buildCommandString("clickXY", x+"", y+"");
		String response = sendCommandToServer(command);

		if (response.contains("[TRUE]")) {
			return true;
		}
		else {
			reportWhenFalse(response, whenFalse);
			return false;
		}
	}
	
	// GET TEXT FROM UI OBJECT
	public String getTextFromUiObject(UiSelectorType selectorType, String selectorString, WhenFalse whenFalse) throws Exception {
		report("Request: get text from element with " + selectorType + " = \"" + selectorString + "\"");
		String command = buildCommandString("getTextFromUiObject", selectorType.toString(), selectorString);
		String response = sendCommandToServer(command);
		
		if (response.contains("[TRUE]")) {
			return response.substring(response.lastIndexOf(": ")+2);
		}
		else {
			reportWhenFalse(response, whenFalse);
			return null;
		}
	}

	// GET TEXT FROM UI OBJECT BY PATH
	public String getTextFromUiObjectByPath(int[] path,UiSelectorType selectorType, String selectorString, WhenFalse whenFalse) throws Exception {
		report("Request: get text from root element with " + selectorType + " = \"" + selectorString + "\"" + " by path " + Arrays.toString(path));
		String command = buildCommandString("getTextFromUiObjectByPath", selectorType.toString(), selectorString,Arrays.toString(path).replaceAll("\\[|\\]",""));
		String response = sendCommandToServer(command);
		
		if (response.contains("[TRUE]")) {
			return response.substring(response.lastIndexOf(": ")+2);
		}
		else {
			reportWhenFalse(response, whenFalse);
			return null;
		}
	}
	
	// IS UI OBJECT CHECKED BY PATH
	public boolean isUiObjectCheckedByPath(int[] path,UiSelectorType selectorType, String selectorString, WhenFalse whenFalse) throws Exception {
		report("Request: get text from root element with " + selectorType + " = \"" + selectorString + "\"" + " by path " + Arrays.toString(path));
		String command = buildCommandString("isUiObjectCheckedByPath", selectorType.toString(), selectorString,Arrays.toString(path).replaceAll("\\[|\\]",""));
		String response = sendCommandToServer(command);
		
		if (response.contains("[TRUE]")) {
			String res = response.substring(response.lastIndexOf(": ")+2);
			if(res.trim().equals("true")){
				return true;
			}else{
				return false;
			}
		}
		else {
			reportWhenFalse(response, whenFalse);
			return false;
		}
	}

	
	//CLICK UI OBJECT BY PATH
	public boolean clickUiObjectByPath(int[] path,UiSelectorType selectorType, String selectorString, WhenFalse whenFalse) throws Exception {
		report("Request: click element from root element with " + selectorType + " = \"" + selectorString + "\"" + " by path " + Arrays.toString(path));
		String command = buildCommandString("clickUiObjectByPath", selectorType.toString(), selectorString,Arrays.toString(path).replaceAll("\\[|\\]",""));
		String response = sendCommandToServer(command);
		
		if (response.contains("[TRUE]")) {
			return true;
		}
		else {
			reportWhenFalse(response, whenFalse);
			return false;
		}
	}

	
	// GET NUMBER OF CHILDREN FROM UI OBJECT
	public String getNumberOfChildrenFromUiObject(UiSelectorType selectorType, String selectorString, WhenFalse whenFalse) throws Exception {
		report("Request: get number of children from element with " + selectorType + " = \"" + selectorString + "\"");
		String command = buildCommandString("getNumberOfChildrenFromUiObject", selectorType.toString(), selectorString);
		String response = sendCommandToServer(command);
		
		if (response.contains("[TRUE]")) {
			return response.substring(response.lastIndexOf(": ")+2);
		}
		else {
			reportWhenFalse(response, whenFalse);
			return null;
		}
	}

		
	// ELEMENT EXISTS?
	public boolean elementExists(UiSelectorType selectorType, String selectorString, WhenFalse whenFalse) throws Exception {
		report("Request: check if element with " + selectorType + " = \"" + selectorString + "\" exists");
		String command = buildCommandString("elementExists", selectorType.toString(), selectorString);
		String response = sendCommandToServer(command);
		
		if (response.contains("[TRUE]")) {
			return true;
		}
		else {
			reportWhenFalse(response, whenFalse);
			return false;
		}
	}
		
	// VALIDATE PACKAGE
	public boolean validatePackage(String expectedPackageName, WhenFalse whenFalse) throws Exception {
		report("Request: check if on package name: " + expectedPackageName);
		String command = buildCommandString("validatePackage", expectedPackageName);
		String response = sendCommandToServer(command);

		if (response.contains("[TRUE]")) {
			return true;
		}
		else {
			reportWhenFalse(response, whenFalse);
			return false;
		}
	}

	// SCROLL AND CLICK
	public boolean scrollAndClick(UiSelectorType selectorType, String selectorString, WhenFalse whenFalse) throws Exception {
		report("Request: scroll to find element with " + selectorType + " = \"" + selectorString + "\", and click it");
		String command = buildCommandString("scrollAndClick", selectorType.toString(), selectorString);
		String response = sendCommandToServer(command);

		if (response.contains("[TRUE]")) {
			return true;
		}
		else {
			reportWhenFalse(response, whenFalse);
			return false;
		}
	}
	
	// SCROLL AND CLICK
	public boolean scrollForElementByScrollView(UiSelectorType selectorType, String selectorString, WhenFalse whenFalse) throws Exception {
		report("Request: scroll to find element with " + selectorType + " = \"" + selectorString + "\", and click it");
		String command = buildCommandString("scrollForElementByScrollView", selectorType.toString(), selectorString);
		String response = sendCommandToServer(command);

		if (response.contains("[TRUE]")) {
			return true;
		}
		else {
			reportWhenFalse(response, whenFalse);
			return false;
		}
	}
	
	
	/* REPORTER METHODS */
	
	private void reportWhenFalse(String text, WhenFalse whenFalse) throws Exception {
		switch (whenFalse) {
		case NORMAL:
			report(text);
			break;
		case WARNING:
			reportWarning(text);
			break;
		case FAIL:
			reportFail(text);
			break;
		case EXCEPTION:
			reportException(text);
			break;
		}
	}
	
	// REPORT SERVER RESPONSE
	private void reportResponseFromServer(String responseStr) throws Exception {
		if (responseStr.contains("warning")) {
			report.report(responseStr, Reporter.WARNING);
		}
		else if (responseStr.contains("error")) {
			report.report(responseStr, Reporter.FAIL);
		}
		else if (responseStr.contains("fatal error")) {
			throw new Exception(responseStr);
		}
		else {
			report.report(responseStr);
		}
	}
	
	// REPORT - NORMAL
	public void report(String text) {
		text = "[uia-client]: " + text;
		report.report(text);
	}

	// REPORT - WARNING
	public void reportWarning(String text) {
		text = "[uia-client]: " + text;
		report.report(text, Reporter.WARNING);
	}

	// REPORT - ERROR
	public void reportFail(String text) {
		text = "[uia-client]: " + text;
		report.report(text, Reporter.FAIL);
	}

	// REPORT - EXCEPTION
	public void reportException(String text) throws Exception {
		text = "[uia-client]: " + text;
		throw new Exception(text);
	}
	
	
	
	/* GETTERS & SETTERS */
	
	public String getUiaServerIpAddress() {
		return uiaServerIpAddress;
	}
	
	public void setUiaServerIpAddress(String uiaServerIpAddress) {
		this.uiaServerIpAddress = uiaServerIpAddress;
	}
	
	public int getUiaServerPort() {
		return uiaServerPort;
	}
	
	public void setUiaServerPort(int uiaServerPort) {
		this.uiaServerPort = uiaServerPort;
	}
	
	public String getUiaServerCommandDelimiter() {
		return uiaServerCommandDelimiter;
	}

	public void setUiaServerCommandDelimiter(String uiaServerCommandDelimiter) {
		this.uiaServerCommandDelimiter = uiaServerCommandDelimiter;
	}

}
