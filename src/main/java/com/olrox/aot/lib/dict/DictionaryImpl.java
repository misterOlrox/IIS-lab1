package com.olrox.aot.lib.dict;

import com.olrox.aot.lib.text.Text;
import com.olrox.aot.lib.word.EnglishWord;
import com.olrox.aot.lib.word.Word;
import com.olrox.aot.lib.word.WordEntry;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DictionaryImpl implements Dictionary {

    private long wordUsageCounter = 0;
    private Map<String, Word> entireMap = new HashMap<>();

    @Override
    public Word addWord(String word, Text text) {
        wordUsageCounter++;
        Word existingWord = entireMap.get(word);
        if (existingWord == null) {
            Word newWord = new EnglishWord(word);
            entireMap.put(word, newWord);
            newWord.addEntry(new WordEntry(newWord, text, 0));
            return newWord;
        } else {
            existingWord.addEntry(new WordEntry(existingWord, text, 0));
            return existingWord;
        }
    }

    @Override
    public Word editWord(String oldValue, String newValue) {
        Word oldWord = entireMap.get(oldValue);
        Word newWord = entireMap.get(newValue);
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
}
