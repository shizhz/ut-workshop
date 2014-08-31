package org.shizhz.game.v2.ui;

import org.shizhz.game.v2.GameMode;
import org.shizhz.game.v2.domain.Guess;

/**
 * Created by zzshi on 8/30/14.
 */
public interface UIStrategy {
    public void gameStart();

    public Guess nextGuess(String prompt);

    public void displayMessage(String message);

    public void gameOver();

    GameMode chooseMode(String modeMenuMessage);
}
