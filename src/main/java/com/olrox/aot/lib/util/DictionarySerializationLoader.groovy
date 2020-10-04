package com.olrox.aot.lib.util

import com.olrox.aot.lib.dict.Dictionary

class DictionarySerializationLoader {
    Dictionary load(String path) {
        FileInputStream fileInputStream = new FileInputStream(path);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        (Dictionary) objectInputStream.readObject();
    }
}
