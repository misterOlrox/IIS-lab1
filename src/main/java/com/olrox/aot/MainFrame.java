package com.olrox.aot;

import com.olrox.aot.lib.logic.StartAction;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.WindowConstants;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class MainFrame extends JFrame {

    private JPanel rootPanel;
    private JPanel buttonPanel;
    private JLabel attributeLabel;
    private JTextPane textPane1;
    private JScrollPane jscrollPane;
    private JButton startButton;

    public MainFrame() throws IOException {
        super("");
        setContentPane(rootPanel);

        startButton = new JButton("Start");
        buttonPanel.add(startButton);
        attributeLabel.setText("Начните, нажав 'Start'");

        startButton.addActionListener(new StartAction(textPane1, jscrollPane));

        setMinimumSize(new Dimension(500, 500));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }

    public static void main(String[] args) throws IOException {
        MainFrame mainFrame = new MainFrame();
        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }
}
