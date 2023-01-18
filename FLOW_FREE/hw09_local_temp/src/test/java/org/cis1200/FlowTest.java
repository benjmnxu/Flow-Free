package org.cis1200;

import org.cis1200temp.FlowFree.Coordinates;
import org.cis1200temp.FlowFree.FlowBoard;
import org.cis1200temp.FlowFree.FlowBoardController;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

public class FlowTest {

    String empty = "0" +
            "0";

    String regular = "src/test/java/org/cis1200/Test/1";

    @Test
    public void testAdjSamePoint() {
        FlowBoardController gL = new FlowBoardController();
        LinkedList<Coordinates> coordinates = new LinkedList<Coordinates>();
        Coordinates c = new Coordinates(1, 1);
        coordinates.add(c);
        assertFalse(gL.adjacent(coordinates, c));
    }

    @Test
    public void testAdjEmptyList() {
        FlowBoardController gL = new FlowBoardController();
        LinkedList<Coordinates> coordinates = new LinkedList<Coordinates>();
        Coordinates c = new Coordinates(1, 1);
        assertFalse(gL.adjacent(coordinates, c));
    }

    @Test
    public void testAdjNormal() {
        FlowBoardController gL = new FlowBoardController();
        LinkedList<Coordinates> coordinates = new LinkedList<Coordinates>();
        Coordinates c1 = new Coordinates(1, 1);
        Coordinates c2 = new Coordinates(2, 2);
        Coordinates c3 = new Coordinates(2, 1);
        coordinates.add(c1);
        coordinates.add(c2);
        coordinates.add(c3);
        assertFalse(gL.adjacent(coordinates, new Coordinates(1, 2)));
    }

    @Test
    public void testAllAdjSingle() {
        FlowBoardController gL = new FlowBoardController();
        LinkedList<Coordinates> coordinates = new LinkedList<Coordinates>();
        Coordinates c = new Coordinates(1, 1);
        coordinates.add(c);
        assertTrue(gL.allAdjacent(coordinates, 1));
    }

    @Test
    public void testAllAdjNormal() {
        FlowBoardController gL = new FlowBoardController();
        LinkedList<Coordinates> coordinates = new LinkedList<Coordinates>();
        Coordinates c1 = new Coordinates(1, 1);
        Coordinates c2 = new Coordinates(1, 2);
        Coordinates c3 = new Coordinates(1, 3);
        Coordinates c4 = new Coordinates(1, 4);
        Coordinates c5 = new Coordinates(1, 5);
        Coordinates c6 = new Coordinates(1, 6);
        coordinates.add(c1);
        coordinates.add(c2);
        coordinates.add(c3);
        coordinates.add(c4);
        coordinates.add(c5);
        coordinates.add(c6);
        assertTrue(gL.allAdjacent(coordinates, 1));
    }

    @Test
    public void testAllAdjNormalFalse() {
        FlowBoardController gL = new FlowBoardController();
        LinkedList<Coordinates> coordinates = new LinkedList<Coordinates>();
        Coordinates c1 = new Coordinates(1, 1);
        Coordinates c2 = new Coordinates(1, 2);
        Coordinates c3 = new Coordinates(1, 3);
        Coordinates c5 = new Coordinates(1, 5);
        Coordinates c6 = new Coordinates(1, 6);
        coordinates.add(c1);
        coordinates.add(c2);
        coordinates.add(c3);
        coordinates.add(c5);
        coordinates.add(c6);
        assertFalse(gL.allAdjacent(coordinates, 1));
    }

    @Test
    public void testGrid() {
        FlowBoardController gL = new FlowBoardController();
        LinkedList<Coordinates> coordinates = new LinkedList<Coordinates>();
        Coordinates c1 = new Coordinates(1, 1);
        Coordinates c2 = new Coordinates(1, 2);
        Coordinates c3 = new Coordinates(1, 3);
        Coordinates c5 = new Coordinates(1, 5);
        Coordinates c6 = new Coordinates(1, 6);
        coordinates.add(c1);
        coordinates.add(c2);
        coordinates.add(c3);
        coordinates.add(c5);
        coordinates.add(c6);
        assertFalse(gL.allAdjacent(coordinates, 1));
    }

    @Test
    public void testFlowBoardControllerEmpty() {
        FlowBoardController fbc = new FlowBoardController();
        fbc.set(empty);
        fbc.updateStatus();
        assertFalse(fbc.VALID_LEVEL);
    }

    @Test
    public void testFlowBoardController() {
        FlowBoardController fbc = new FlowBoardController();
        fbc.set(regular);
        fbc.updateStatus();
        assertFalse(fbc.FREEZE);
        fbc.getGrid(false)[0][0] = 2;
        fbc.getGrid(false)[1][0] = 2;
        fbc.getGrid(false)[2][0] = 2;
        fbc.getGrid(false)[0][1] = 3;
        fbc.getGrid(false)[1][1] = 3;
        fbc.getGrid(false)[2][1] = 3;
        fbc.getGrid(false)[0][2] = 4;
        fbc.getGrid(false)[1][2] = 4;
        fbc.getGrid(false)[2][2] = 4;
        fbc.updateStatus();
        assertTrue(fbc.FREEZE);
    }

    @Test
    public void testFlowBoardControllerIntersection() {
        FlowBoardController fbc = new FlowBoardController();
        fbc.set(regular);
        fbc.updateStatus();
        assertFalse(fbc.FREEZE);
        fbc.set(regular);
        LinkedList<Coordinates> red = new LinkedList<Coordinates>();
        red.add(new Coordinates(0, 0));
        red.add(new Coordinates(1, 0));
        red.add(new Coordinates(2, 0));
        fbc.getOrder(false).put(2, red);
        fbc.getGrid(false)[0][0] = 2;
        fbc.getGrid(false)[0][1] = 2;
        fbc.getGrid(false)[0][2] = 2;
        fbc.removeIntersection(0, 1);
        fbc.updateStatus();
        assertFalse(fbc.FREEZE);
        assertEquals(fbc.getGrid(true)[0][0], 0);
        assertEquals(fbc.getGrid(true)[0][2], 0);
    }

    @Test
    public void testFBCAdjacentBad() {
        FlowBoardController fbc = new FlowBoardController();
        fbc.set(regular);
        fbc.updateStatus();
        assertFalse(fbc.FREEZE);
        fbc.set(regular);
        LinkedList<Coordinates> red = new LinkedList<Coordinates>();
        red.add(new Coordinates(0, 0));
        red.add(new Coordinates(1, 0));
        red.add(new Coordinates(2, 1));
        red.add(new Coordinates(1, 1));
        fbc.getGrid(false)[0][0] = 2;
        fbc.getGrid(false)[0][1] = 2;
        fbc.getGrid(false)[1][2] = 2;
        fbc.getGrid(false)[1][1] = 2;
        assertFalse(fbc.allAdjacent(red, 2));
        assertEquals(
                fbc.getGrid(false)[0][0], 0
        );

        assertEquals(
                fbc.getGrid(false)[0][1], 0
        );
        assertEquals(
                fbc.getGrid(false)[1][2], 0
        );
        assertEquals(
                fbc.getGrid(false)[1][1], 0
        );
    }

    @Test
    public void testFBCAdjacentGood() {
        FlowBoardController fbc = new FlowBoardController();
        fbc.set(regular);
        fbc.updateStatus();
        assertFalse(fbc.FREEZE);
        fbc.set(regular);
        LinkedList<Coordinates> red = new LinkedList<Coordinates>();
        red.add(new Coordinates(0, 0));
        red.add(new Coordinates(1, 0));
        red.add(new Coordinates(1, 1));
        red.add(new Coordinates(2, 1));
        fbc.getGrid(false)[0][0] = 2;
        fbc.getGrid(false)[0][1] = 2;
        fbc.getGrid(false)[1][2] = 2;
        fbc.getGrid(false)[1][1] = 2;
        assertTrue(fbc.allAdjacent(red, 2));
        assertFalse(
                fbc.getGrid(false)[0][0] == 0
        );
        assertFalse(
                fbc.getGrid(false)[0][1] == 0
        );
        assertFalse(
                fbc.getGrid(false)[1][2] == 0
        );
        assertFalse(
                fbc.getGrid(false)[1][1] == 0
        );
    }

    @Test
    public void testEncapsulation() {
        FlowBoardController fbc = new FlowBoardController();
        fbc.set(regular);

        fbc.getGrid(true)[0][0] = 10;
        assertEquals(fbc.getGrid(true)[0][0], 1);
        fbc.getGrid(false)[0][0] = 10;
        assertEquals(fbc.getGrid(true)[0][0], 10);

        fbc.getSpots(true).put(new Coordinates(0, 0), "blah");
        assertFalse(fbc.getSpots(true).containsValue("blah"));
        fbc.getSpots(false).put(new Coordinates(0, 0), "blah");
        assertEquals(fbc.getSpots(true).get(new Coordinates(0, 0)), "blah");

        fbc.getColorDraw(true).add(Color.BLACK);
        assertFalse(fbc.getColorDraw(true).contains(Color.BLACK));
        fbc.getColorDraw(false).add(Color.BLACK);
        assertTrue(fbc.getColorDraw(true).contains(Color.BLACK));

        LinkedList<Coordinates> temp = new LinkedList<Coordinates>();
        temp.add(new Coordinates(5, 5));

        fbc.getOrder(true).put(0, temp);
        assertNull(fbc.getOrder(true).get(0));
        fbc.getOrder(false).put(0, temp);
        assertEquals(fbc.getOrder(true).get(0), temp);
    }

    @Test
    public void testUndo() {
        FlowBoardController fbc = new FlowBoardController();
        fbc.set(regular);
        fbc.getGrid(false)[0][0] = 2;
        fbc.getColorDraw(false).add(Color.RED);
        fbc.undo(new FlowBoard(fbc));
        assertTrue(fbc.getGrid(true)[0][0] != 2);
    }
}
