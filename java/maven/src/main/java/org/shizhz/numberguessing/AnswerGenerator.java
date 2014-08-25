package org.shizhz.numberguessing;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Created by zzshi on 8/25/14.
 */
public class AnswerGenerator {

    private boolean isNumberValid(int number) {
        List<Character> charList = Lists.charactersOf(String.valueOf(number));
        Set<Character> charSet = Sets.newHashSet(charList);

        return charList.size() == charSet.size();
    }

    public int generateAnswer() {
        int min = 1234;
        int max = 9876;

        Random random = new Random();
        int result = 0;

        do {
            result = random.nextInt(max - min + 1) + min;
        } while(!isNumberValid(result));

        return result;
    }
}
