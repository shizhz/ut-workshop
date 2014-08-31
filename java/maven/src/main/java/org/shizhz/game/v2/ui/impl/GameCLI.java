package org.shizhz.game.v2.ui.impl;

import org.shizhz.game.v2.GameMode;
import org.shizhz.game.v2.domain.Guess;
import org.shizhz.game.v2.ui.UIStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * Created by zzshi on 8/29/14.
 */
public class GameCLI implements UIStrategy {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private PrintStream writer;

    private BufferedReader reader;

    public GameCLI(InputStream is, OutputStream os) {
        this(new BufferedReader(new InputStreamReader(is)), new PrintStream(os));
    }

    public GameCLI(BufferedReader bufferedReader, PrintStream printStream) {
        this.reader = bufferedReader;
        this.writer = printStream;
    }

    @Override
    public void gameStart() {

    }

    @Override
    public Guess nextGuess(String prompt) {
        String input = null;
        Guess guess = Guess.of(input);

        while (!guess.isValid()) {
            writer.print(prompt);
            try {
                guess = Guess.of(reader.readLine());
            } catch (IOException e) {
                logger.warn("Unexpected IOException happened: {}", e.getMessage());
            }
        }

        return guess;
    }

    @Override
    public void displayMessage(String message) {
        writer.println(message);
    }

    @Override
    public void gameOver() {
        try {
            if (reader != null) {
                reader.close();
            }
        } catch (IOException e) {
            logger.warn("Unexpected exception happened: {}", e.getMessage());
        }

        if (writer != null) {
            writer.flush();
            writer.close();
        }
    }

    @Override
    public GameMode chooseMode(String modeMenuMessage) {
        while(true) {
            try {
                writer.print(modeMenuMessage);
                return GameMode.getEnum(reader.readLine());
            } catch (IOException e) {
                logger.warn("Unexpected exception happened: {}", e.getMessage());
            } catch (IllegalArgumentException iae) {
                logger.warn("Either input 1 or 2");
            }
        }
    }
}
