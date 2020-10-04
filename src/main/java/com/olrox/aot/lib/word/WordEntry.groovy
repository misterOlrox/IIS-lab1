package com.olrox.aot.lib.word

import com.olrox.aot.lib.text.Text;

class WordEntry implements Serializable {
    final Word word
    final Text text
    long startPos

    WordEntry(Word word, Text text) {
        this.word = word
        this.text = text
        this.startPos = startPos
    }

    @Override
    String toString() {
        text.pathToText + " : " + startPos
    }
}
