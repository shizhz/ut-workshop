package org.shizhz.game.v2.domain;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by zzshi on 8/30/14.
 */
public class Comparator {
    public ComparingResult compare(Answer answer, Guess guess) {
        List<Character> answerCharList = Lists.charactersOf(answer.getAnswer());
        List<Character> guessCharList = Lists.charactersOf(guess.getGuess());

        handlePreciseMatch(answerCharList, guessCharList);
        handleFuzzyMatch(answerCharList, guessCharList);

        return ComparingResult.of(handlePreciseMatch(answerCharList, guessCharList), handleFuzzyMatch(answerCharList, guessCharList));
    }

    private Set<Character> handleFuzzyMatch(List<Character> answerCharList, List<Character> guessCharList) {
        Set<Character> result = Sets.<Character>newHashSet();

        for (int i = 0; i < guessCharList.size(); i ++) {
            Character guessChar = guessCharList.get(i);
            if (answerCharList.contains(guessChar) && !answerCharList.get(i).equals(guessChar))
            {
                result.add(guessChar);
            }
        }

        return result;
    }

    private Map<Integer, Character> handlePreciseMatch(List<Character> answerCharList, List<Character> guessCharList) {
        ImmutableMap.Builder<Integer, Character> builder = ImmutableMap.builder();

        for (int i = 0; i < guessCharList.size(); i ++) {
            if (guessCharList.get(i).equals(answerCharList.get(i))) {
                builder.put(i, answerCharList.get(i));
            }
        }

        return builder.build();
    }

    private List<Character> asCharList(int input) {
        return Lists.charactersOf(String.valueOf(input));
    }
}
