package com.olrox.aot;

import com.olrox.aot.layout.JMenuBarFactory;
import com.olrox.aot.layout.model.WordModel;
import com.olrox.aot.lib.dict.Dictionary;
import com.olrox.aot.lib.dict.DictionaryImpl;
import com.olrox.aot.text.TextReader;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class MainFrame extends JFrame {

    private Dictionary dictionary = new DictionaryImpl();
    private TextReader textReader = new TextReader();

    private DefaultListModel<WordModel> listModel = new DefaultListModel<>();
    private JPanel rootPanel;
    private JList<WordModel> list1;
    private JTextField wordsUsageCounter;
    private JTextField wordsInDictionaryCounter;
    private JButton alphabetSortButton;
    private JButton frequencySortButton;

    public MainFrame() {
        super("MyPaint");
        setContentPane(rootPanel);

        JMenuBarFactory jMenuBarFactory = new JMenuBarFactory(this);
        setJMenuBar(jMenuBarFactory.getJMenuBar());
        setMinimumSize(new Dimension(500, 500));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);

        alphabetSortButton.addActionListener(x -> this.showDictionary());
        frequencySortButton.addActionListener(x -> this.showDictionarySortedByFrequency());

        list1.setModel(listModel);
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
        showDictionary();
    }

    public void showDictionary() {
        listModel.removeAllElements();
        listModel.addAll(
                dictionary
                        .getSortedByAlphabet()
                        .stream()
                        .map(WordModel::new)
                        .collect(Collectors.toList())
        );
        wordsUsageCounter.setText(String.valueOf(dictionary.getWordUsageCounter()));
        wordsInDictionaryCounter.setText(String.valueOf(dictionary.getWordsInDictionary()));
    }

    public void showDictionarySortedByFrequency() {
        listModel.removeAllElements();
        listModel.addAll(
                dictionary
                        .getSortedByFrequency()
                        .stream()
                        .map(WordModel::new)
                        .collect(Collectors.toList())
        );
        wordsUsageCounter.setText(String.valueOf(dictionary.getWordUsageCounter()));
        wordsInDictionaryCounter.setText(String.valueOf(dictionary.getWordsInDictionary()));
    }

    public Dictionary getDictionary() {
        return dictionary;
    }
}
