package com.olrox.aot.lib.text

import java.nio.file.Files

class TextSaver {
    void save(Text text) {
        Files.writeString(text.pathToText, text.getAsString())
    }
}
