package ru.yandex.practicum;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WordleDictionary {

    private List<String> dictionaryList = new ArrayList<>();

    WordleDictionaryLoader wordleDictionaryLoader = new WordleDictionaryLoader();

    public void filterListByLength() throws IOException {
        List<String> internalList = wordleDictionaryLoader.readWordsFromFile("words_ru.txt");

        for(String word : internalList){
            if(word.length() == 5){
                dictionaryList.add(normalizeYoToEe(word).toLowerCase());
            }
        }
    }

    private String normalizeYoToEe(String word) {
        if (word == null) return null;

        StringBuilder sb = new StringBuilder(word);
        for (int i = 0; i < sb.length(); i++) {
            char c = sb.charAt(i);
            if (c == 'ё') {
                sb.setCharAt(i, 'е');
            } else if (c == 'Ё') {
                sb.setCharAt(i, 'Е');
            }
        }
        return sb.toString();
    }

    public List<String> getDictionaryList() {
        return dictionaryList;
    }
}
