package com.olrox.aot.lib.util

import com.olrox.aot.lib.dict.Dictionary

class DictionarySerializationSaver {
    void save(Dictionary dictionary, String path) {
        try (FileOutputStream outputStream = new FileOutputStream(path);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)) {
            objectOutputStream.writeObject(dictionary);
            objectOutputStream.close();
        }
    }
}
