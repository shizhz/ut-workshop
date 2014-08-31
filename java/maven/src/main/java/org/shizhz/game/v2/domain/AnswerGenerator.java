package org.shizhz.game.v2.domain;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Ordering;

import java.util.List;
import java.util.Random;

/**
 * Created by zzshi on 8/29/14.
 */
public class AnswerGenerator {
    public static final int ANSWER_LENGTH = 4;

    private static final ImmutableList<Integer> DIGITS = ImmutableList.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);

    private AnswerGenerator() {}

    private final Ordering<Integer> RANDOM_ORDERING = new Ordering<Integer>() {
        private Random random = new Random();

        @Override
        public int compare(Integer left, Integer right) {
            return random.nextInt() - random.nextInt();
        }
    };

    public static AnswerGenerator newInstance() {
        return new AnswerGenerator();
    }

    public Answer generateAnswer() {
        List<Integer> randomSortedCopy = RANDOM_ORDERING.sortedCopy(DIGITS);
        int startPosition = randomSortedCopy.indexOf(0) == 0 ? 1 : 0;
        return Answer.of(Joiner.on("").join(randomSortedCopy.subList(startPosition, startPosition + ANSWER_LENGTH)));
    }
}
