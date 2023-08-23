package com.sabiantools.utilities;

public class SabianArrays {
    public static <T> boolean exists(T[] array, T value) {
        for (T t : array) {
            if (value.equals(t))
                return true;
        }
        return false;
    }

    public static boolean exists(int[] array, int value) {
        for (int t : array) {
            if (value == t)
                return true;
        }
        return false;
    }
}
