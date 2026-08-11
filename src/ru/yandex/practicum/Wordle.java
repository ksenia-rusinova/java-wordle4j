package ru.yandex.practicum;

import ru.yandex.practicum.wordle.exceptions.WordNotFoundInDictionary;

import java.io.*;
import java.util.Scanner;

public class Wordle {
    private static final Scanner scanner = new Scanner(System.in);
    private static final WordleGame wordleGame = new WordleGame();
    private static final WordleDictionary wordleDictionary = new WordleDictionary();
    private static final WordleDictionaryLoader wordleDictionaryLoader = new WordleDictionaryLoader();

    public static void main(String[] args) throws IOException {
        showMenu();

        try(LogPrinter logPrinter = new LogPrinter("program_log.txt")) {

            ///обработка исключения NumberFormatException (при вводе НЕ числа (кириллицы, латиницы итд))
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:

                        ///обработка исключения FileNotFoundException (если файл НЕ существует)
                        wordleGame.getRandomWordFromList(wordleDictionaryLoader, wordleDictionary);
                        ///удалить в конце
                        System.out.println("Загаданное слово - " + wordleGame.getAnswer());

                        System.out.println("Правила игры:\n1. Мы загадали слово из русского словаря (если в слове есть буква 'ё', она заменена на 'е') из 5 букв.\n2. У Вас есть 6 попыток чтобы его отгадать.\n(введенное Вами слово должно быть в нижнем регистре)\n3. Результатом сравнения будет строка из пяти символов, где каждый из них соответствует букве очередного ввода пользователя:\n'-' им отмечается буква, которой НЕТ в загаданном слове;\n'+' этим символом отмечается буква, которая ЕСТЬ в загаданном слове и находится на правильной позиции;\n'^' так отмечается буква, которая ЕСТЬ в загаданном слове, но находится в другом месте.\nПример: +^-^-");
                        System.out.println("Если Вам нужна подсказка, нажмите Enter.");

                        final int MAX_ATTEMPTS = 6;

                        for (int i = 1; i <= MAX_ATTEMPTS && wordleGame.getSteps() != 0; i++) {
                            System.out.printf("%d-я попытка. Введите слово.%n", i);

                            ///обработка собственного исключения WordNotFoundInDictionary
                            attempt();

                            if (wordleGame.getSteps() == 0) {
                                System.out.println("Загаданное слово - " + wordleGame.getAnswer());
                                break;
                            }
                        }

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
            } catch (WordNotFoundInDictionary exception) {
                logPrinter.println("Произошло исключение: WordNotFoundInDictionary\n" + exception.getMessage());
                System.out.println("Введенное слово НЕ из словаря words_ru.txt");
            }
        }
    }

    private static void showMenu() {
        System.out.println("Выберите действие:");
        System.out.println("1 — Начать игру Wordle");
        System.out.println("0 — Завершить");
    }

    private static void attempt() {

        while(true) {
            String wordAttempt = scanner.nextLine();

            ///ввод пользователя = 5 символам
            ///пользователь нажал Enter
            ///ввод пользователя != 5 символам
            if(wordAttempt.length() == 5) {

                String result = wordleGame.checkUserWordAgainstAnswer(wordleDictionary, wordAttempt.toLowerCase());

                ///если результат = "+++++"
                ///если результат != "+++++"
                if(result.equals("+++++")){
                    System.out.println("Поздравляем! Вы отгадали слово!");
                    wordleGame.setSteps(0);
                } else {
                    System.out.println(result);
                    wordleGame.setSteps(wordleGame.getSteps() - 1);
                }

                break;

            } else if(wordAttempt.isBlank()) {

                System.out.println("Подсказки:");
                wordleGame.searchForRightWords(wordleDictionary);

                for(String rightWord : wordleGame.getListOfRightWords()){
                    System.out.println(rightWord);
                }

                if(wordleGame.getListOfRightWords().size() == 1){
                    System.out.println("Поздравляем! Вы отгадали слово!");
                    wordleGame.setSteps(0);
                    break;
                }

            } else {
                System.out.println("Кол-во символов в слове != 5. Попытка не засчитана. Введите слово из 5 букв.");
            }
        }
    }
}
