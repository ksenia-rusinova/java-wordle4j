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
    void testCheckUserWordAgainstAnswer(String answer, String userWord, String expected) throws IOException {
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

    //список listOfAttempts (попытки пользователя) пустой
    //список listOfRightWords (подсказки) пустой
    @Test
    void testListOfAttemptsAndListOfRightWordsEmpty() throws IOException {
        wordleDictionary.filterListByLength(wordleDictionaryLoader, "search_for_right_words_test.txt");
        wordleGame.setAnswer("жатва");

        wordleGame.searchForRightWords(wordleDictionary);
        assertEquals(5, wordleGame.getListOfRightWords().size());
        assertEquals("жарка", wordleGame.getListOfRightWords().get(0));
        assertEquals("жарок", wordleGame.getListOfRightWords().get(1));
        assertEquals("жатва", wordleGame.getListOfRightWords().get(2));
        assertEquals("жвало", wordleGame.getListOfRightWords().get(3));
        assertEquals("жатка", wordleGame.getListOfRightWords().get(4));
    }

    //список listOfAttempts (попытки пользователя) пустой
    //список listOfRightWords (подсказки) НЕ пустой
    @Test
    void testListOfAttemptsEmptyListOfRightWordsNotEmpty() throws IOException {
        wordleDictionary.filterListByLength(wordleDictionaryLoader, "search_for_right_words_test.txt");
        wordleGame.setAnswer("жатва");
        wordleGame.searchForRightWords(wordleDictionary);

        //повторный запуск searchForRightWords - поиск по 2-м первым буквам
        wordleGame.searchForRightWords(wordleDictionary);
        assertEquals(4, wordleGame.getListOfRightWords().size());
        assertEquals("жарка", wordleGame.getListOfRightWords().get(0));
        assertEquals("жарок", wordleGame.getListOfRightWords().get(1));
        assertEquals("жатва", wordleGame.getListOfRightWords().get(2));
        assertEquals("жатка", wordleGame.getListOfRightWords().get(3));

        //повторный запуск searchForRightWords - поиск по 3-м первым буквам
        wordleGame.searchForRightWords(wordleDictionary);
        assertEquals(2, wordleGame.getListOfRightWords().size());
        assertEquals("жатва", wordleGame.getListOfRightWords().get(0));
        assertEquals("жатка", wordleGame.getListOfRightWords().get(1));

        //повторный запуск searchForRightWords - поиск по 4-м первым буквам
        wordleGame.searchForRightWords(wordleDictionary);
        assertEquals(1, wordleGame.getListOfRightWords().size());
        assertEquals("жатва", wordleGame.getListOfRightWords().get(0));
    }

    //список listOfAttempts (попытки пользователя) НЕ пустой
    //список listOfRightWords (подсказки) пустой
    @Test
    void testListOfAttemptsNotEmptyListOfRightWordsEmpty() throws IOException {

    }

}
