package ru.yandex.practicum;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/*
в этом классе хранится словарь и состояние игры
    текущий шаг
    всё что пользователь вводил
    правильный ответ

в этом классе нужны методы, которые
    проанализируют совпадение слова с ответом
    предложат слово-подсказку с учётом всего, что вводил пользователь ранее

не забудьте про специальные типы исключений для игровых и неигровых ошибок
 */
public class WordleGame {
    private WordleDictionary wordleDictionary = new WordleDictionary();

    private String answer = "";
    private int steps = 6;
    private int indexOfChosenWord = 0;
    private List<String> listOfAttempts = new ArrayList<>();

    public void getRandomWordFromList() throws IOException {
        wordleDictionary.filterListByLength();
        indexOfChosenWord = ThreadLocalRandom.current().nextInt(wordleDictionary.getDictionaryList().size());
        answer = wordleDictionary.getDictionaryList().get(indexOfChosenWord);
    }

    public void addAttemptToList(String wordFromUser){
        listOfAttempts.add(wordFromUser);
    }

    //метод на проверку введенного слова
    public String checkUserWordAgainstAnswer(String userWord) {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < userWord.length(); i++) {
            if (userWord.charAt(i) == answer.charAt(i)) {
                builder.append("+");
            } else if(userWord.charAt(i) != answer.charAt(i)) {
                boolean flag = false;
                for(int k = 0; k < answer.length(); k++){
                    if(userWord.charAt(i) == answer.charAt(k)){
                        flag = true;
                    }
                }
                if(flag) {
                    builder.append("^");
                } else {
                    builder.append("-");
                }
            }
        }
        return builder.toString();
    }

    public String getAnswer() {
        return answer;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public List<String> getListOfAttempts() {
        return listOfAttempts;
    }
}
