package org.shizhz.numberguessing;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CompareNumberTest {

    private CompareNumber compareNumber;

    @Before
    public void setup() {
        compareNumber = new CompareNumber();
    }

    @Test
    public void should_return_4A0B_when_guess_and_answer_are_same() {
        // given
        int answer = 1234;
        int guess = 1234;

        // when
        String result = compareNumber.compare(answer, guess);

        // then
        assertThat(result, is("4A0B"));
    }

    @Test
    public void should_return_0A0B_when_guess_is_5678_while_answer_is_1234() {
        // given
        int answer = 1234;
        int guess = 5678;

        // when
        String result = compareNumber.compare(answer, guess);

        // then
        assertThat(result, is("0A0B"));
    }

    @Test
    public void should_return_0A4B_when_guess_is_4321_while_answer_is_1234() {
        // given
        int answer = 1234;
        int guess = 4321;

        // when
        String result = compareNumber.compare(answer, guess);

        // then
        assertThat(result, is("0A4B"));
    }

    @Test
    public void should_return_1A2B_when_guess_is_1325_while_answer_is_1234() {
        // given
        int answer = 1234;
        int guess = 1325;

        // when
        String result = compareNumber.compare(answer, guess);

        // then
        assertThat(result, is("1A2B"));
    }
}