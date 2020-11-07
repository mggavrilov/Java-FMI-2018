package bg.uni.sofia.fmi.mjt.streams;

import java.util.Map;

public class StringReplacer {

	StringBuilder input;

	public StringReplacer(String input) {
		if (input != null) {
			this.input = new StringBuilder(input);
		}
	}

	public String replace(Map<String, String> map) {
		if (input == null) {
			return null;
		}

		if (map.size() == 0) {
			return input.toString();
		}

		StringBuilder returnString = new StringBuilder(input);

		for (int i = 0; i < returnString.length(); i++) {
			if (returnString.charAt(i) == '{') {
				int closingBracketIndex = returnString.indexOf("}", i);

				if (closingBracketIndex != -1) {
					String foundString = returnString.substring(i + 1, closingBracketIndex);

					if (map.get(foundString) != null) {
						returnString.replace(i, closingBracketIndex + 1, map.get(foundString));
					}
				}
			}
		}

		return returnString.toString();
	}

}