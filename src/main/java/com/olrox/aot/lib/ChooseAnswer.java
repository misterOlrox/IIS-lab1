package com.olrox.aot.lib;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class ChooseAnswer extends JDialog {
    private JPanel contentPane;
    private JLabel attributeLabel;
    private JPanel buttonPanel;

    private String answer;

    private List<JButton> buttons = new ArrayList<>();

    public ChooseAnswer(String target, List<String> options) {
        setContentPane(contentPane);
        setModal(true);

        attributeLabel.setText(target);
        options.forEach(b -> {
            JButton button = new JButton(b);
            buttons.add(button);
            button.addActionListener(l -> {
                postAnswer(b);
            });
            buttonPanel.add(button);
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        pack();
        setLocationRelativeTo(null);
    }

    private void postAnswer(String answer) {
        this.answer = answer;
        dispose();
    }

    private void onCancel() {
        this.answer = null;
        dispose();
    }

    public String getAnswer() {
        return answer;
    }
}
