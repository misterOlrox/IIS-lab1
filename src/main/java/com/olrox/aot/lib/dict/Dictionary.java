package com.olrox.aot.lib.dict;

import com.olrox.aot.lib.word.Word;

import java.util.List;

public interface Dictionary {
    void addWord(String word);

    Word editWord(String oldValue, String newValue);

    void addWords(List<String> words);

    List<Word> getSortedByFrequency();

    long getWordUsageCounter();

    long getWordsInDictionary();

    void deleteWord(String word);
}
