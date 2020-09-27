package com.olrox.aot.lib.text

import com.olrox.aot.lib.alphabet.EnglishAlphabet

import java.nio.file.Files
import java.nio.file.Path
import java.util.stream.Collectors

class Text {

    public final static String WORD_DELIMITER = "[\\s/.,‚!`?:;‘’“”*_+—\\d\"'\\)\\(\\n]+";
    private final static TextSaver textSaver = new TextSaver()

    final Path pathToText
    private String stringRepresentation
    private List<String> words

    Text(Path path) {
        this.pathToText = path;
    }

    void read() {
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

    void save(String editedText) {
        stringRepresentation = editedText
        textSaver.save(this)
    }

    List<String> getWords() {
        return Collections.unmodifiableList(words)
    }

    String getAsString() {
        if (stringRepresentation == null) {
            stringRepresentation = Files.readString(pathToText).replaceAll("\\r", "")
        }
        stringRepresentation
    }

    String getPathAsString() {
        pathToText.toString()
    }
}
