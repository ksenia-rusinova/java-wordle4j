package ru.yandex.practicum;

import ru.yandex.practicum.wordle.exceptions.WordNotFoundInDictionary;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class WordleGame {
    private String answer = "";
    private int steps = 6;
    private final List<String> listOfAttempts = new ArrayList<>();
    private final LinkedHashMap<String, Integer> candidateWordCounts = new LinkedHashMap<>();

    public void getRandomWordFromList(WordleDictionaryLoader wordleDictionaryLoader, WordleDictionary wordleDictionary) throws IOException {
        wordleDictionary.filterListByLength(wordleDictionaryLoader, "words_ru.txt");
        int indexOfChosenWord = ThreadLocalRandom.current().nextInt(wordleDictionary.getDictionaryList().size());
        answer = wordleDictionary.getDictionaryList().get(indexOfChosenWord);
    }

    public String checkUserWordAgainstAnswer(WordleDictionary wordleDictionary, String userWord) throws WordNotFoundInDictionary {
        StringBuilder builder = new StringBuilder();

        if (!wordleDictionary.getDictionaryList().contains(userWord)) {
            throw new WordNotFoundInDictionary(userWord);
        }

        listOfAttempts.add(userWord);

        for (int i = 0; i < userWord.length(); i++) {
            if (userWord.charAt(i) == answer.charAt(i)) {
                builder.append("+");
            } else if (userWord.charAt(i) != answer.charAt(i)) {
                boolean flag = false;
                for (int k = 0; k < answer.length(); k++) {
                    if (userWord.charAt(i) == answer.charAt(k)) {
                        flag = true;
                        break;
                    }
                }
                builder.append(flag ? "^" : "-");
            }
        }
        return builder.toString();
    }

    public void searchForRightWords(WordleDictionary wordleDictionary) {
        Map<Integer, Character> exactPositions = new HashMap<>();
        Set<Character> presentLetters = new HashSet<>();

        candidateWordCounts.clear();

        if (listOfAttempts.isEmpty()) {
            String w = wordleDictionary.getDictionaryList().get(0);
            candidateWordCounts.put(w, 1);
            return;
        }

        for (String attempt : listOfAttempts) {

            for (int i = 0; i < answer.length(); i++) {
                char a = answer.charAt(i);
                char c = attempt.charAt(i);
                if (c == a) {
                    exactPositions.put(i, c);
                } else {
                    if (answer.indexOf(c) != -1) {
                        presentLetters.add(c);
                    }
                }
            }
        }

        for (String word : wordleDictionary.getDictionaryList()) {
            boolean ok = true;

            for (Map.Entry<Integer, Character> e : exactPositions.entrySet()) {
                int pos = e.getKey();
                char required = e.getValue();
                if (word.charAt(pos) != required) {
                    ok = false;
                    break;
                }
            }
            if (!ok) continue;

            for (char ch : presentLetters) {
                if (word.indexOf(ch) == -1) {
                    ok = false;
                    break;
                }
            }
            if (ok) {
                candidateWordCounts.put(word, candidateWordCounts.getOrDefault(word, 0) + 1);
            }
        }
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public List<String> getListOfAttempts() {
        return listOfAttempts;
    }

    public LinkedHashMap<String, Integer> getCandidateWordCounts() {
        return candidateWordCounts;
    }
}
