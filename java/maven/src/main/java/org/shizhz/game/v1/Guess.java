package org.shizhz.game.v1;

/**
 * Created by zzshi on 8/25/14.
 */
public class Guess {
    private final CompareNumber compareNumber;
    private final AnswerGenerator answerGenerator;

    public Guess(AnswerGenerator answerGenerator, CompareNumber compareNumber) {
        this.answerGenerator = answerGenerator;
        this.compareNumber = compareNumber;
    }

    public String play(int guess) {
        return compareNumber.compare(answerGenerator.generateAnswer(), guess);
    }
}
