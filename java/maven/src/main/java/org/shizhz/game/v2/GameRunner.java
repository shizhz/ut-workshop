package org.shizhz.game.v2;

import org.shizhz.game.v2.domain.AnswerGenerator;
import org.shizhz.game.v2.domain.Comparator;
import org.shizhz.game.v2.ui.UIStrategy;

/**
 * Created by zzshi on 8/30/14.
 */
public interface GameRunner {
    GameRunner withUI(UIStrategy ui);

    GameRunner withAnswerGenerator(AnswerGenerator answerGenerator);

    GameRunner withNumberComparator(Comparator comparator);

    boolean supportMode(GameMode gameMode);

    void run();
}
