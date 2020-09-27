package com.olrox.aot.lib.dict;

import com.olrox.aot.lib.text.Text;
import com.olrox.aot.lib.word.Word;

import java.util.List;

public interface Dictionary {
    Word addWord(String word, Text text);

    Word editWord(String oldValue, String newValue);

    void addWords(Text text);

    List<Word> getSortedByFrequency();

    long getWordUsageCounter();

    long getWordsInDictionary();

    void deleteWord(String word);

    void onTextChanged(Text changedText);
}
