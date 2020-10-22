package com.olrox.aot.lib.dict;

import com.olrox.aot.lib.tagging.Tagger;
import com.olrox.aot.lib.text.Text;
import com.olrox.aot.lib.word.EnglishWord;
import com.olrox.aot.lib.word.Word;
import com.olrox.aot.lib.word.WordEntry;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class DictionaryImpl implements Dictionary {

    private long wordUsageCounter = 0;
    private Map<String, Word> entireMap = new HashMap<>();

    @Override
    public Word addWord(String word) {
        wordUsageCounter++;
        Word existingWord = entireMap.get(word);
        if (existingWord == null) {
            Word newWord = new EnglishWord(word);
            entireMap.put(word, newWord);
            return newWord;
        } else {
            System.err.println("Word already in dictionary");
            return existingWord;
        }
    }

    @Override
    public Word addWord(String word, Text text) {
        wordUsageCounter++;
        Word existingWord = entireMap.get(word);
        if (existingWord == null) {
            Word newWord = new EnglishWord(word);
            entireMap.put(word, newWord);
            newWord.addEntry(new WordEntry(newWord, text));
            return newWord;
        } else {
            existingWord.addEntry(new WordEntry(existingWord, text));
            return existingWord;
        }
    }

    @Override
    public Word editWord(String oldValue, String newValue) {
        Word oldWord = entireMap.get(oldValue);
        Word newWord = entireMap.get(newValue);
        if (oldValue.equals(newValue)) {
            return oldWord;
        }
        if (newWord == null) {
            entireMap.remove(oldValue);
            oldWord.setValue(newValue);
            entireMap.put(newValue, oldWord);
        } else {
            entireMap.remove(oldValue);
            newWord.addEntries(oldWord.getWordEntries());
        }

        return newWord;
    }

    @Override
    public boolean contains(String word) {
        return entireMap.containsKey(word);
    }

    @Override
    public Word getWord(String word) {
        return entireMap.get(word.toLowerCase());
    }

    @Override
    public void addWords(Text text) {
        text.getWords().forEach(w -> this.addWord(w, text));
    }

    @Override
    public List<Word> getSortedByFrequency() {
        return entireMap
                .values()
                .stream()
                .sorted(Comparator.comparingLong(Word::getFrequency).reversed())
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

    @Override
    public void deleteWord(String word) {
        entireMap.remove(word);
    }

    @Override
    public void onTextChanged(Text changedText) {
        Set<String> toRemove = new HashSet<>();
        entireMap.forEach((k, v) -> {
            v.onTextRemoved(changedText);
            if (v.getFrequency() == 0) {
                toRemove.add(k);
            }
        });
        toRemove.forEach(k -> entireMap.remove(k));
        changedText.read();
        addWords(changedText);
    }

    @Override
    public void clear() {
        entireMap.clear();
        wordUsageCounter = 0;
    }
}
