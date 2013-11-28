package il.co.topq.uia.client.infra.utils;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Rony Byalsky
 */
public class Regex {

	public static String getGroup(String text, String regex, int groupNumber) {
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(text);
		if (m.find()) {
			return m.group(groupNumber);
		} else {
			return null;
		}
	}
	
	public static String[] getAllMatches(String text, String regex) {
		
		ArrayList<String> matches = new ArrayList<String>();
		
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(text);
		
		while (m.find()) {
			matches.add(m.group(1));
		}
		
		String[] matchesArr = new String[matches.size()];
		matches.toArray(matchesArr);
		
		return matchesArr;
	}
}
