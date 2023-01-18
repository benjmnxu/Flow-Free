package org.cis1200temp.FlowFree;

/*
 * CIS 120 HW09 - FLOW FREe
 * (c) University of Pennsylvania
 * Created by Benjami Xu
 */

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;

@SuppressWarnings("serial")
public class FlowBoardController extends JPanel {

    private HashMap<Coordinates, String> spots; // map of spots to be drawn
    private HashMap<Integer, LinkedList<Coordinates>> order;
    private int[][] grid;
    private LinkedList<Color> colorDraw;

    // Game constants
    public int BOARD_WIDTH;
    public int BOARD_HEIGHT;
    public String LEVEL;
    public boolean VALID_LEVEL;
    public boolean DRAW;
    public boolean FREEZE;

    /**
     * Initializes the game board.
     */
    public FlowBoardController() {
        spots = new HashMap<Coordinates, String>();
        order = new HashMap<Integer, LinkedList<Coordinates>>();
        colorDraw = new LinkedList<Color>();
        VALID_LEVEL = false;
        DRAW = false;
        FREEZE = false;
    }

    // Tests adjacency of line "head". The last element in the line.
    public boolean adjacent(LinkedList<Coordinates> coordinates, Coordinates c) {
        boolean adj = false;
        if (!coordinates.isEmpty()) {
            int x = coordinates.getLast().x();
            int y = coordinates.getLast().y();
            if (y == c.y() && (x == c.x() + 1 || x == c.x() - 1)) {
                adj = true;
            } else if (x == c.x() && (y == c.y() + 1 || y == c.y() - 1)) {
                adj = true;
            }
        }
        return adj;
    }

    // Tests to see if a whole line is connected completely.
    public boolean allAdjacent(LinkedList<Coordinates> coordinates, int color) {
        boolean adj = false;
        ListIterator<Coordinates> iter = coordinates.listIterator();
        Coordinates first = new Coordinates(-10, -10);
        if (iter.hasNext()) {
            first = iter.next();
            adj = true;
        }
        while (iter.hasNext()) {
            Coordinates c = iter.next();
            if (first.y() == c.y() && (first.x() == c.x() + 1 || first.x() == c.x() - 1)) {
                adj = true;
            } else if (first.x() == c.x() && (first.y() == c.y() + 1 || first.y() == c.y() - 1)) {
                adj = true;
            } else {
                resetGrid(color);
                return false;
            }
            first = c;
        }
        return adj;
    }

    public void removeIntersection(int y, int x) {
        int c = grid[y][x];
        order.get(c).remove(new Coordinates(x, y));
        if (!allAdjacent(order.get(c), c)) {
            colorDraw.removeFirstOccurrence(Convert.intToColor(c));
            for (Map.Entry<Coordinates, String> Entry : spots.entrySet()) {
                if (Entry.getValue().equals(Convert.intToString(c))) {
                    grid[Entry.getKey().y()][Entry.getKey().x()] = 0;
                }
            }
        }
        if (order.get(c) == null || order.get(c).isEmpty()) {
            order.remove(c);
        }
    }

    /**
     * (Re-)sets the game to its initial state.
     */

    /**
     * Updates the JLabel to reflect the current state of the game.
     */
    public void updateStatus() {
        int spotsLength = 0;
        int spotsConnected = 0;
        for (Map.Entry<Coordinates, String> Entry : spots.entrySet()) {
            int x = Entry.getKey().x();
            int y = Entry.getKey().y();
            if (grid[y][x] > 1) {
                spotsConnected++;
            }
            spotsLength++;
        }
        if (spotsLength == spotsConnected) {
            FREEZE = true;
            NextLevel.create(this);
        }
    }

    public void undo(FlowBoard f) {
        if (!FREEZE) {
            if (colorDraw.peek() != null) {
                int color = Convert.colorToInt(colorDraw.pop());
                resetGrid(color);
            }
            f.repaint();
        }
    }

    public void save() {
        if (!FREEZE) {
            File file = new File(
                    "src/main/java/org/cis1200/FlowFree/SaveFiles/Save: " + LEVEL
            );
            try {
                if (!file.exists()) {
                    file.createNewFile();
                }
                FileWriter fW = new FileWriter(file);
                BufferedWriter bW = new BufferedWriter(fW);
                bW.write(String.valueOf(BOARD_WIDTH / 100));
                bW.newLine();
                bW.write(String.valueOf(BOARD_HEIGHT / 100));
                bW.newLine();
                for (Map.Entry<Coordinates, String> Entry : spots.entrySet()) {
                    bW.write(
                            Entry.getValue() + " " + Entry.getKey().x() + " " + Entry.getKey().y()
                    );
                    bW.newLine();
                }
                for (int i = 0; i < grid[0].length; i++) {
                    for (int j = 0; j < grid.length; j++) {
                        bW.write(String.valueOf(grid[i][j]));
                    }
                    bW.newLine();
                }

                for (Map.Entry<Integer, LinkedList<Coordinates>> Entry : order.entrySet()) {
                    bW.write("C " + String.valueOf(Entry.getKey()));
                    for (Coordinates c : Entry.getValue()) {
                        bW.write(" " + c.x() + " " + c.y());
                    }
                    bW.newLine();
                }
                bW.write("D");
                for (Color c : colorDraw) {
                    bW.write(" " + Convert.colorToString(c));
                }

                bW.flush();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void set(String s) {
        File file;
        if (s.contains("/")) {
            file = new File(s);
            String[] stringParts = s.split("/");
            if (stringParts.length > 1) {
                LEVEL = stringParts[stringParts.length - 1];
                String[] p = stringParts[stringParts.length - 1].split(" ");
                if (p.length > 1) {
                    LEVEL = p[1];
                }
            }
        } else {
            file = new File("src/main/java/org/cis1200/FlowFree/Levels/" + s);
            LEVEL = s;
        }
        FileReader fR;
        BufferedReader bR;
        FileLineIterator fli;
        try {
            fR = new FileReader(file);
            bR = new BufferedReader(fR);
            fli = new FileLineIterator(bR);
            BOARD_WIDTH = 100 * Integer.parseInt(fli.next());
            BOARD_HEIGHT = 100 * Integer.parseInt(fli.next());
            grid = new int[BOARD_HEIGHT / 100][BOARD_WIDTH / 100];
            while (fli.hasNext()) {
                String spt = fli.next();
                String[] parts = spt.split(" ");
                if (parts.length == 1) {
                    loadRest(fli, spt);
                    break;
                } else {
                    Coordinates xy = new Coordinates(
                            Integer.parseInt(parts[1]),
                            Integer.parseInt(parts[2])
                    );
                    spots.put(xy, parts[0]);
                    grid[xy.y()][xy.x()] = 1;
                }
            }
            VALID_LEVEL = true;
        } catch (FileNotFoundException f) {
            if (Integer.parseInt(LEVEL) > 6) {
                JFrame jF = new JFrame();
                JLabel label = new JLabel("No More Levels. Buy premium for more levels");
                JButton button = new JButton("Click Here for Premium");
                button.addActionListener(e -> BoardsController.buyPremium());
                jF.add(button, BorderLayout.SOUTH);
                jF.add(label, BorderLayout.CENTER);
                jF.pack();
                jF.setVisible(true);
            } else {
                VALID_LEVEL = false;
            }
        }

    }

    public void loadRest(FileLineIterator fli, String s) {
        int loadGridRow = 0;
        String spt = s;
        for (int i = 0; i < spt.length(); i++) {
            grid[loadGridRow][i] = Integer.parseInt(String.valueOf(spt.charAt(i)));
        }
        loadGridRow++;
        while (fli.hasNext()) {
            spt = fli.next();
            String[] parts = spt.split(" ");
            if (parts[0].equals("C")) {
                LinkedList<Coordinates> coords = new LinkedList<Coordinates>();
                for (int i = 2; i < parts.length; i += 2) {
                    coords.add(
                            new Coordinates(
                                    Integer.parseInt(parts[i]), Integer.parseInt(parts[i + 1])
                            )
                    );
                }
                order.put(Integer.parseInt(parts[1]), coords);
            } else if (parts[0].equals("D")) {
                System.out.println("D");
                for (int i = 1; i < parts.length; i++) {
                    System.out.println(parts[i]);
                    colorDraw.addLast(Convert.stringToColor(parts[i]));
                }
            } else {
                for (int i = 0; i < spt.length(); i++) {
                    grid[loadGridRow][i] = Integer.parseInt(String.valueOf(spt.charAt(i)));
                }
                loadGridRow++;
            }
        }

    }

    public void resetGrid(int c) {
        order.remove(c);
        if (grid != null) {
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[0].length; j++) {
                    if (grid[j][i] == c) {
                        grid[j][i] = 0;
                    }
                }
            }
        }
    }

    public HashMap<Integer, LinkedList<Coordinates>> getOrder(boolean encap) {
        if (encap) {
            HashMap<Integer, LinkedList<Coordinates>> r = new HashMap<Integer, LinkedList<Coordinates>>();
            r.putAll(order);
            return r;
        }
        return order;
    }

    public LinkedList<Coordinates> getOrderList(boolean encap, int c) {
        if (encap) {
            LinkedList<Coordinates> l = new LinkedList<Coordinates>();
            l.addAll(order.get(c));
            return l;
        }
        return order.get(c);
    }

    public HashMap<Coordinates, String> getSpots(boolean encap) {
        if (encap) {
            HashMap<Coordinates, String> r = new HashMap<Coordinates, String>();
            r.putAll(spots);
            return r;
        }
        return spots;
    }

    public int[][] getGrid(boolean encap) {
        if (encap) {
            int[][] g = new int[grid[0].length][grid.length];
            for (int i = 0; i < grid[0].length; i++) {
                for (int j = 0; j < grid.length; j++) {
                    g[i][j] = grid[i][j];
                }
            }
            return g;
        }
        return grid;
    }

    public LinkedList<Color> getColorDraw(boolean encap) {
        if (encap) {
            LinkedList<Color> l = new LinkedList<Color>();
            l.addAll(colorDraw);
            return l;
        }
        return colorDraw;
    }
}
