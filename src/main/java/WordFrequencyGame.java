import java.util.*;

public class WordFrequencyGame {
    public String getWordFrequency(String sentence) {
        if (sentence.split("\\s+").length == 1) {
            return sentence + " 1";
        } else {
            try {
                //split the input string with 1 to n pieces of spaces
                String[] words = sentence.split("\\s+");

                List<WordFrequency> inputList = new ArrayList<>();
                for (String word : words) {
                    WordFrequency input = new WordFrequency(word, 1);
                    inputList.add(input);
                }

                //get the map for the next step of sizing the same word
                Map<String, List<WordFrequency>> map = getListMap(inputList);

                List<WordFrequency> list = new ArrayList<>();
                for (Map.Entry<String, List<WordFrequency>> entry : map.entrySet()) {
                    WordFrequency input = new WordFrequency(entry.getKey(), entry.getValue().size());
                    list.add(input);
                }
                inputList = list;

                inputList.sort((w1, w2) -> w2.getWordCount() - w1.getWordCount());

                StringJoiner joiner = new StringJoiner("\n");
                for (WordFrequency w : inputList) {
                    String s = w.getWord() + " " + w.getWordCount();
                    joiner.add(s);
                }
                return joiner.toString();
            } catch (Exception e) {
                return "Calculate Error";
            }
        }
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
