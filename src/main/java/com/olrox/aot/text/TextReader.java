package com.olrox.aot.text;

import com.olrox.aot.lib.alphabet.EnglishAlphabet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TextReader {

    private final static String WORD_DELIMITER = "[\\s/.,!`?:;‘’“”*_+—\\d\"'\\(\\)\\n]+";

    public List<String> readFile(File file) {
        List<String> words = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                words.addAll(Arrays.stream(line.trim().toLowerCase().split(WORD_DELIMITER))
                        .filter(s -> !s.isBlank() && !s.startsWith("-") && EnglishAlphabet.isEnglish(s))
                        .collect(Collectors.toList())
                );
            }
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }

        return words;
    }
}
