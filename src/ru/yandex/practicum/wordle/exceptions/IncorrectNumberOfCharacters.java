package ru.yandex.practicum.wordle.exceptions;

public class IncorrectNumberOfCharacters extends RuntimeException {
    public IncorrectNumberOfCharacters(String word) {
        super("Кол-во символов в слове '" + word + "' != 5.");
    }
}
