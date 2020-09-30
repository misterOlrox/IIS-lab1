package com.olrox.aot.layout.factory;

import com.olrox.aot.MainFrame;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import java.io.File;
import java.nio.file.Paths;

public class JMenuBarFactory {

    private MainFrame parentFrame;

    public JMenuBarFactory(MainFrame parentFrame) {
        this.parentFrame = parentFrame;
    }

    public JMenuBar getJMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);
        JMenu wordsMenu = new JMenu("Words");
        menuBar.add(wordsMenu);

        JMenuItem openItem = new JMenuItem("Open");
        fileMenu.add(openItem);

        JMenuItem addWordItem = new JMenuItem("Add word");
        wordsMenu.add(addWordItem);

        openItem.addActionListener(actionEvent -> {
            JFileChooser fileopen = new JFileChooser("./src/main/resources");
            int ret = fileopen.showDialog(parentFrame, "Open");
            if (ret == JFileChooser.APPROVE_OPTION) {
                File file = fileopen.getSelectedFile();
                parentFrame.readText(Paths.get(file.getPath()));
            }
        });

        addWordItem.addActionListener(l -> {
            String result = JOptionPane.showInputDialog(parentFrame, "New word: ");
            parentFrame.addWord(result);
        });

        return menuBar;
    }
}
