package org.shizhz.game.v2;

import org.shizhz.game.v2.domain.*;
import org.shizhz.game.v2.ui.UIStrategy;

/**
 * Created by zzshi on 8/30/14.
 */
public class StoryGameRunner implements GameRunner {
    private static final String WELCOME = "Welcome!";
    private static final String PROMPT_TEMP = "Please input your number(%s): ";
    private static final int MAX_TIMES = 6;
    private static final String CONGRATULATIONS = "Congratulations!";
    private static final String GAME_OVER = "Game Over";

    private UIStrategy uiStrategy;
    private AnswerGenerator answerGenerator;
    private Comparator comparator;
    private int leftTimes = MAX_TIMES;
    private Answer answer;

    private StoryGameRunner() {
    }

    public static GameRunner newInstance() {
        return new StoryGameRunner();
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

    public void run() {
        answer = answerGenerator.generateAnswer();
        uiStrategy.displayMessage(WELCOME);

        while (shouldContinue()) {
            ComparingResult comparingResult = comparator.compare(answer, uiStrategy.nextGuess(prompt()));
            if (isPerfectGuess(comparingResult)) {
                uiStrategy.displayMessage(CONGRATULATIONS);
                return;
            }

            uiStrategy.displayMessage(comparingResult.toString());

            leftTimes--;
        }

        uiStrategy.displayMessage(GAME_OVER);
    }

    private String prompt() {
        return String.format(PROMPT_TEMP, leftTimes);
    }

    private boolean isPerfectGuess(ComparingResult comparingResult) {
        return comparingResult.numberOfPreciseMatch() == AnswerGenerator.ANSWER_LENGTH;
    }

    private boolean shouldContinue() {
        return leftTimes > 0;
    }

    @Override
    public boolean supportMode(GameMode gameMode) {
        return gameMode == GameMode.STORY;
    }
}
