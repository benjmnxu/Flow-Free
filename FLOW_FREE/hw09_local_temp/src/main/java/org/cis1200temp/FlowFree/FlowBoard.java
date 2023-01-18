package org.cis1200temp.FlowFree;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map;

public class FlowBoard extends JPanel {

    private FlowBoardController gB;

    public FlowBoard(FlowBoardController g) {

        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Enable keyboard focus on the court area. When this component has the
        // keyboard focus, key events are handled by its key listener.
        setFocusable(true);

        gB = g;
        addMouseListener(new MouseAdapter() {
            Point p;

            @Override
            public void mouseReleased(MouseEvent e) {
                gB.DRAW = false;
                if ((int) p.getX() / 100 == (int) e.getX() / 100
                        && (int) p.getY() / 100 == (int) e.getY() / 100) {
                    gB.getColorDraw(false).removeFirst();
                }
                // updateStatus(); // updates the status JLabel
                repaint(); // repaints the game board
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (!gB.FREEZE) {
                    p = e.getPoint();
                    int x = (int) p.getX() / 100;
                    int y = (int) p.getY() / 100;
                    if (p.getY() < gB.BOARD_HEIGHT && p.getX() < gB.BOARD_WIDTH) {
                        Coordinates c = new Coordinates(x, y);
                        if (gB.getSpots(true).containsKey(c)) {
                            gB.DRAW = true;
                            gB.resetGrid(
                                    Convert.stringToInt(gB.getSpots(true).get(c))
                            );

                            gB.getColorDraw(false)
                                    .addFirst(Convert.stringToColor(gB.getSpots(true).get(c)));

                            LinkedList<Coordinates> tp = new LinkedList<Coordinates>();
                            tp.add(c);
                            gB.getOrder(false).put(
                                    Convert.stringToInt(gB.getSpots(true).get(c)), tp
                            );

                            gB.getGrid(false)[y][x] = Convert
                                    .colorToInt(gB.getColorDraw(true).getFirst());
                        }
                    }
                }
            }
        });
        addMouseMotionListener(new MouseMotionListener() {

            @Override
            public void mouseDragged(MouseEvent e) {
                if (!gB.FREEZE) {
                    Point p = e.getPoint();
                    int x = (int) p.getX() / 100;
                    int y = (int) p.getY() / 100;
                    if (p.getY() < gB.BOARD_HEIGHT && p.getX() < gB.BOARD_WIDTH) {
                        Coordinates c = new Coordinates(x, y);
                        int cNum = Convert.colorToInt(gB.getColorDraw(true).getFirst());
                        if (gB.DRAW && !gB.getOrderList(true, cNum).contains(c)) {
                            if ((!gB.getSpots(true).containsKey(c))) {
                                if (gB.adjacent(gB.getOrder(true).get(cNum), c)) {
                                    gB.getOrderList(false, cNum).add(c);
                                }
                                if (gB.getGrid(true)[y][x] > 1) {
                                    gB.removeIntersection(y, x);
                                }
                                if (gB.adjacent(gB.getOrderList(true, cNum), c) ||
                                        gB.getOrderList(true, cNum).getLast().equals(c)) {
                                    gB.getGrid(false)[y][x] = cNum;
                                }
                            } else if (!gB.getOrder(true).get(cNum).contains(c)) {
                                if (Convert.stringToInt(gB.getSpots(true).get(c)) == cNum &&
                                        gB.adjacent(gB.getOrderList(true, cNum), c)) {
                                    gB.getOrder(false).get(cNum).add(c);
                                    gB.getGrid(false)[y][x] = cNum;

                                }
                                gB.DRAW = false;
                            }
                            /*
                             * if(gB.getSpots(true).get(c) != null &&
                             * gB.getSpots(true).get(c).equals(Convert.intToString(cNum))){
                             * gB.DRAW = false;
                             * }
                             */
                        } else if (gB.getOrder(true).get(cNum).contains(c)
                                && !c.equals(gB.getOrder(true).get(cNum).getLast())) {
                            LinkedList<Coordinates> temp = gB.getOrderList(true, cNum);
                            if (temp != null) {
                                Coordinates prev = temp.removeLast();

                                if (c.equals(
                                        temp.getLast()
                                )) {
                                    gB.getOrder(false).get(cNum).removeLast();
                                    gB.getGrid(false)[prev.y()][prev.x()] = 0;
                                }
                            }
                        } else if (gB.getOrder(true).get(cNum).contains(c)
                                && !gB.getSpots(true).containsKey(c)) {
                            gB.DRAW = true;
                        }
                        gB.updateStatus();
                        repaint();
                    }
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
            } // No change needed
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(new Color(20, 20, 20));

        // Draws horizontal grid lines
        for (int i = 1; i < (gB.BOARD_HEIGHT / 100); i++) {
            g.setColor(Color.gray);
            g.drawLine(0, i * 100, gB.BOARD_WIDTH, i * 100);
        }

        // Draws vertical grid lines
        for (int i = 1; i < (gB.BOARD_WIDTH / 100); i++) {
            g.setColor(Color.gray);
            g.drawLine(i * 100, 0, i * 100, gB.BOARD_HEIGHT);
        }

        // Draws initial spots
        for (int i = 0; i < gB.BOARD_WIDTH / 100; i++) {
            for (int j = 0; j < gB.BOARD_HEIGHT / 100; j++) {
                Coordinates xy = new Coordinates(i, j);
                if (gB.getSpots(true).containsKey(xy)) {
                    g.setColor(Convert.stringToColor(gB.getSpots(true).get(xy)));
                    g.fillOval(20 + (i * 100), 20 + (j * 100), 60, 60);
                }
            }
        }

        for (Map.Entry<Integer, LinkedList<Coordinates>> entry : gB.getOrder(true).entrySet()) {
            g.setColor(Convert.intToColor(entry.getKey()));
            ListIterator<Coordinates> l = entry.getValue().listIterator();
            Coordinates xy = new Coordinates(-10, -10);
            if (l.hasNext()) {
                xy = l.next();
            }
            while (l.hasNext()) {
                Coordinates past = xy;
                Coordinates newLine = l.next();
                int x = newLine.x();
                int y = newLine.y();
                int xP = past.x();
                int yP = past.y();
                if (x == xP + 1 && y == yP) {
                    g.fillRect((x * 100) - 60, 40 + (y * 100), 120, 20);
                } else if (x == xP - 1 && y == yP) {
                    g.fillRect((x * 100) + 50, 40 + (y * 100), 110, 20);
                } else if (y == yP + 1 && x == xP) {
                    g.fillRect(40 + (x * 100), (y * 100) - 60, 20, 110);
                } else if (y == yP - 1 && x == xP) {
                    g.fillRect(40 + (x * 100), 50 + (y * 100), 20, 110);
                }
                xy = newLine;
            }
        }
    }

    /**
     * Returns the size of the game board.
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(gB.BOARD_WIDTH, gB.BOARD_HEIGHT);
    }
}
