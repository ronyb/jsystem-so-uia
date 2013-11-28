package il.co.topq.uia.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import com.android.uiautomator.core.UiObject;

/**
 * @author Rony Byalsky
 */
public class UiAutomatorTcpServer extends UiAutomatorWrapper {

	private final String version = "0.1";
	private final int port = 3435;
	private final String commandWrodsDelimiter = "~~";
	
	boolean serverRunning = true;

	ServerSocket serverSocket = null;
	Socket connectionSocket = null;
	DataOutputStream outToClient = null;
	BufferedReader inFromClient = null;

	// THIS IS THE MAIN METHOD THAT'S BEING RUN BY UIATOMATOR ON THE ANDROID DEVICE
	public void testUiAutomatorTcpServer() throws Exception {
		serverSocket = new ServerSocket(port);
		System.out.println("\n******* UI Automator server (v. " + version + ") started and listening on port: " + port + " *******\n");

		connectionSocket = serverSocket.accept();
		inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
		outToClient = new DataOutputStream(connectionSocket.getOutputStream());

		while (serverRunning) {

			try {
				String clientRequest = inFromClient.readLine();
				executeClientRequest(clientRequest);
			}
			catch (Exception e) {
				reportError("Runtime exception: " + e.getMessage(), false);

				serverSocket.close();
				serverSocket = new ServerSocket(port);

				System.out.println("\n******* Client disconnected. Listening on a new connection on port: " + port + " *******\n");

				connectionSocket = serverSocket.accept();
				inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
				outToClient = new DataOutputStream(connectionSocket.getOutputStream());
			}
		}

		serverSocket.close();
	}

	// EXECUTE CLIENT REQUEST
	private void executeClientRequest(String clientRequestStr) throws Exception {

		report("Received from client: " + clientRequestStr, false);

		if (!clientRequestStr.equals("null")) {
			try {
				ClientRequest clientRequest = new ClientRequest(clientRequestStr, commandWrodsDelimiter);
				String methodName = clientRequest.methodName;
				String[] params = clientRequest.methodParams;

				if (methodName.equals("pressHome")) {
					pressHome();
				}
				else if (methodName.equals("pressBack")) {
					pressBack();
				}
				else if (methodName.equals("pressMenu")) {
					pressMenu();
				}
				else if (methodName.equals("pressDPadLeft")) {
					pressDPadLeft();
				}
				else if (methodName.equals("pressDPadRight")) {
					pressDPadRight();
				}
				else if (methodName.equals("pressDPadUp")) {
					pressDPadUp();
				}
				else if (methodName.equals("pressDPadDown")) {
					pressDPadDown();
				}
				else if (methodName.equals("pressDPadCenter")) {
					pressDPadCenter();
				}
				else if (methodName.equals("pressDelete")) {
					pressDelete();
				}
				else if (methodName.equals("pressEnter")) {
					pressEnter();
				}
				else if (methodName.equals("swipe")) {
					swipe(params[0], params[1], params[2], params[3], params[4]);
				}
				else if (methodName.equals("pressKeyCode")) {
					pressKeyCode(params[0]);
				}
				else if (methodName.equals("wakeUp")) {
					wakeUp();
				}
				else if (methodName.equals("pressMenu")) {
					pressMenu();
				}
				else if (methodName.equals("clickXY")) {
					clickXY(params[0], params[1]);
				}
				else if (methodName.equals("clickUiObjectByUiSelector")) {
					clickUiObjectByUiSelector(params[0], params[1]);
				}
				else if (methodName.equals("getTextFromUiObject")) {
					getTextFromUiObject(params[0], params[1]);
				}
				
				else if (methodName.equals("elementExists")) {
					elementExists(params[0], params[1]);
				}
				else if (methodName.equals("scrollAndClick")) {
					scrollAndClick(params[0], params[1]);
				}
				else if (methodName.equals("scrollForElementByScrollView")) {
					scrollForElementByScrollView(params[0], params[1]);
				}
				else if (methodName.equals("validatePackage")) {
					validatePackage(params[0]);
				}
				else if (methodName.equals("stopServer")) {
					stopServer();
				}
				
				else {
					reportFatalError("Unknown method call: \"" + methodName + "\"", true);
				}
			}
			catch (Exception e) {
				reportFatalError("Error parsing input command: \"" + clientRequestStr + "\"", true);
			}
		}
	}



	// STOP SERVER
	private void stopServer() throws Exception {
		reportWarning("UI Automator server is stopping", true);
		serverRunning = false;
	}

	/* CALLS TO UIAUTOMATOR WRAPPER METHODS */

	// PRESS HOME
	public void pressHome() throws Exception {
		if (pressHome_w()) {
			report("[TRUE]: Pressed <HOME>", true);
		}
		else {
			report("[FALSE]: Failed pressing <HOME>", true);
		}
	}

	// PRESS BACK
	public void pressBack() throws Exception {
		if (pressHome_w()) {
			report("[TRUE]: Pressed <BACK>", true);
		}
		else {
			report("[FALSE]: Failed pressing <BACK>", true);
		}
	}

	// PRESS MENU
	public void pressMenu() throws Exception {
		if (pressHome_w()) {
			report("[TRUE]: Pressed <MENU>", true);
		}
		else {
			report("[FALSE]: Failed pressing <MENU>", true);
		}
	}
	
	// PRESS CENTER
	public void pressDPadCenter() throws Exception {
		if (pressDPadCenter_w()) {
			report("[TRUE]: Pressed <CENTER>", true);
		}
		else {
			report("[FALSE]: Failed pressing <CENTER>", true);
		}
	}
	
	// PRESS LEFT
	public void pressDPadLeft() throws Exception {
		if (pressDPadLeft_w()) {
			report("[TRUE]: Pressed <LEFT>", true);
		}
		else {
			report("[FALSE]: Failed pressing <LEFT>", true);
		}
	}

	// PRESS RIGHT
	public void pressDPadRight() throws Exception {
		if (pressDPadRight_w()) {
			report("[TRUE]: Pressed <RIGHT>", true);
		}
		else {
			report("[FALSE]: Failed pressing <RIGHT>", true);
		}
	}

	// PRESS UP
	public void pressDPadUp() throws Exception {
		if (pressDPadUp_w()) {
			report("[TRUE]: Pressed <UP>", true);
		}
		else {
			report("[FALSE]: Failed pressing <LEFT>", true);
		}
	}

	// PRESS DOWN
	public void pressDPadDown() throws Exception {
		if (pressDPadDown_w()) {
			report("[TRUE]: Pressed <DOWN>", true);
		}
		else {
			report("[FALSE]: Failed pressing <DOWN>", true);
		}
	}
	

	// PRESS DELETE
	public void pressDelete() throws Exception {
		if (pressDelete_w()) {
			report("[TRUE]: Pressed <DELETE>", true);
		}
		else {
			report("[FALSE]: Failed pressing <DELETE>", true);
		}
	}
	
	// PRESS ENTER
	public void pressEnter() throws Exception {
		if (pressEnter_w()) {
			report("[TRUE]: Pressed <ENTER>", true);
		}
		else {
			report("[FALSE]: Failed pressing <ENTER>", true);
		}
	}
	
	// PRESS KeyCode
	public void pressKeyCode(String keyCode_str) throws Exception {
		int keyCode = Integer.parseInt(keyCode_str);
		if (pressKeyCode_w(keyCode)) {
			report("[TRUE]: Pressed key code: " + keyCode, true);
		}
		else {
			report("[FALSE]: Failed pressing key code: " + keyCode, true);
		}
	}

	// SWIPE
	public void swipe(String startX_str, String startY_str, String endX_str, String endY_str, String steps_str) throws Exception {
		
		int startX = Integer.parseInt(startX_str);
		int startY = Integer.parseInt(startY_str);
		int endX = Integer.parseInt(endX_str);
		int endY = Integer.parseInt(endY_str);
		int steps = Integer.parseInt(steps_str);
		
		swipe_w(startX, startY, endX, endY, steps);
		report("[TRUE]: Swiped from (" + startX + "," + startY + ") to (" + endX + "," + endY + ") in " + steps + " steps", true);
	}
	
	// WAKE UP
	public void wakeUp() throws Exception {
		wakeUp_w();
		report("[TRUE]: Sent WAKE UP command", true);
	}

	// CLICK X,Y
	public void clickXY(String x_str, String y_str) throws Exception {
		int x = Integer.parseInt(x_str);
		int y = Integer.parseInt(y_str);
		
		if (clickXY_w(x, y)) {
			report("[TRUE]: Clicked at (" + x + "," + y + ")", true);
		}
		else {
			report("[FALSE]: Failed to click at (" + x + "," + y + ")", true);
		}
	}
	
	// CLICK UI OBJECT BY SELECTOR TYPE
	public void clickUiObjectByUiSelector(String selectorTypeStr, String selectorString) throws Exception {
		UiSelectorType selectorType = UiSelectorType.valueOf(selectorTypeStr);
		
		boolean result = clickUiObjectByUiSelector_w(selectorType, selectorString);
		if (result) {
			report("[TRUE]: Clicked on element with " + selectorType + " = \"" + selectorString + "\"", true);
		}
		else {
			report("[FALSE]: Couldn't click on element with " + selectorType + " = \"" + selectorString + "\"", true);
		}
	}
	
	/*
	// CLICK UI OBJECT
	public void clickUiObject(String clickTypeStr, String selectorTypeStr, String selectorString) throws Exception {
		UiSelectorType selectorType = UiSelectorType.valueOf(selectorTypeStr);
		ClickType clickType = ClickType.valueOf(clickTypeStr);

		boolean result = clickUiObject(clickType, selectorType, selectorString);
		if (result) {
			report("[TRUE]: Clicked on element with " + selectorType + " = \"" + selectorString + "\"", true);
		}
		else {
			report("[FALSE]: Couldn't click on element with " + selectorType + " = \"" + selectorString + "\"", true);
		}
	}
	*/
	
	// GET TEXT FROM UI OBJECT
	public void getTextFromUiObject(String selectorTypeStr, String selectorString) throws Exception {
		UiSelectorType selectorType = UiSelectorType.valueOf(selectorTypeStr);
		
		String result = getTextFromUiObject_w(selectorType, selectorString);
		if (result != null) {
			report("[TRUE]: Got text from element with " + selectorType + " = \"" + selectorString + "\": " + result, true);
		}
		else {
			report("[FALSE]: Couldn't get text from element with " + selectorType + " = \"" + selectorString + "\"", true);
		}
	}

	// ELEMENT EXISTS?
	public void elementExists(String selectorTypeStr, String selectorString) throws Exception {
		UiSelectorType selectorType = UiSelectorType.valueOf(selectorTypeStr);

		boolean result = uiObjectExists_w(selectorType, selectorString);
		if (result) {
			report("[TRUE]: Found element with " + selectorType + " = \"" + selectorString + "\"", true);
		}
		else {
			report("[FALSE]: Didn't find element with " + selectorType + " = \"" + selectorString + "\"", true);
		}
	}

	// SCROLL AND CLICK
	public void scrollAndClick(String selectorTypeStr, String selectorString) throws Exception {
		UiSelectorType selectorType = UiSelectorType.valueOf(selectorTypeStr);

		boolean result = scrollAndClick(selectorType, selectorString);
		if (result) {
			report("[TRUE]: Found element with " + selectorType + " = \"" + selectorString + "\" on scrollable - and clicked", true);
		}
		else {
			report("[FALSE]: Didn't find element with " + selectorType + " = \"" + selectorString + "\" on scrollable", true);
		}
	}
	
	// SCROLL FOR ELEMENT
	public void scrollForElementByScrollView(String selectorTypeStr, String selectorString) throws Exception {
		UiSelectorType selectorType = UiSelectorType.valueOf(selectorTypeStr);

		UiObject result = scrollForElementByScrollView(selectorType, selectorString);
		if (result != null) {
			report("[TRUE]: Found element with " + selectorType + " = \"" + selectorString + "\" on scrollable - and clicked", true);
		}
		else {
			report("[FALSE]: Didn't find element with " + selectorType + " = \"" + selectorString + "\" on scrollable", true);
		}
	}


	// VALIDATE PACKAGE
	public void validatePackage(String expectedPackageName) throws Exception {
		boolean result = packageExists_w(expectedPackageName);
		if (result) {
			report("[TRUE]: On expected packege: " + expectedPackageName, true);
		}
		else {
			report("[FALSE]: Not on expected packege: " + expectedPackageName, true);
		}
	}

	/* REPORTER METHODS */
	
	// REPORT - NORMAL
	private void report(String text, boolean writeToClient) throws Exception {
		String reposnse = "[uia-server]: " + text;
		System.out.println(reposnse);
		if (writeToClient) outToClient.writeBytes(reposnse + "\n");
	}

	// REPORT - WARNING
	private void reportWarning(String text, boolean writeToClient) throws Exception {
		String reposnse = "[uia-server > warning]: " + text;
		System.out.println(reposnse);
		if (writeToClient) outToClient.writeBytes(reposnse + "\n");
	}

	// REPORT - ERROR
	private void reportError(String text, boolean writeToClient) throws Exception {
		String reposnse = "[uia-server > error]: " + text;
		System.out.println(reposnse);
		if (writeToClient) outToClient.writeBytes(reposnse + "\n");
	}

	// REPORT - FATAL ERROR
	private void reportFatalError(String text, boolean writeToClient) throws Exception {
		String reposnse = "[uia-server > fatal error]: " + text;
		System.out.println(reposnse);
		if (writeToClient) outToClient.writeBytes(reposnse + "\n");
	}
}
