package com.olrox.aot.lib.word;

public interface Word {
    String getValue();

    void incrementFrequency();
    long getFrequency();
}
