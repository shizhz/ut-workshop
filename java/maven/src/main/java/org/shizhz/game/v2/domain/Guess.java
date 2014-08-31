package org.shizhz.game.v2.domain;

import org.shizhz.game.v2.ValidationUtils;

/**
 * Created by zzshi on 8/30/14.
 */
public class Guess {
    private String guess;

    private Guess(String input) {
        this.guess = input == null ? "" : input;
    }

    public String getGuess() {
        return guess;
    }

    @Override
    public int hashCode() {
        return getGuess().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Guess)) return false;

        return Guess.class.cast(obj).getGuess().equals(guess);
    }

    public static Guess of(String input) {
        return new Guess(input);
    }

    public boolean isValid() {
        return ValidationUtils.isValid(guess);
    }
}
