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
                .map(wordFrequency -> wordFrequency.getWord()+" "+wordFrequency.getWordCount())
                .collect(Collectors.joining(LINE_BREAK));
    }

    private List<WordFrequency> getWordFrequencies(List<WordFrequency> frequencies) {
        Map<String, List<WordFrequency>> wordToWordFrequenciesMap = getListMap(frequencies);
        frequencies = wordToWordFrequenciesMap.entrySet().stream()
                .map(entry -> new WordFrequency(entry.getKey(), entry.getValue().size()))
                .collect(Collectors.toList());
        return frequencies;
    }

    private static List<WordFrequency> getInitialWordFrequencies(String sentence) {
        String[] words = sentence.split(SPACE);

        List<WordFrequency> frequencies = new ArrayList<>();
        frequencies = Arrays.stream(words)
                .map(word -> new WordFrequency(word, 1))
                .collect(Collectors.toList());
        return frequencies;
    }

    private Map<String, List<WordFrequency>> getListMap(List<WordFrequency> inputList) {
        Map<String, List<WordFrequency>> map = new HashMap<>();
        for (WordFrequency input : inputList) {
            if (!map.containsKey(input.getWord())) {
                ArrayList arr = new ArrayList<>();
                arr.add(input);
                map.put(input.getWord(), arr);
            } else {
                map.get(input.getWord()).add(input);
            }
        }
        return map;
    }
}
