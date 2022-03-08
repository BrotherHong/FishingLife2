package me.brotherhong.fishinglife2.utils;

public class ColorUtil {

    public static int[] toRGB256(String color) {
        int[] result = new int[3];
        char r1 = color.charAt(1); char r2 = color.charAt(2);
        char g1 = color.charAt(3); char g2 = color.charAt(4);
        char b1 = color.charAt(5); char b2 = color.charAt(6);
        // R
        result[0] = hexTo10(r1) * hexTo10(r2);
        // G
        result[1] = hexTo10(g1) * hexTo10(g2);
        // B
        result[2] = hexTo10(b1) * hexTo10(b2);
        return result;
    }

    private static int hexTo10(char c) {
        if ('0' <= c && c <= '9') {
            return c - '0';
        } else {
            return c - 'A' + 10;
        }
    }

}
