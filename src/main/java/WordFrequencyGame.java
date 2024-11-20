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
        StringJoiner joiner = new StringJoiner(LINE_BREAK);
        for (WordFrequency w : frequencies) {
            String s = w.getWord() + " " + w.getWordCount();
            joiner.add(s);
        }
        return joiner.toString();
    }

    private List<WordFrequency> getWordFrequencies(List<WordFrequency> frequencies) {
        //get the map for the next step of sizing the same word
        Map<String, List<WordFrequency>> wordToWordFrequenciesMap = getListMap(frequencies);

        List<WordFrequency> list = new ArrayList<>();
        for (Map.Entry<String, List<WordFrequency>> entry : wordToWordFrequenciesMap.entrySet()) {
            WordFrequency input = new WordFrequency(entry.getKey(), entry.getValue().size());
            list.add(input);
        }

        List<WordFrequency> tempList = wordToWordFrequenciesMap.entrySet().stream()
                .map(entry -> new WordFrequency(entry.getKey(), entry.getValue().size()))
                .collect(Collectors.toList());

        frequencies = wordToWordFrequenciesMap.entrySet().stream()
                .map(entry -> new WordFrequency(entry.getKey(), entry.getValue().size()))
                .collect(Collectors.toList());
        ;

        frequencies.sort((w1, w2) -> w2.getWordCount() - w1.getWordCount());
        return frequencies;
    }

    private static List<WordFrequency> getInitialWordFrequencies(String sentence) {
        //split the input string with 1 to n pieces of spaces
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
//       map.computeIfAbsent(input.getValue(), k -> new ArrayList<>()).add(input);
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
