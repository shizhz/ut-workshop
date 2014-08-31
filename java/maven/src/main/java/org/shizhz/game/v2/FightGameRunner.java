package org.shizhz.game.v2;

import org.shizhz.game.v2.domain.*;
import org.shizhz.game.v2.ui.UIStrategy;

/**
 * Created by zzshi on 8/30/14.
 */
public class FightGameRunner implements GameRunner {
    private static final String PROMPT_TEMP = "Please input a number(%s) ,%s:";

    private static final String WIN_BY_BEAT_COMPETITOR_TEMP = "Congratulation, %s.";

    private static final String WIN_BY_CORRECT_GUESS_TEMP = "You got it. Congratulation，%s!";

    private static final Counter MAX_TIMES = Counter.of(6);
    private static final String USER_A = "UserA";
    private static final String USER_B = "UserB";

    private UIStrategy uiStrategy;
    private AnswerGenerator answerGenerator;
    private Comparator comparator;
    private Counter leftTimesForUserA = Counter.copy(MAX_TIMES);
    private Counter leftTimesForUserB = Counter.copy(MAX_TIMES);
    private boolean guessedByIndex[] = new boolean[AnswerGenerator.ANSWER_LENGTH];
    private Answer answer;
    private boolean gameOver;

    private FightGameRunner() {
    }

    public static GameRunner newInstance() {
        return new FightGameRunner();
    }

    @Override
    public GameRunner withUI(UIStrategy ui) {
        uiStrategy = ui;
        return this;
    }

    @Override
    public GameRunner withAnswerGenerator(AnswerGenerator answerGenerator) {
        this.answerGenerator = answerGenerator;
        return this;
    }

    @Override
    public GameRunner withNumberComparator(Comparator comparator) {
        this.comparator = comparator;
        return this;
    }

    @Override
    public void run() {
        answer = answerGenerator.generateAnswer();

        while(shouldContinue()) {
            roundForUserA();
            if (shouldContinue()) {
                roundForUserB();
            }
        }
    }

    private void roundForUserA() {
        round(USER_A, leftTimesForUserA, leftTimesForUserB);
    }


    private void roundForUserB() {
        round(USER_B, leftTimesForUserB, leftTimesForUserA);
    }

    private boolean shouldContinue() {
        return !gameOver;
    }

    private void round(String user, Counter counterOfSelf, Counter counterOfCompetitor) {
        if (!counterOfCompetitor.hasChanceLeft()) {
            win(WIN_BY_BEAT_COMPETITOR_TEMP, user);
            return;
        }

        Guess guess = uiStrategy.nextGuess(prompt(counterOfSelf, user));
        ComparingResult comparingResult = comparator.compare(answer, guess);

        if (isPerfectGuess(comparingResult)) {
            win(WIN_BY_CORRECT_GUESS_TEMP, user);
            return;
        }

        counterOfCompetitor.reduce(newlyCorrectGuessNumber(comparingResult));
        if (!counterOfCompetitor.hasChanceLeft()) {
            win(WIN_BY_BEAT_COMPETITOR_TEMP, user);
            return;
        }

        counterOfSelf.reduce(1);
    }


    private int newlyCorrectGuessNumber(ComparingResult comparingResult) {
        int newlyCorrectGuess = 0;

        for (int i = 0; i < guessedByIndex.length; i ++) {
            if (!guessedByIndex[i] && comparingResult.preciseMatchesAt(i)) {
                guessedByIndex[i] = true;
                newlyCorrectGuess ++;
            }
        }
        return newlyCorrectGuess;
    }

    private void win(String messageTemp, String user) {
        uiStrategy.displayMessage(String.format(messageTemp, user));
        gameOver = true;
    }

    private String prompt(Counter counter, String user) {
        return String.format(PROMPT_TEMP, counter.times(), user);
    }

    private boolean isPerfectGuess(ComparingResult comparingResult) {
        return comparingResult.numberOfPreciseMatch() == AnswerGenerator.ANSWER_LENGTH;
    }

    @Override
    public boolean supportMode(GameMode gameMode) {
        return gameMode == GameMode.FIGHT;
    }
}
