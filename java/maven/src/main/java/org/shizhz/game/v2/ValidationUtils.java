package org.shizhz.game.v2;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.shizhz.game.v2.domain.AnswerGenerator;

import java.util.List;
import java.util.Set;

/**
 * Created by zzshi on 8/29/14.
 */
public class ValidationUtils {
    public static boolean isValid(String number) {
        try {
            int i = Integer.valueOf(number);
            return isNumberValid(i) && isLengthValid(i);
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    private static boolean isNumberValid(int number) {
        List<Character> digitsList = Lists.charactersOf(String.valueOf(number));
        Set<Character> digitsSet = Sets.newHashSet(digitsList);

        return digitsList.size() == digitsSet.size();
    }

    private static boolean isLengthValid(int number) {
        return String.valueOf(number).length() == AnswerGenerator.ANSWER_LENGTH;
    }
}
