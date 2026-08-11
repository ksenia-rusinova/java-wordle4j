package ru.yandex.practicum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WordleDictionaryLoaderTest {
    private static WordleDictionaryLoader wordleDictionaryLoader;

    @BeforeEach
    void setUp() {
        wordleDictionaryLoader = new WordleDictionaryLoader();
    }

    @Test
    void testReadWordsFromFile() throws IOException {
        List<String> internalList = wordleDictionaryLoader.readWordsFromFile("words_ru_for_test.txt");

        assertEquals(3, internalList.size());
        assertEquals("АМЁБА", internalList.get(0));
        assertEquals("актёр", internalList.get(1));
        assertEquals("жаркое", internalList.get(2));
    }
}
