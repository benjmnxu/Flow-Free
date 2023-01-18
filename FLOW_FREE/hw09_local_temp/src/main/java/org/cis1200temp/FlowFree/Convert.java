package org.cis1200temp.FlowFree;

import java.awt.*;

public class Convert {

    public static Color myGreen = new Color(0, 200, 0);

    public static Color stringToColor(String s) {
        if (s.equals("R")) {
            return Color.RED;
        } else if (s.equals("G")) {
            return myGreen;
        } else if (s.equals("B")) {
            return Color.BLUE;
        } else if (s.equals("Y")) {
            return Color.YELLOW;
        } else if (s.equals("O")) {
            return Color.ORANGE;
        } else if (s.equals("P")) {
            return Color.PINK;
        } else if (s.equals("C")) {
            return Color.CYAN;
        }
        return Color.WHITE;
    }

    public static String colorToString(Color c) {
        if (c.equals(Color.RED)) {
            return "R";
        } else if (c.equals(myGreen)) {
            return "G";
        } else if (c.equals(Color.BLUE)) {
            return "B";
        } else if (c.equals(Color.YELLOW)) {
            return "Y";
        } else if (c.equals(Color.ORANGE)) {
            return "O";
        } else if (c.equals(Color.PINK)) {
            return "P";
        } else if (c.equals(Color.CYAN)) {
            return "C";
        }
        return "L";
    }

    public static Color intToColor(int i) {
        if (i == 2) {
            return Color.RED;
        } else if (i == 3) {
            return Color.BLUE;
        } else if (i == 4) {
            return myGreen;
        } else if (i == 5) {
            return Color.YELLOW;
        } else if (i == 6) {
            return Color.ORANGE;
        } else if (i == 7) {
            return Color.PINK;
        } else if (i == 8) {
            return Color.CYAN;
        }
        return Color.BLACK;
    }

    public static int colorToInt(Color c) {
        if (c.equals(Color.RED)) {
            return 2;
        } else if (c.equals(Color.BLUE)) {
            return 3;
        } else if (c.equals(myGreen)) {
            return 4;
        } else if (c.equals(Color.YELLOW)) {
            return 5;
        } else if (c.equals(Color.ORANGE)) {
            return 6;
        } else if (c.equals(Color.PINK)) {
            return 7;
        } else if (c.equals(Color.CYAN)) {
            return 8;
        }
        return 9;
    }

    public static int stringToInt(String s) {
        return colorToInt(stringToColor(s));
    }

    public static String intToString(int s) {
        return colorToString(intToColor(s));
    }

}
