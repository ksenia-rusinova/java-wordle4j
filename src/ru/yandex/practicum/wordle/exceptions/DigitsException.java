package ru.yandex.practicum.wordle.exceptions;

public class DigitsException extends Exception {
    public DigitsException(String word) {
        super("Слово: " + word + " содержит цифры. Должно было состоять только из кириллицы.");
    }
}
