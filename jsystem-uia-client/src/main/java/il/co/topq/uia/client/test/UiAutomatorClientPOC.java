package il.co.topq.uia.client.test;

import il.co.topq.uia.client.infra.enums.UiSelectorType;
import il.co.topq.uia.client.infra.enums.WhenFalse;
import il.co.topq.uia.client.infra.so.UiAutomatorTcpClient;
import jsystem.framework.TestProperties;
import junit.framework.SystemTestCase4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Rony Byalsky
 */
public class UiAutomatorClientPOC extends SystemTestCase4 {

	UiAutomatorTcpClient uia;
	
	@Before
	public void before() throws Exception {
		uia = (UiAutomatorTcpClient) system.getSystemObject("uia-client");
		uia.init();
		uia.openSocket();
	}
	
	@Test
	@TestProperties(name="Get total storage")
	public void getTotalStorage() throws Exception {
		
		uia.pressMenu();
		uia.pressMenu();
		uia.clickUiObjectByUiSelector(UiSelectorType.TEXT, "System settings", WhenFalse.FAIL);
		uia.validatePackage("com.android.settings", WhenFalse.FAIL);
		uia.scrollAndClick(UiSelectorType.TEXT, "Storage", WhenFalse.FAIL);
		
		String str = uia.getTextFromUiObject(UiSelectorType.TEXT_CONTAINS, "GB", WhenFalse.WARNING);
		if (str == null) {
			str = uia.getTextFromUiObject(UiSelectorType.TEXT_CONTAINS, "MB", WhenFalse.FAIL);
		}
		
		report("********** Total space = " + str + " ***************");
	}
	
	@Test
	@TestProperties(name="WakeUp, Unlock Screen")
	public void wakeUpAndUnlockScreen() throws Exception {
		uia.wakeUp();
		unlockScreen();
		
		uia.pressHome();
		uia.clickUiObjectByUiSelector(UiSelectorType.DESCRIPTION, "Apps", WhenFalse.EXCEPTION);
		uia.clickUiObjectByUiSelector(UiSelectorType.TEXT, "Calculator", WhenFalse.EXCEPTION);
	}
	
	@Test
	@TestProperties(name="Enter Wi-Fi Screen")
	public void EnterWiFiScreen() throws Exception {
		uia.pressHome();
		uia.pressMenu();
		uia.clickUiObjectByUiSelector(UiSelectorType.TEXT, "System settings", WhenFalse.FAIL);
		uia.scrollAndClick(UiSelectorType.TEXT, "Wi-Fi", WhenFalse.FAIL);
	}
	
	private void unlockScreen() throws Exception {
		int x1 = 242;
		int y1 = 637;
		int x2 = 445;
		int y2 = 638;
		uia.swipe(x1, y1, x2, y2, 5, WhenFalse.FAIL);
	}
	
	@After
	public void after() throws Exception {
		uia.closeSocket();
	}
	
	public void report(String text) {
		report.report(text);
	}
}
