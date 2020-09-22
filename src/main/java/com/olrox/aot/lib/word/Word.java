package com.olrox.aot.lib.word;

public interface Word {
    String getValue();
    void setValue(String value);

    void incrementFrequency();
    void incrementFrequency(long i);
    long getFrequency();
}
