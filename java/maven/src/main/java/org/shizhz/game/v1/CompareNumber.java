package org.shizhz.game.v1;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.List;

/**
 * Created by zzshi on 8/25/14.
 */
public class CompareNumber {

    public String compare(int answer, int guess) {
        List<Character> answerList = asCharList(answer);
        List<Character> guessList = asCharList(guess);

        return String.format("%sA%sB", countNumberOfA(answerList, guessList), countNumberOfB(answerList, guessList));
    }

    private int countNumberOfB(List<Character> answerList, List<Character> guessList) {
        return Sets.intersection(Sets.newHashSet(answerList), Sets.newHashSet(guessList)).size() - countNumberOfA(answerList, guessList);
    }

    private int countNumberOfA(List<Character> answerList, List<Character> guessList) {
        int numberOfA = 0;

        for (int i = 0; i < guessList.size(); i++) {
            if (guessList.get(i).equals(answerList.get(i))) {
                numberOfA++;
            }
        }

        return numberOfA;
    }

    private List<Character> asCharList(int input) {
        return Lists.charactersOf(String.valueOf(input));
    }
}
