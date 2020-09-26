package com.olrox.aot.lib.word;

import java.util.List;

public interface Word {
    String getValue();
    void setValue(String value);
    void addEntry(WordEntry wordEntry);
    long getFrequency();
    void addEntries(List<WordEntry> wordEntries);

    List<WordEntry> getWordEntries();
}
