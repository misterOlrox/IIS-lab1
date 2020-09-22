package com.olrox.aot;

import com.olrox.aot.layout.factory.JMenuBarFactory;
import com.olrox.aot.layout.model.WordTableModel;
import com.olrox.aot.lib.dict.Dictionary;
import com.olrox.aot.lib.dict.DictionaryImpl;
import com.olrox.aot.lib.word.Word;
import com.olrox.aot.text.TextReader;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class MainFrame extends JFrame {

    private Dictionary dictionary = new DictionaryImpl();
    private List<Word> showedWords = new ArrayList<>();
    private TextReader textReader = new TextReader();

    private JPanel rootPanel;
    private JTable wordTable;
    private JTextField findWordField;
    private WordTableModel wordTableModel = new WordTableModel(showedWords, dictionary);

    public MainFrame() {
        super("MyPaint");
        setContentPane(rootPanel);

        JMenuBarFactory jMenuBarFactory = new JMenuBarFactory(this);
        setJMenuBar(jMenuBarFactory.getJMenuBar());
        setMinimumSize(new Dimension(500, 500));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);

        findWordField.addActionListener((actionEvent) -> {
            wordTableModel.setFilter(findWordField.getText());
        });

        wordTable.setModel(wordTableModel);
        wordTable.setAutoCreateRowSorter(true);
        wordTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                var dialog = new EditWordDialog(
                        wordTableModel,
                        wordTable.convertRowIndexToModel(wordTable.getSelectedRow()),
                        wordTable.convertColumnIndexToModel(wordTable.getSelectedColumn())
                );
                dialog.setVisible(true);
                super.mouseClicked(e);
            }
        });
        readText(Paths.get("./src/main/resources/text1.txt").toFile());
        readText(Paths.get("./src/main/resources/text2.txt").toFile());
        readText(Paths.get("./src/main/resources/text3.txt").toFile());
        readText(Paths.get("./src/main/resources/text4.txt").toFile());
        readText(Paths.get("./src/main/resources/text5.txt").toFile());
    }

    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame();
        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    public void readText(File file) {
        dictionary.addWords(textReader.readFile(file));
        wordTableModel.fireTableDataChanged();
    }

}
