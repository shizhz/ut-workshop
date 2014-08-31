package org.shizhz.game.v2.domain;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by zzshi on 8/30/14.
 */
public class AnswerTest {
    @Test
    public void should_return_false_if_answer_is_constructed_from_null() throws Exception {
        // given
        Answer answer = Answer.of(null);

        // when
        boolean isValid = answer.isValid();

        // then
        assertThat(isValid, is(false));

    }

    @Test
    public void should_return_false_if_answer_is_constructed_from_empty_input() throws Exception {
        // given
        Answer answer = Answer.of("");

        // when
        boolean isValid = answer.isValid();

        // then
        assertThat(isValid, is(false));
    }

    @Test
    public void should_return_false_if_answer_is_constructed_from_non_pure_digit_input() throws Exception {
        // given
        Answer answer = Answer.of("12ab");


        // when
        boolean isValid = answer.isValid();

        // then
        assertThat(isValid, is(false));

    }

    @Test
    public void should_return_false_if_answer_is_constructed_from_pure_digits_but_length_is_invalid() throws Exception {
        // given
        Answer answerWithShorterLength = Answer.of("123");
        Answer answerWithLongerLength = Answer.of("123");

        // when
        boolean isShorterLengthOneValid = answerWithShorterLength.isValid();
        boolean isLongerLengthOneValid = answerWithLongerLength.isValid();

        // then
        assertThat(isShorterLengthOneValid, is(false));
        assertThat(isLongerLengthOneValid, is(false));
    }

    @Test
    public void should_return_false_if_answer_is_constructed_from_pure_digits_with_valid_length_but_has_duplicated_digits() throws Exception {
        // given
        Answer answer = Answer.of("1233");

        // when
        boolean isValid = answer.isValid();

        // then
        assertThat(isValid, is(false));
    }

    @Test
    public void should_return_true_if_answer_is_constructed_from_valid_input() throws Exception {
        // given
        Answer answer = Answer.of("1234");

        // when
        boolean isValid = answer.isValid();

        // then
        assertThat(isValid, is(true));
    }
}
