package ru.yandex.practicum;

import java.io.IOException;
import java.util.Scanner;

/*
в главном классе нам нужно:
    создать лог-файл (он должен передаваться во все классы)
    создать загрузчик словарей WordleDictionaryLoader
    загрузить словарь WordleDictionary с помощью класса WordleDictionaryLoader
    затем создать игру WordleGame и передать ей словарь
    вызвать игровой метод в котором в цикле опрашивать пользователя и передавать информацию в игру
    вывести состояние игры и конечный результат
 */
public class Wordle {
    private static final Scanner scanner = new Scanner(System.in);
    private static final WordleGame wordleGame = new WordleGame();

    public static void main(String[] args) throws IOException {
        showMenu();

        int choice = Integer.parseInt(scanner.nextLine());
        switch (choice) {
            case 1:
                wordleGame.getRandomWordFromList();
                ///удалить в конце
                System.out.println("Слово загадано - " + wordleGame.getAnswer());

                System.out.println("Правила игры:\n1. Мы загадали слово из русского словаря (если в слове есть буква 'ё', она заменена на 'е') из 5 букв.\n2. У Вас есть 6 попыток чтобы его отгадать.\n(введенное Вами слово должно быть в нижнем регистре)\n3. Результатом сравнения будет строка из пяти символов, где каждый из них соответствует букве очередного ввода пользователя:\n'-' им отмечается буква, которой НЕТ в загаданном слове;\n'+' этим символом отмечается буква, которая ЕСТЬ в загаданном слове и находится на правильной позиции;\n'^' так отмечается буква, которая ЕСТЬ в загаданном слове, но находится в другом месте.\nПример: +^-^-");
                System.out.println("Если Вам нужна подсказка, нажмите Enter.");

                final int MAX_ATTEMPTS = 6;

                for (int i = 1; i <= MAX_ATTEMPTS && wordleGame.getSteps() != 0; i++) {
                    System.out.printf("%d-я попытка. Введите слово.%n", i);
                    attempt();

                    if (wordleGame.getSteps() == 0) {
                        break;
                    }
                }

            case 0:
                wordleGame.setSteps(0);
                break;
            default:
                System.out.println("Неверный выбор.");
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

                wordleGame.addAttemptToList(wordAttempt);
                String result = wordleGame.checkUserWordAgainstAnswer(wordAttempt.toLowerCase());

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
                wordleGame.searchForRightWords();

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
