package Auditoriska_1;

public class StringPrefix {
    public static boolean isPrefix(String a, String b) {
        if (a.length() > b.length()) {
            return false;
        }
        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) != b.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(isPrefix("12345", "12345678"));
    }
}
