package ru.yandex.practicum;

import ru.yandex.practicum.wordle.exceptions.DigitsException;
import ru.yandex.practicum.wordle.exceptions.EnglishLettersException;
import ru.yandex.practicum.wordle.exceptions.WordNotFoundInDictionary;

import java.io.*;
import java.util.*;

public class Wordle {
    private static final Scanner scanner = new Scanner(System.in);
    private static final WordleGame wordleGame = new WordleGame();
    private static final WordleDictionary wordleDictionary = new WordleDictionary();
    private static final WordleDictionaryLoader wordleDictionaryLoader = new WordleDictionaryLoader();

    public static void main(String[] args) throws IOException {
        showMenu();

        try (LogPrinter logPrinter = new LogPrinter("program_log.txt")) {

            ///обработка исключения NumberFormatException (при вводе НЕ числа (кириллицы, латиницы итд))
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        ///обработка исключения FileNotFoundException (если файл НЕ существует)
                        wordleGame.getRandomWordFromList(wordleDictionaryLoader, wordleDictionary);
                        System.out.println("Правила игры:\n1. Мы загадали слово из русского словаря (если в слове есть буква 'ё', она заменена на 'е') из 5 букв.\n2. У Вас есть 6 попыток чтобы его отгадать.\n(введенное Вами слово должно быть в нижнем регистре)\n3. Результатом сравнения будет строка из пяти символов, где каждый из них соответствует букве очередного ввода пользователя:\n'-' им отмечается буква, которой НЕТ в загаданном слове;\n'+' этим символом отмечается буква, которая ЕСТЬ в загаданном слове и находится на правильной позиции;\n'^' так отмечается буква, которая ЕСТЬ в загаданном слове, но находится в другом месте.\nПример: +^-^-");
                        System.out.println("Если Вам нужна подсказка, нажмите Enter.");

                        final int MAX_ATTEMPTS = 6;
                        for (int i = 1; i <= MAX_ATTEMPTS && wordleGame.getSteps() != 0; i++) {
                            System.out.printf("%d-я попытка. Введите слово.%n", i);
                            attempt();

                            if (wordleGame.getSteps() == 0) {
                                System.out.println("Загаданное слово - " + wordleGame.getAnswer());
                                break;
                            }
                        }
                        break;
                    case 0:
                        wordleGame.setSteps(0);
                        break;
                    default:
                        System.out.println("Неверный выбор.");
                }
            } catch (NumberFormatException exception) {
                logPrinter.println("Произошло исключение: NumberFormatException\n" + exception.getMessage());
                System.out.println("Ожидался ввод цифры.");
            } catch (FileNotFoundException exception) {
                logPrinter.println("Произошло исключение: FileNotFoundException\n" + exception.getMessage());
                System.out.println("Файл words_ru.txt НЕ существует.");
            }
        }
    }

    private static void showMenu() {
        System.out.println("Выберите действие:");
        System.out.println("1 — Начать игру Wordle");
        System.out.println("0 — Завершить");
    }

    private static void attempt() throws IOException {
        try (LogPrinter logPrinter = new LogPrinter("game_log.txt")) {
            while (true) {
                ///обработка собственых исключении WordNotFoundInDictionary, EnglishLettersException, DigitsException
                try {
                    String wordAttempt = scanner.nextLine();

                    if (!wordAttempt.isBlank()) {
                        wordAttempt = wordleDictionary.normalizeYoToEe(wordAttempt).toLowerCase().trim();
                    }
                    if (wordAttempt.chars().anyMatch(ch -> (ch >= 'a' && ch <= 'z'))) {
                        throw new EnglishLettersException(wordAttempt);
                    }
                    if (wordAttempt.chars().anyMatch(Character::isDigit)) {
                        throw new DigitsException(wordAttempt);
                    }

                    ///ввод пользователя = 5 символам
                    ///пользователь нажал Enter
                    ///ввод пользователя != 5 символам
                    if (wordAttempt.length() == 5) {
                        String result = wordleGame.checkUserWordAgainstAnswer(wordleDictionary, wordAttempt);

                        ///если результат = "+++++"
                        ///если результат != "+++++"
                        if (result.equals("+++++")) {
                            System.out.println("Поздравляем! Вы отгадали слово!");
                            wordleGame.setSteps(0);
                        } else {
                            System.out.println(result);
                            wordleGame.setSteps(wordleGame.getSteps() - 1);
                        }
                        break;

                    } else if (wordAttempt.isBlank()) {
                        wordleGame.searchForRightWords(wordleDictionary);
                        for (String w : wordleDictionary.getDictionaryList()) {
                            if (wordleGame.getCandidateWordCounts().containsKey(w)) {
                                System.out.println("Подсказка: " + w);
                                break;
                            }
                        }
                    } else {
                        System.out.println("Кол-во символов в слове != 5. Попытка не засчитана. Введите слово из 5 букв.");
                    }
                } catch (WordNotFoundInDictionary exception) {
                    logPrinter.println("Произошло исключение: WordNotFoundInDictionary\n" + exception.getMessage());
                    System.out.println("Введенное слово НЕ из словаря words_ru.txt");
                    System.out.println("Попытка не засчитана.");
                } catch (EnglishLettersException | DigitsException exception) {
                    logPrinter.println("Произошло исключение: EnglishLettersException/ DigitsException\n" + exception.getMessage());
                    System.out.println("Введенное слово должно состоять из кириллицы.");
                    System.out.println("Попытка не засчитана.");
                }
            }
        }
    }
}
