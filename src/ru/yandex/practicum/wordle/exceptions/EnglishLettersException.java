package ru.yandex.practicum.wordle.exceptions;

public class EnglishLettersException extends RuntimeException {
    public EnglishLettersException(String word) {
        super("Слово: " + word + " состоит из латиницы. Должно было состоять только из кириллицы.");
    }
}
