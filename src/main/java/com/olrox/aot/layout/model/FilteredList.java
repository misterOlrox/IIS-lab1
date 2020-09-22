package com.olrox.aot.layout.model;

import com.olrox.aot.lib.word.Word;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class FilteredList {
    private List<Word> allWords;
    private List<Word> filteredList;
    private String filter;

    FilteredList(List<Word> allWords) {
        this.allWords = allWords;
        this.filteredList = new ArrayList<>(allWords);
        this.filter = "";
    }

    public int size() {
        return filteredList.size();
    }

    public Word get(int index) {
        return filteredList.get(index);
    }

    public void remove(int index) {
        allWords.remove(index);
    }

    public boolean isEmpty() {
        return allWords.isEmpty();
    }

    public void setFilter(String filter) {
        this.filter = filter;
        updateFilteredList();
    }

    public void updateWords(List<Word> allWords) {
        this.allWords = allWords;
        updateFilteredList();
    }

    private void updateFilteredList() {
        filteredList = allWords
                .stream()
                .filter(w -> w.getValue().startsWith(filter))
                .collect(Collectors.toList());
    }
}
