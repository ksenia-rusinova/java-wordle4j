package ru.yandex.practicum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WordleDictionaryTest {
    private static WordleDictionary wordleDictionary;
    private static WordleDictionaryLoader wordleDictionaryLoader;

    @BeforeEach
    void setUp() {
        wordleDictionary = new WordleDictionary();
        wordleDictionaryLoader = new WordleDictionaryLoader();
    }

    @ParameterizedTest(name = "expected={0} -> word={1}")
    @CsvSource({
            "актер, актёр",
            "АМЕБА, АМЁБА"
    })
    void testNormalizeYoToEe(String expected, String word) {
        String actual = wordleDictionary.normalizeYoToEe(word);
        assertEquals(expected, actual);
    }

    @Test
    void testFilterListByLength() throws IOException {
        wordleDictionary.filterListByLength(wordleDictionaryLoader, "words_ru_for_test.txt");
        List<String> internalList = wordleDictionary.getDictionaryList();

        assertEquals(2, internalList.size());
        assertEquals("амеба", internalList.get(0));
        assertEquals("актер", internalList.get(1));
    }
}
