package org.cis1200temp.FlowFree;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuButtonListener implements ActionListener {
    private JTextField level;
    private boolean load;

    public MenuButtonListener(JTextField field, boolean bool) {
        level = field;
        load = bool;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (load) {
            BoardsController.firstDrawGameBoard(
                    "src/main/java/org/cis1200/FlowFree/SaveFiles/" + level.getText()
            );
        } else {
            BoardsController.firstDrawGameBoard(level.getText());
        }

    }
}
