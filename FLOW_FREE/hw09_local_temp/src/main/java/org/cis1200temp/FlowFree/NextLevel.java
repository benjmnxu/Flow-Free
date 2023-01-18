package org.cis1200temp.FlowFree;

import javax.swing.*;
import java.awt.*;

public class NextLevel {

    public static void create(FlowBoardController gB) {
        JFrame jF = new JFrame();
        int nextL = 0;
        if (gB.LEVEL.contains("/")) {
            jF.add(new Label("YOU WIN!"));
        } else {
            nextL = Integer.parseInt(gB.LEVEL) + 1;
            JLabel label;
            JButton button;
            JTextField field = new JTextField(String.valueOf(nextL));
            if (nextL > 6) {
                label = new JLabel("No More Levels. Buy premium for more levels");
                button = new JButton("Click Here for Premium");
                button.addActionListener(e -> BoardsController.buyPremium());
            } else {
                label = new JLabel("YOU WIN!");
                button = new JButton("Next Level");
                button.addActionListener(new MenuButtonListener(field, false));
                jF.add(button, BorderLayout.SOUTH);
            }
            jF.add(label, BorderLayout.CENTER);
            jF.add(button, BorderLayout.SOUTH);
        }
        jF.pack();
        jF.setVisible(true);
    }

}
