import java.awt.*;

/**
 * Created by Dylan on 22/05/16.
 */
public class DEColorCalculator {

    private static final String chars = "0123456789abcdef";

    /**
     * Accepts strings like "#123abc" and "A2bC99"
     *
     * @param str
     * @return
     */
    public static Color getColorForString(String str) {
        String colorString = str;
        if (str.length() == 7 && str.startsWith("#"))
            colorString = str.substring(1);

        if (!checkValidColorHexString(str)) return null;
        int r = getDecimalValueOfHexStringColor(colorString.substring(0, 2));
        int g = getDecimalValueOfHexStringColor(colorString.substring(2, 4));
        int b = getDecimalValueOfHexStringColor(colorString.substring(4, 6));
        return new Color(r, g, b);
    }

    /**
     * Makes sure all the characters in str are in the chars
     * array and that the length is 6
     *
     * @param str
     * @return
     */
    private static boolean checkValidColorHexString(String str) {
        if (str.length() != 6) return false;

        for (int c = 0; c < str.length(); c++) {
            if (chars.indexOf(str.charAt(c)) == -1) return false;
        }

        return true;
    }

    /**
     * Only accepts 2 character strings. Assumes the string is valid
     *
     * @param str
     * @return
     */
    private static int getDecimalValueOfHexStringColor(String str) {
        assert str.length() == 2 : "DEColorCalculator.getDecimalValueOfHexString(String)"
                + " only accepts 2-character strings";

        int base = chars.length();
        int[] digits = new int[str.length()];

        String lower = str.toLowerCase();

        for (int c = 0; c < str.length(); c++) {
            digits[c] = chars.indexOf(lower.charAt(c));
        }

        return digits[0] * chars.length() + digits[1];
    }

}
