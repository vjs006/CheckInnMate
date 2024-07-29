package stringoperations;

public class StringModifier {
    public static String addSpace(String str) {
        for (int i = 1; i < str.length(); i++) {
            if (Character.isUpperCase(str.charAt(i))) {
                str = str.substring(0, i) + " " + str.substring(i);
                i++;
            }
        }
        return str;
    }

    public static String removeSpace(String str) {
        for (int i = 1; i < str.length(); i++) {
            if (str.charAt(i) == ' ') {
                str = str.substring(0, i) + str.substring(i + 1);
                i--;
            }
        }
        return str;
    }
}
