package org.shizhz.numberguessing;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class GuessTest {

    private Guess guess;
    @Mock
    private AnswerGenerator answerGenerator;
    @Mock
    private CompareNumber compareNumber;

    @Before
    public void setUp() throws Exception {
        guess = new Guess(answerGenerator, compareNumber);
    }

    @Test
    public void should_return_4A0B_if_guess_is_correct() throws Exception {
        // given
        given(answerGenerator.generateAnswer()).willReturn(1234);
        given(compareNumber.compare(1234, 1234)).willReturn("4A0B");

        // when
        String result = guess.play(1234);

        // then
        assertThat(result, is("4A0B"));
    }
}