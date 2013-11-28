package il.co.topq.uia.server;

import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiScrollable;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

/**
 * @author Rony Byalsky
 */
public abstract class UiAutomatorWrapper extends UiAutomatorTestCase {

	/**
	 * Press on the Home button
	 */
	public boolean pressHome_w() throws Exception {
		return getUiDevice().pressHome();
	}

	/**
	 * Press on the Back button
	 */
	public boolean pressBack_w() throws Exception {
		return getUiDevice().pressBack();
	}

	/**
	 * Press on the Menu button
	 */
	public boolean pressMenu_w() throws Exception {
		return getUiDevice().pressMenu();
	}
	
	/**
	 * Simulates pressing the power button if the screen is OFF else it does nothing if the screen is already ON
	 */
	public void wakeUp_w() throws Exception {
		getUiDevice().wakeUp();
	}
	
	/**
	 * Simulates a short press on the CENTER button.
	 */
	public boolean pressDPadCenter_w() throws Exception {
		return getUiDevice().pressDPadCenter();
	}

	/**
     * Simulates a short press on the LEFT button.
	 */
	public boolean pressDPadLeft_w() throws Exception {
		return getUiDevice().pressDPadLeft();
	}

	/**
	 * Simulates a short press on the RIGHT button.
	 */
	public boolean pressDPadRight_w() throws Exception {
		return getUiDevice().pressDPadRight();
	}

	/**
	 * Simulates a short press on the UP button.
	 */
	public boolean pressDPadUp_w() throws Exception {
		return getUiDevice().pressDPadUp();
	}

	/**
	 * Simulates a short press on the DOWN button.
	 */
	public boolean pressDPadDown_w() throws Exception {
		return getUiDevice().pressDPadDown();
	}
	
	/**
	 * Simulates a short press on the DELETE key.
	 */
	public boolean pressDelete_w() throws Exception {
		return getUiDevice().pressDelete();
	}

	/**
	 * Simulates a short press on the ENTER key.
	 */
	public boolean pressEnter_w() throws Exception {
		return getUiDevice().pressEnter();
	}

	/**
	 * Simulates a short press using a key code.
	 */
	public boolean pressKeyCode_w(int keyCode) throws Exception {
		return getUiDevice().pressKeyCode(keyCode);
	}
	
	/**
	 * Simulates a short click on the specified (x, y) coordinates
	 * @author Rony Byalsky
	 */
	public boolean clickXY_w(int x, int y) throws Exception {
		return getUiDevice().click(x, y);
	}
	
	/**
	 * Performs a swipe from one coordinate to another using the number of steps to determine smoothness and speed
	 */
	public boolean swipe_w(int startX, int startY, int endX, int endY, int steps) throws Exception {
		return getUiDevice().swipe(startX, startY, endX, endY, steps);
	}
	
	/**
	 * Get the UI object by "selector type", and click it.
	 * @author Rony Byalsky
	 */
	public boolean clickUiObjectByUiSelector_w(UiSelectorType selectorType, String selectorString) throws Exception {
		return getUiObjectByUiSelector(selectorType, selectorString).click();
	}
	
	/**
	 * Get the UI object by "path", and click it.
	 * @author Rony Byalsky
	 */
	public boolean clickUiObjectByPath_w(int... path) throws Exception {
		return getUiObjectByPath(path).click();
	}
	
	/**
	 * Get a UI object by the UI selector type (Text, Text contains, description, description contains, Index or class name)
	 * @param selectorType
	 * @param selectorString
	 * @return UiObject
	 * @author Rony Byalsky
	 */
	public UiObject getUiObjectByUiSelector(UiSelectorType selectorType, String selectorString) {
		try {
			switch (selectorType) {
			case TEXT:
				return new UiObject(new UiSelector().text(selectorString));
			case TEXT_CONTAINS:
				return new UiObject(new UiSelector().textContains(selectorString));
			case DESCRIPTION:
				return new UiObject(new UiSelector().description(selectorString));
			case DESCRIPTION_CONTAINS:
				return new UiObject(new UiSelector().descriptionContains(selectorString));
			case INDEX:
				return new UiObject(new UiSelector().index(Integer.parseInt(selectorString)));
			case CLASS_NAME:
				return new UiObject(new UiSelector().className(selectorString));
			default:
				return null;
			}
		}
		catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * Get a UI object by its "path" - The path of indexes leading to the requested object,
	 * as can be seen in the "UI Automator Viewer" tool.
	 * @param path - A sequence of integers, specifying the "child-indexes" of the objects on the path to the requested object.
	 * @return UiObject
	 * @author Rony Byalsky
	 */
	public UiObject getUiObjectByPath(int... path) {
		
		try {
			// The root is always android.widget.FrameLayout
			UiObject uiObject = getUiObjectByUiSelector(UiSelectorType.CLASS_NAME, "android.widget.FrameLayout");
			
			for (int i=1; i<path.length; i++) {
				uiObject = uiObject.getChild(new UiSelector().index(path[i]));
			}
			return uiObject;
		}
		catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * Get the text of a UI object
	 * @param selectorType
	 * @param selectorString
	 * @return String
	 * @author Rony Byalsky
	 */
	public String getTextFromUiObject_w(UiSelectorType selectorType, String selectorString) {
		try {
			return getUiObjectByUiSelector(selectorType, selectorString).getText();
		}
		catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * Check if the UI object exists on screen
	 * @author Rony Byalsky
	 */
	public boolean uiObjectExists_w(UiSelectorType selectorType, String selectorString) {
		return getUiObjectByUiSelector(selectorType, selectorString).exists();
	}
	
	/**
	 * Check if currently on the package with the specified name
	 * @author Rony Byalsky
	 */
	public boolean packageExists_w(String expectedPackageName) {
		return new UiObject(new UiSelector().packageName(expectedPackageName)).exists();
	}

	/**
	 * Scroll the screen until the UI object is visible, then click on it
	 * @author Rony Byalsky
	 */
	public boolean scrollAndClick(UiSelectorType selectorType, String selectorString) {

		UiScrollable scrollable = new UiScrollable(new UiSelector().scrollable(true));

		try {
			UiObject element = null;

			switch (selectorType) {
			case TEXT:
				element = scrollable.getChildByText(new UiSelector().className(android.widget.TextView.class.getName()), selectorString, true);
				break;
			case TEXT_CONTAINS:
				break;
			case DESCRIPTION:
				break;
			case DESCRIPTION_CONTAINS:
				break;
			}

			element.clickAndWaitForNewWindow();
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}
	
	// SCROLL TO OBJECT
	public UiObject scrollForElementByScrollView(UiSelectorType selectorType, String selectorString) {
		UiScrollable scrollable = new UiScrollable(new UiSelector().className(android.widget.ScrollView.class.getName()).scrollable(true));
		try {
			UiObject element = null;
			switch (selectorType) {
			case TEXT:
				element = scrollable.getChildByText(new UiSelector().className(android.widget.TextView.class.getName()), selectorString, true);
				break;
			case DESCRIPTION:
				break;
			default:
				return null;
			}
			return element;
		}
		catch (Exception e) {
			return null;
		}
	}
	
}