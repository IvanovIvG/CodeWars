package ru.ivanov;

/**
 * @author Ivan Ivanov
 **/
public class Maskify {
    public static String maskify(String str) {
        int length = str.length();
        if(length<=4){
            return str;
        }
        int visiblePartFirstIndex = length - 4;
        String visiblePart = str.substring(visiblePartFirstIndex);
        int maskifyLength = length - 4;
        return "#".repeat(maskifyLength) + visiblePart;
    }
}
