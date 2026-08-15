package ru.yandex.practicum.wordle.exceptions;

public class WordNotFoundInDictionary extends Exception {

    public WordNotFoundInDictionary(String word) {
        super("Слово: " + word + " НЕ найдено в словаре.");
    }
}
