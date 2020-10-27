package com.olrox.aot.layout;

import com.olrox.aot.lib.dict.Dictionary;
import com.olrox.aot.lib.text.Text;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Comparator;

public class TextChooserDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JList<String> textsList;

    private Dictionary dictionary;
    private String result;

    public TextChooserDialog(Dictionary dictionary) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        this.dictionary = dictionary;
        textsList.setListData(dictionary
                .getTexts()
                .stream()
                .map(Text::getPathToText)
                .sorted(Comparator.naturalOrder()).toArray(String[]::new)
        );

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
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
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        setPreferredSize(new Dimension(600, 300));
        pack();
    }

    public String getResult() {
        return result;
    }

    private void onOK() {
        result = textsList.getSelectedValue();
        if (result == null || result.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Choose text!", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {
            dispose();
        }
    }

    private void onCancel() {
        result = null;
        dispose();
    }
}
