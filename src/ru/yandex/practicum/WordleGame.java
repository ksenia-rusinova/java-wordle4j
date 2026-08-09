package ru.yandex.practicum;

import java.io.IOException;
import java.util.*;
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
    private static final WordleDictionary wordleDictionary = new WordleDictionary();

    private String answer = "";
    private int steps = 6;
    private List<String> listOfAttempts = new ArrayList<>();
    private List<String> listOfRightWords = new ArrayList<>();
    private int index = 1;

    public void getRandomWordFromList() throws IOException {
        wordleDictionary.filterListByLength();
        int indexOfChosenWord = ThreadLocalRandom.current().nextInt(wordleDictionary.getDictionaryList().size());
        answer = wordleDictionary.getDictionaryList().get(indexOfChosenWord);
    }

    public void addAttemptToList(String wordFromUser){
        listOfAttempts.add(wordFromUser);
    }

    ///метод для проверки введенного слова
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

    //поиск подсказок
    public void searchForRightWords() {

        ///список listOfAttempts (попытки пользователя) НЕ пустой, пользователь уже вводил слова
        ///список listOfAttempts (попытки пользователя) пустой, пользователь НЕ вводил слова
        if(!listOfAttempts.isEmpty()){

            for(String attempt : listOfAttempts) {

                //собираю список matches (совпадения с answer)
                List<Integer> matches = new ArrayList<>();
                for(int i = 0; i < attempt.length(); i++){
                    if(attempt.charAt(i) == answer.charAt(i)){
                        matches.add(i);
                    }
                }

                ///список listOfRightWords (подсказки) НЕ пустой, пользователь уже нажимал Enter
                ///список listOfRightWords (подсказки) пустой, пользователь еще НЕ нажимал Enter ни разу
                if(!listOfRightWords.isEmpty()) {

                    Collections.sort(matches);
                    Set<String> resultSet = new LinkedHashSet<>();

                    for (String word : listOfRightWords) {
                        boolean allMatch = true;
                        for (int idx : matches) {
                            if (answer.charAt(idx) != word.charAt(idx)) {
                                allMatch = false;
                                break;
                            }
                        }
                        if (allMatch) {
                            resultSet.add(word);
                        }
                    }
                    listOfRightWords.clear();
                    listOfRightWords.addAll(resultSet);

                } else {

                    ///если у пользователя в слове из списка listOfAttempts нет ни одного совпадения с answer
                    ///если у пользователя в слове из списка listOfAttempts есть совпадения с answer
                    if(matches.isEmpty()) {

                        for (String word : wordleDictionary.getDictionaryList()) {
                            if(answer.charAt(0) == word.charAt(0)) {
                                listOfRightWords.add(word);
                            }
                        }
                    } else {
                        Collections.sort(matches);
                        Set<String> resultSet = new LinkedHashSet<>();

                        for (String word : wordleDictionary.getDictionaryList()) {
                            boolean allMatch = true;
                            for (int idx : matches) {
                                if (answer.charAt(idx) != word.charAt(idx)) {
                                    allMatch = false;
                                    break;
                                }
                            }
                            if (allMatch) {
                                resultSet.add(word);
                            }
                        }
                        if (listOfRightWords != null) {
                            listOfRightWords.clear();
                            listOfRightWords.addAll(resultSet);
                        }
                    }
                }
            }
        } else {
            ///список listOfRightWords (подсказки) НЕ пустой, пользователь уже нажимал Enter (запрошивал подсказки)
            ///список listOfRightWords (подсказки) пустой, пользователь НЕ нажимал Enter (НЕ запрошивал подсказки)
            if(!listOfRightWords.isEmpty()){

                List<String> next = new ArrayList<>();
                for (String word : listOfRightWords) {
                    if(answer.charAt(index) == word.charAt(index)) {
                        next.add(word);
                    }
                }
                listOfRightWords.clear();
                listOfRightWords.addAll(next);
                index++;

            } else {
                for (String word : wordleDictionary.getDictionaryList()) {
                    if(answer.charAt(0) == word.charAt(0)) {
                        listOfRightWords.add(word);
                    }
                }
            }
        }
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

    public List<String> getListOfRightWords() {
        return listOfRightWords;
    }
}
