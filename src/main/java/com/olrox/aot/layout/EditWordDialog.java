package com.olrox.aot.layout;

import com.olrox.aot.layout.model.WordTableModel;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class EditWordDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textField1;
    private JButton deleteButton;
    private JButton showEntriesButton;

    public EditWordDialog(WordTableModel wordTableModel, int rowInd, int colInd) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        textField1.setText((String) wordTableModel.getValueAt(rowInd, 0));

        buttonOK.addActionListener(e -> {
            wordTableModel.setValueAt(textField1.getText(), rowInd, colInd);
            dispose();
        });

        deleteButton.addActionListener((e) -> {
            wordTableModel.setValueAt(textField1.getText(), rowInd, colInd);
            dispose();
        });

        showEntriesButton.addActionListener(e -> {
            var editTextDialog = new EditTextDialog(wordTableModel.getWord(rowInd));
            editTextDialog.setVisible(true);
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

        setPreferredSize(new Dimension(600, 400));
        pack();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
