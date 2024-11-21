import java.util.*;
import java.util.stream.Collectors;

public class WordFrequencyGame {

    public static final String SPACE = "\\s+";
    public static final String LINE_BREAK = "\n";
    public static final String CALCULATE_ERROR = "Calculate Error";

    public String getWordFrequency(String sentence) {
        try {
            List<WordFrequency> frequencies = getInitialWordFrequencies(sentence);
            frequencies = getWordFrequencies(frequencies);
            return joinResult(frequencies);
        } catch (Exception e) {
            return CALCULATE_ERROR;
        }
    }

    private static String joinResult(List<WordFrequency> frequencies) {
        return frequencies.stream()
                .sorted((word, nextWord) -> Integer.compare(nextWord.getWordCount(), word.getWordCount()))
                .map(wordFrequency -> wordFrequency.getWord() + " " + wordFrequency.getWordCount())
                .collect(Collectors.joining(LINE_BREAK));
    }

    private List<WordFrequency> getWordFrequencies(List<WordFrequency> frequencies) {
        Map<String, List<WordFrequency>> wordToWordFrequenciesMap = getListMap(frequencies);
        return wordToWordFrequenciesMap.entrySet().stream()
                .map(entry -> new WordFrequency(entry.getKey(), entry.getValue().size()))
                .collect(Collectors.toList());
    }

    private static List<WordFrequency> getInitialWordFrequencies(String sentence) {
        String[] words = sentence.split(SPACE);
        return Arrays.stream(words)
                .map(word -> new WordFrequency(word, 1))
                .collect(Collectors.toList());
    }

    private Map<String, List<WordFrequency>> getListMap(List<WordFrequency> wordFrequencies) {
        return wordFrequencies.stream().collect(Collectors.groupingBy(WordFrequency::getWord));
    }
}
