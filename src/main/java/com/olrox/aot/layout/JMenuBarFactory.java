package com.olrox.aot.layout;

import com.olrox.aot.MainFrame;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.io.File;

public class JMenuBarFactory {

    private MainFrame parentFrame;

    public JMenuBarFactory(MainFrame parentFrame) {
        this.parentFrame = parentFrame;
    }

    public JMenuBar getJMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        JMenuItem openItem = new JMenuItem("Open");
        fileMenu.add(openItem);

        openItem.addActionListener(actionEvent -> {
            JFileChooser fileopen = new JFileChooser("./src/main/resources");
            int ret = fileopen.showDialog(parentFrame, "Open");
            if (ret == JFileChooser.APPROVE_OPTION) {
                File file = fileopen.getSelectedFile();
                parentFrame.readText(file);
            }
        });

        return menuBar;
    }
}
