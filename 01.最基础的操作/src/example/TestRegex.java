package example;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestRegex {
	public static void main(String[] args) {
		String[] sourceString = { "//|Zoneinfo|1122|8900", "Zone//|info|1122|8900", "Zoneinfo//||1122|8900",
				"Zoneinfo|//|1122|8900", "Zoneinfo|11//|22|8900", "Zoneinfo|1122//||8900", "Zoneinfo|1122|//|8900",
				"Zoneinfo|1122|89//|00", "Zoneinfo|1122|8900//|" };
		
		String regex = "((//////|)*//w+(//////|)*//w*)(?=//|)?";
		Pattern p = Pattern.compile(regex);
		int lineNumber = 1;
		StringBuilder result = new StringBuilder();
		for (String string : sourceString) {
			result.append("Source string is: ").append(string);
			System.out.println(result.toString());
			result.delete(0, result.length());

			Matcher m = p.matcher(string);
			while (m.find()) {
				result.append(lineNumber++).append("  ").append(m.group(0));
				System.out.println(result.toString());
				result.delete(0, result.length());
			}
			lineNumber = 1;
			System.out.println("---------华丽的分割线----------");
		}
	}
}