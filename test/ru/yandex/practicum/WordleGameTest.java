package ru.yandex.practicum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class WordleGameTest {
    private static WordleGame wordleGame;
    private static WordleDictionary wordleDictionary;
    private static WordleDictionaryLoader wordleDictionaryLoader;

    @BeforeEach
    void setUp() {
        wordleGame = new WordleGame();
        wordleDictionary = new WordleDictionary();
        wordleDictionaryLoader = new WordleDictionaryLoader();
    }

    @Test
    void testGetRandomWordFromList() throws IOException {
        wordleGame.getRandomWordFromList(wordleDictionaryLoader, wordleDictionary);
        assertFalse(wordleGame.getAnswer().isEmpty());
    }

    @ParameterizedTest(name = "answer={0} -> userWord={1} -> expected={2}")
    @CsvSource({
            "жатва, ЖАТВА, +++++",
            "жатва, тюник, ^----"
    })
    void testCheckUserWordAgainstAnswer(String answer, String userWord, String expected) throws IOException {
        wordleDictionary.filterListByLength(wordleDictionaryLoader, "words_ru.txt");
        wordleGame.setAnswer(answer);

        String actual = wordleGame.checkUserWordAgainstAnswer(wordleDictionary, userWord.toLowerCase());
        assertEquals(expected, actual);

        assertEquals(userWord.toLowerCase(), wordleGame.getListOfAttempts().get(0));
    }

    @Test
    void testSearchForRightWords() {
//надо писать
    }
}
