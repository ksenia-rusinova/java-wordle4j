package ru.yandex.practicum.wordle.exceptions;

public class WordNotFoundInDictionary extends RuntimeException {

    public WordNotFoundInDictionary(String word) {
        super("Слово: " + word + " НЕ найдено в словаре.");
    }
}
