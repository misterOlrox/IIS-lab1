package com.olrox.aot.layout;

import com.olrox.aot.lib.alphabet.EnglishAlphabet;
import com.olrox.aot.lib.text.Text;
import com.olrox.aot.lib.word.Word;
import com.olrox.aot.lib.word.WordEntry;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class EditTextDialog extends JDialog {
    private Word word;
    private JPanel contentPane;
    private JButton saveButton;
    private JButton closeButton;
    private JList<WordEntry> wordEntryList;
    private JTextPane textPane;
    private JButton cancelButton;
    private DefaultListModel<WordEntry> wordEntryListModel = new DefaultListModel<>();
    private WordEntry selectedWordEntry;
    private int beforeStartPos;
    private int beforeEndPos;
    private Set<Text> savedTexts = new HashSet<>();

    public EditTextDialog(Word word) {
        this.word = word;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(saveButton);
        wordEntryList.setModel(wordEntryListModel);

        updateEntriesPos();
        wordEntryListModel.addAll(word.getWordEntries());
        wordEntryList.setSelectedIndex(0);
        onWordEntrySelected();
        wordEntryList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                onWordEntrySelected();
            }
        });
        saveButton.addActionListener(e -> save());
        cancelButton.addActionListener(e -> onCancel());
        closeButton.addActionListener(e -> dispose());

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        setPreferredSize(new Dimension(1200, 600));
        pack();
    }

    private void updateEntriesPos() {
        String wordToFind = word.getValue();
        List<Text> textEntries = word.getWordEntries().stream().map(WordEntry::getText).distinct().collect(Collectors.toList());
        var entryIterator = word.getWordEntries().iterator();
        AtomicInteger count = new AtomicInteger();
        textEntries.forEach(text -> {
            String textInLC = text.getAsString().toLowerCase();
            var listInd = Pattern.compile(wordToFind + Text.WORD_DELIMITER).matcher(textInLC)
                    .results()
                    .map(MatchResult::start)
                    .filter(startPos -> {
                        if (startPos > 0) {
                            char c = textInLC.charAt(startPos - 1);
                            if (startPos > 1 && c == '-') {
                                return !Character.isLetter(textInLC.charAt(startPos - 2));
                            } else {
                                return !EnglishAlphabet.isEnglish(c) && !Character.isLetter(c);
                            }
                        } else {
                            return true;
                        }
                    })
                    .collect(Collectors.toList());
            for (Integer integer : listInd) {
                if (!entryIterator.hasNext()) {
                    System.err.println(String.format("Skip entry. %s, %s, %s", count.get(), integer, textInLC.substring(integer - 10, integer+30)));
                    continue;
                }
                //if (textInLC.charAt(integer - 1) != ' ' && textInLC.charAt(integer - 1) != '\n')
                //System.out.println(String.format("Entry. %s, %s, %s", count.get(), integer, textInLC.charAt(integer-1)));
                count.incrementAndGet();
                entryIterator.next().setStartPos(integer);
            }
        });
        if (word.getWordEntries().size() != count.get()) {
            System.err.println("Wrong entry count! - " + count.get());
        }
    }

    private void save() {
        String editedSegment;
        try {
            editedSegment = textPane.getDocument().getText(0, textPane.getDocument().getLength());
        } catch (BadLocationException e) {
            e.printStackTrace();
            return;
        }
        if (editedSegment.startsWith("...")) {
            editedSegment = editedSegment.substring(3);
        }
        if (editedSegment.endsWith("...")) {
            editedSegment = editedSegment.substring(0, editedSegment.length() - 3);
        }

        String beforeText = selectedWordEntry.getText().getAsString();
        String editedTextString = beforeText.substring(0, beforeStartPos) + editedSegment + beforeText.substring(beforeEndPos);
        selectedWordEntry.getText().save(editedTextString);
        savedTexts.add(selectedWordEntry.getText());
        wordEntryListModel.removeAllElements();
        word.getWordEntries().remove(selectedWordEntry);
        updateEntriesPos();
        wordEntryListModel.addAll(word.getWordEntries());
        onWordEntrySelected();
    }

    private void onCancel() {
        onWordEntrySelected();
    }

    private void onWordEntrySelected() {
        var wordEntry = wordEntryList.getSelectedValue();
        selectedWordEntry = wordEntry;
        if (wordEntry == null) {
            textPane.setText("");
            return;
        }
        Text textToShow = wordEntry.getText();
        int entryPos = Math.toIntExact(wordEntry.getStartPos());
        int len = 1000;
        int startPos = entryPos - len;
        String start = "...";
        String end = "...";
        if (startPos < 0) {
            startPos = 0;
            start = "";
        }
        int wordLen = wordEntry.getWord().getValue().length();
        int endPos = entryPos + wordLen + len;
        if (endPos > textToShow.getAsString().length()) {
            endPos = textToShow.getAsString().length();
            end = "";
        }
        textPane.setText(start + textToShow.getAsString().substring(startPos, endPos) + end);
        StyledDocument doc1 = textPane.getStyledDocument();
        doc1.setCharacterAttributes(0, doc1.getLength(), SimpleAttributeSet.EMPTY, true);

        SimpleAttributeSet color = new SimpleAttributeSet();
        StyleConstants.setBackground(color, Color.yellow);
        StyledDocument doc = textPane.getStyledDocument();
        int startHighlight = start.length() + entryPos - startPos;
        doc.setCharacterAttributes(startHighlight, wordLen, color, true);
        beforeStartPos = startPos;
        beforeEndPos = endPos;
    }

    public Set<Text> getSavedTexts() {
        return savedTexts;
    }
}
