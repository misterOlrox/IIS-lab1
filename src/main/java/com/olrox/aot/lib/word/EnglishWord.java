package com.olrox.aot.lib.word;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EnglishWord implements Word {
    private String value;
    private List<WordEntry> wordEntries = new ArrayList<>();

    public EnglishWord(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public long getFrequency() {
        return wordEntries.size();
    }

    @Override
    public void addEntry(WordEntry wordEntry) {
        wordEntries.add(wordEntry);
    }

    @Override
    public void addEntries(List<WordEntry> wordEntries) {
        this.wordEntries.addAll(wordEntries);
    }

    @Override
    public List<WordEntry> getWordEntries() {
        return Collections.unmodifiableList(wordEntries);
    }
}
