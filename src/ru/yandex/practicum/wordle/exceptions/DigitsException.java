package ru.yandex.practicum.wordle.exceptions;

public class DigitsException extends RuntimeException {
    public DigitsException(String word) {
        super("Слово: " + word + " состоит из цифр. Должно было состоять только из кириллицы.");
    }
}
