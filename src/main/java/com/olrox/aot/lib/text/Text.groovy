package com.olrox.aot.lib.text

import com.olrox.aot.lib.alphabet.EnglishAlphabet

import java.nio.file.Files
import java.nio.file.Path
import java.util.stream.Collectors

class Text {

    private final static String WORD_DELIMITER = "[\\s/.,!`?:;‘’“”*_+—\\d\"'\\(\\)\\n]+";

    final Path pathToText
    private String stringRepresentation
    private List<String> words

    Text(Path path) {
        this.pathToText = path;
    }

    void read() {
        try {
            stringRepresentation = Files.readString(pathToText);
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }

        words = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(pathToText.toString()))) {
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
    }

    List<String> getWords() {
        return Collections.unmodifiableList(words)
    }

    String getStringRepresentation() {
        return stringRepresentation
    }
}
