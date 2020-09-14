package com.olrox.aot.lib.dict;

import com.olrox.aot.lib.word.Word;

import java.util.List;

public interface Dictionary {
    void addWord(String word);
    void addWords(List<String> words);
    List<Word> getSortedByAlphabet();
    List<Word> getSortedByFrequency();

    long getWordUsageCounter();

    long getWordsInDictionary();
}
