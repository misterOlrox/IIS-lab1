package com.olrox.aot.lib.text

import java.nio.file.Files
import java.nio.file.Paths

class TextSaver {
    void save(Text text) {
        Files.writeString(Paths.get(text.pathToText), text.getAsString())
    }
}
