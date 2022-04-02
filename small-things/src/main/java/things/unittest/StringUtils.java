package things.unittest;

public class StringUtils {
    public static boolean isStringLong(String input) {
        if (input.length() > 5) {
            return true;
        }
        return false;
    }

//    public static boolean isStringLongInOneLine(String input) {
//        return input.length() > 5;
//    }
//
//    public static boolean notWorking() {
//        return true;
//    }
}
