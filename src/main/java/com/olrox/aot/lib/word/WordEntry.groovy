package com.olrox.aot.lib.word

import com.olrox.aot.lib.text.Text;

class WordEntry {
    final Word word
    final Text text
    final long startPos

    WordEntry(Word word, Text text, long startPos) {
        this.word = word
        this.text = text
        this.startPos = startPos
    }

    @Override
    String toString() {
        text.pathToText.toString() + " : " + startPos
    }
}
