package org.shizhz.game.v2;

import org.shizhz.game.v2.domain.AnswerGenerator;
import org.shizhz.game.v2.domain.Comparator;
import org.shizhz.game.v2.ui.UIStrategy;
import org.shizhz.game.v2.ui.impl.GameCLI;

/**
 * Created by zzshi on 8/29/14.
 */
public class Game {
    private static final String MODE_MENU = "Please choose game mode (1.Story, 2.Fight): ";

    private UIStrategy ui;
    private AnswerGenerator answerGenerator;
    private Comparator comparator;
    private GameMode mode;
    private GameRunner[] gameRunners = new GameRunner[]{StoryGameRunner.newInstance(), FightGameRunner.newInstance()};

    public static Game newInstance() {
        return new Game();
    }

    public Game withUI(UIStrategy ui) {
        this.ui = ui;
        return this;
    }

    public Game withAnswerGenerator(AnswerGenerator answerGenerator) {
        this.answerGenerator = answerGenerator;
        return this;
    }

    public Game withNumberComparator(Comparator comparator) {
        this.comparator = comparator;
        return this;
    }

    public void start() {
        ui.gameStart();
        mode = ui.chooseMode(MODE_MENU);
        for (GameRunner gameRunner : getGameRunners()) {
            if (gameRunner.supportMode(mode)) {
                gameRunner.withUI(ui).withAnswerGenerator(answerGenerator).withNumberComparator(comparator).run();
            }
        }
        ui.gameOver();
    }

    public GameMode mode() {
        return mode;
    }

    public GameRunner[] getGameRunners() {
        return gameRunners;
    }

    public void setGameRunners(GameRunner[] gameRunners) {
        this.gameRunners = gameRunners;
    }

    public static void main(String[] args) {
        AnswerGenerator answerGenerator = AnswerGenerator.newInstance();
        Comparator comparator = new Comparator();
        UIStrategy ui = new GameCLI(System.in, System.out);
        Game game = newInstance().withAnswerGenerator(answerGenerator).withNumberComparator(comparator).withUI(ui);
        game.start();
    }
}
