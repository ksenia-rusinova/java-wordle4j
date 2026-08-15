package ru.yandex.practicum.wordle.exceptions;

public class EnglishLettersException extends Exception {
    public EnglishLettersException(String word) {
        super("Слово: " + word + " содержит латиницу. Должно было состоять только из кириллицы.");
    }
}
