package org.shizhz.game.v2.domain;

import org.shizhz.game.v2.ValidationUtils;

/**
 * Created by zzshi on 8/30/14.
 */
public class Answer {
    private String answer;

    private Answer(String answer) {
        this.answer = answer == null ? "" : answer;
    }

    public String getAnswer() {
        return answer;
    }

    @Override
    public int hashCode() {
        return answer.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Answer)) return false;

        return Answer.class.cast(obj).getAnswer().equals(answer);
    }

    public static Answer of(String input) {
        return new Answer(input);
    }

    public boolean isValid() {
        return ValidationUtils.isValid(answer);
    }
}
