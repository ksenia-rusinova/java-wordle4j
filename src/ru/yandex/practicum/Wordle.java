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
    public static final WordleGame wordleGame = new WordleGame();

    public static void main(String[] args) throws IOException {

        boolean running = true;
        while(running){
            showMenu();

            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    wordleGame.getRandomWordFromList();
                    wordleGame.setSteps(6);
                    ///удалить в конце
                    System.out.println("Слово загадано - " + wordleGame.getAnswer());

                    System.out.println("Правила игры:\nМы загадали слово из русского словаря (если в слове есть буква 'ё', она заменена на 'е') из 5 букв.\nУ Вас есть 6 попыток чтобы его отгадать.\n(введенное Вами слово должно быть в нижнем регистре)");
                    ///доп правила про подсказку дописать
                    System.out.println("Слово загадано.\nПервая попытка.\nВведите слово.");
                    wordleGame.addAttemptToList(scanner.nextLine());

                    //записала первое слово первой попытки в список, далее надо сравнивать с загаданным

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

}
