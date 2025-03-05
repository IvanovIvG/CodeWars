package ru.ivanov;

/**
 * @author Ivan Ivanov
 **/
public class Converter {
    public static double mpgToKPM(int mpg) {
        double res = mpg / 4.54609188 * 1.609344;
        res = res*100;
        res = Math.round(res);
        res = res /100;
        return res;
    }
}
