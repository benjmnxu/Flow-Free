package org.cis1200temp.FlowFree;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BoardsController extends JComponent {

    public static void start() {
        JFrame frame = new JFrame("FLOW");
        JPanel panel = new JPanel();
        Font f = new Font("Moderna", Font.PLAIN, 40);
        JLabel label = new JLabel("flow", SwingConstants.CENTER);
        label.setFont(f);
        JButton button = new JButton("START GAME");
        button.addActionListener(e -> {
            startMenu();
            frame.setVisible(false);
        });
        JButton instructions = new JButton("INSTRUCTIONS");
        instructions.addActionListener(e -> {
            instructionsDraw();
        });
        panel.add(button, BorderLayout.CENTER);
        panel.add(instructions, BorderLayout.SOUTH);
        frame.setSize(200, 200);
        frame.add(label, BorderLayout.CENTER);
        frame.add(panel, BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);
    }

    public static void startMenu() {
        JFrame frame = new JFrame("Flow Menu");
        JPanel panel = new JPanel();
        frame.setSize(new Dimension(250, 250));
        frame.getContentPane().add(panel);
        JTextField field = new JTextField("000");
        JTextField loadField = new JTextField("Insert Save File/Custom Level Here");
        JButton button = new JButton("PLAY LEVEL ");
        JButton lButton = new JButton("Load");
        lButton.addActionListener(new MenuButtonListener(loadField, true));
        button.addActionListener(new MenuButtonListener(field, false));
        panel.add(button, BorderLayout.NORTH);
        panel.add(field, BorderLayout.NORTH);
        panel.add(loadField, BorderLayout.SOUTH);
        panel.add(lButton, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    public static void firstDrawGameBoard(String s) {
        FlowBoardController gB = new FlowBoardController();
        FlowBoard fB = new FlowBoard(gB);
        gB.set(s);
        if (gB.VALID_LEVEL) {
            JFrame frame = new JFrame(gB.LEVEL);
            frame.setSize(new Dimension(gB.BOARD_WIDTH, gB.BOARD_HEIGHT));
            JButton undo = new JButton("UNDO");
            JButton save = new JButton("SAVE");
            undo.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    gB.undo(fB);
                }
            });
            save.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    gB.save();
                }
            });
            JPanel buttons = new JPanel();
            buttons.add(undo);
            buttons.add(save);
            buttons.setBackground(Color.gray);
            frame.add(fB, BorderLayout.CENTER);
            frame.add(buttons, BorderLayout.NORTH);
            frame.pack();
            frame.setVisible(true);
        }
    }

    public static void instructionsDraw() {
        JFrame frame = new JFrame("Instructions");
        frame.setSize(550, 250);
        JLabel label = new JLabel(
                "<html><div style='text-align: center;'> "
                        + "You need to connect all the dots on the grid, " +
                        "without leaving any spot empty. You can only connect dots of the same color to create"
                        +
                        " a flow between them. To load in a game " +
                        "use the format 'SAVE: [LEVEL NUMBER]' <html>",
                SwingConstants.CENTER
        );
        JLabel good = new JLabel("This is good");
        ImageIcon good2 = new ImageIcon("src/main/java/org/cis1200/FlowFree/Images/Good.png");
        Image good3 = good2.getImage().getScaledInstance(35, 140, Image.SCALE_SMOOTH);
        JLabel goodPicture = new JLabel(new ImageIcon(good3));
        JPanel panel = new JPanel();
        panel.add(good, BorderLayout.NORTH);
        panel.add(goodPicture, BorderLayout.CENTER);

        JLabel bad = new JLabel("This is bad");
        ImageIcon bad1 = new ImageIcon("src/main/java/org/cis1200/FlowFree/Images/Bad.png");
        Image bad2 = bad1.getImage().getScaledInstance(70, 140, Image.SCALE_SMOOTH);
        JLabel badPicture = new JLabel(new ImageIcon(bad2));
        JPanel panel2 = new JPanel();
        panel2.add(bad, BorderLayout.NORTH);
        panel2.add(badPicture, BorderLayout.CENTER);

        JLabel ins = new JLabel(
                "<html><div style='text-align: center;'> " + "When picking levels, 1-6 are free. " +
                        "<br/> Type that into the box next to the" +
                        "<br/> PLAY button. A premium subscription <br/> " +
                        "is needed for Levels 7-10. <html>",
                SwingConstants.CENTER
        );
        JButton premium = new JButton("Click Here for Premium");
        premium.addActionListener(e -> {
            buyPremium();
        });

        JPanel cont = new JPanel();
        cont.add(ins, BorderLayout.CENTER);
        cont.add(premium, BorderLayout.SOUTH);

        frame.add(label, BorderLayout.NORTH);
        frame.add(panel, BorderLayout.WEST);
        frame.add(panel2, BorderLayout.EAST);
        frame.add(cont, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    public static void buyPremium() {
        JFrame frame = new JFrame();

        JLabel label = new JLabel("Don't spend money on mobile games");
        ImageIcon hack1 = new ImageIcon("src/main/java/org/cis1200/FlowFree/Images/CreditCard.png");
        Image hack2 = hack1.getImage().getScaledInstance(100, 140, Image.SCALE_SMOOTH);
        JLabel hackPic = new JLabel(new ImageIcon(hack2));
        JPanel panel = new JPanel();
        panel.add(label, BorderLayout.NORTH);
        panel.add(hackPic, BorderLayout.CENTER);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }
}
