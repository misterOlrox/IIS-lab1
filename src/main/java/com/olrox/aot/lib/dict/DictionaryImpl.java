package com.olrox.aot.lib.dict;

import com.olrox.aot.lib.word.EnglishWord;
import com.olrox.aot.lib.word.Word;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class DictionaryImpl implements Dictionary {

    private long wordUsageCounter = 0;
    private Map<String, Word> entireMap = new HashMap<>();
    private NavigableSet<Word> alphabeticallySorted = new TreeSet<>(Comparator.comparing(Word::getValue));

    @Override
    public void addWord(String word) {
        wordUsageCounter++;
        Word existingWord = entireMap.get(word);
        if (existingWord == null) {
            Word newWord = new EnglishWord(word);
            entireMap.put(word, newWord);
            alphabeticallySorted.add(newWord);
        } else {
            existingWord.incrementFrequency();
        }
    }

    @Override
    public void addWords(List<String> words) {
        words.forEach(this::addWord);
    }

    @Override
    public List<Word> getSortedByAlphabet() {
        return new ArrayList<>(alphabeticallySorted);
    }

    @Override
    public List<Word> getSortedByFrequency() {
        return entireMap
                .values()
                .stream()
                .sorted((w1, w2) -> (int) (w2.getFrequency() - w1.getFrequency()))
                .collect(Collectors.toList());
    }

    @Override
    public long getWordUsageCounter() {
        return wordUsageCounter;
    }

    @Override
    public long getWordsInDictionary() {
        return entireMap.size();
    }
}
