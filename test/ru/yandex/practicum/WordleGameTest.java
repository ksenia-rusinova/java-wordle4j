package ru.yandex.practicum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.yandex.practicum.wordle.exceptions.WordNotFoundInDictionary;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

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
    void testCheckUserWordAgainstAnswer(String answer, String userWord, String expected) throws IOException, WordNotFoundInDictionary {
        wordleDictionary.filterListByLength(wordleDictionaryLoader, "words_ru.txt");
        wordleGame.setAnswer(answer);

        String actual = wordleGame.checkUserWordAgainstAnswer(wordleDictionary, userWord.toLowerCase());
        assertEquals(expected, actual);

        assertEquals(userWord.toLowerCase(), wordleGame.getListOfAttempts().get(0));
    }

    @Test
    void testCheckWordNotFoundInDictionaryException() throws IOException {
        wordleDictionary.filterListByLength(wordleDictionaryLoader, "words_ru.txt");

        Throwable thrown = assertThrows(WordNotFoundInDictionary.class, () ->
                wordleGame.checkUserWordAgainstAnswer(wordleDictionary, "йцуке".toLowerCase())
        );
        assertEquals("Слово: йцуке НЕ найдено в словаре.", thrown.getMessage());
    }

    @Test
    void testSearchForRightWordsListOfAttemptsEmpty() throws IOException {
        wordleDictionary.filterListByLength(wordleDictionaryLoader, "words_ru.txt");
        wordleGame.setAnswer("жатва");

        wordleGame.searchForRightWords(wordleDictionary);
        assertFalse(wordleGame.getCandidateWordCounts().isEmpty());
    }

    @Test
    void testSearchForRightWordsListOfAttemptsNotEmpty() throws IOException, WordNotFoundInDictionary {
        wordleDictionary.filterListByLength(wordleDictionaryLoader, "words_ru.txt");
        wordleGame.setAnswer("жатва");

        wordleGame.checkUserWordAgainstAnswer(wordleDictionary, "севец");

        wordleGame.searchForRightWords(wordleDictionary);
        assertFalse(wordleGame.getCandidateWordCounts().isEmpty());
    }
}
