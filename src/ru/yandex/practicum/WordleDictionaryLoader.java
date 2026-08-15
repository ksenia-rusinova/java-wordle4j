package ru.yandex.practicum;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WordleDictionaryLoader {

    public List<String> readWordsFromFile(String filename) throws IOException {
        List<String> finalList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                finalList.add(line);
            }
        }
        return finalList;
    }
}
