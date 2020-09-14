package com.olrox.aot.lib.word;

public class EnglishWord implements Word {
    private String value;
    private long frequency = 1L;

    public EnglishWord(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public long getFrequency() {
        return frequency;
    }

    @Override
    public void incrementFrequency() {
        ++frequency;
    }
}
