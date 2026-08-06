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

        boolean running = true;
        while(running){
            showMenu();

            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    wordleGame.getRandomWordFromList();
                    ///удалить в конце
                    System.out.println("Слово загадано - " + wordleGame.getAnswer());

                    System.out.println("Правила игры:\n1. Мы загадали слово из русского словаря (если в слове есть буква 'ё', она заменена на 'е') из 5 букв.\n2. У Вас есть 6 попыток чтобы его отгадать.\n(введенное Вами слово должно быть в нижнем регистре)\n3. Результатом сравнения будет строка из пяти символов, где каждый из них соответствует букве очередного ввода пользователя:\n'-' им отмечается буква, которой НЕТ в загаданном слове;\n'+' этим символом отмечается буква, которая ЕСТЬ в загаданном слове и находится на правильной позиции;\n'^' так отмечается буква, которая ЕСТЬ в загаданном слове, но находится в другом месте.\nПример: +^-^-");
                    ///доп правила про подсказку дописать
                    System.out.println("Начинаем играть!\nПервая попытка. Введите слово.");
                    running = attempt();

                    System.out.println("Вторая попытка. Введите слово.");
                    running = attempt();

                    System.out.println("Третья попытка. Введите слово.");
                    running = attempt();

                    System.out.println("Четвертая попытка. Введите слово.");
                    running = attempt();

                    System.out.println("Пятая попытка. Введите слово.");
                    running = attempt();

                    System.out.println("Последняя попытка. Введите слово.");
                    running = attempt();

                    System.out.println("Вы не отгадали слово. Увы, но это проигрыш!");
                    running = false;

                    //короче надо переписать, тк нек использую steps

                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Неверный выбор.");
            }
        }
    }

    private static void showMenu() {
        System.out.println("Выберите действие:");
        System.out.println("1 — Начать игру Wordle");
        System.out.println("0 — Завершить");
    }

    private static boolean attempt() {
        boolean running = true;

        boolean firstAttempt = true;
        while(firstAttempt) {
            String wordAttempt = scanner.nextLine();
            wordleGame.addAttemptToList(wordAttempt);

            if(wordAttempt.length() == 5){
                String result = wordleGame.checkUserWordAgainstAnswer(wordAttempt.toLowerCase());

                if(result.equals("+++++")){
                    System.out.println("Поздравляем! Вы отгадали слово!");
                    running = false;
                } else {
                    System.out.println(result);
                    wordleGame.setSteps(wordleGame.getSteps() - 1);
                }

                firstAttempt = false;
            } else {
                System.out.println("Кол-во символов в слове != 5. Попытка не засчитана. Введите слово из 5 букв.");
            }
        }

        return running;
    }

}
