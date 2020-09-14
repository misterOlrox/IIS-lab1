package com.olrox.aot.layout.model;

import com.olrox.aot.lib.word.Word;

public class WordModel {

    private String representation;

    public WordModel(Word word) {
        this.representation = word.getValue() + " : " + word.getFrequency();
    }

    @Override
    public String toString() {
        return representation;
    }
}
