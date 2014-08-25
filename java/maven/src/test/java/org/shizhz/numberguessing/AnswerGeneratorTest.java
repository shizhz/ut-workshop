package org.shizhz.numberguessing;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;

public class AnswerGeneratorTest {

    private AnswerGenerator answerGenerator;

    @Before
    public void setUp() throws Exception {
        answerGenerator = new AnswerGenerator();
    }

    @Test
    public void should_return_number_between_1234_and_9876() throws Exception {
        // given
        int min = 1234;
        int max = 9876;

        // when
        int result = answerGenerator.generateAnswer();

        // then
        assertThat(result, greaterThan(min));
        assertThat(result, lessThan(max));
    }

    @Test
    public void should_return_number_that_has_no_duplicate_digits() throws Exception {
        // given

        // when
        int result = answerGenerator.generateAnswer();

        Set<Character> digitSet = Sets.newHashSet(Lists.charactersOf(String.valueOf(result)));
        int expect = String.valueOf(result).length();

        // then
        assertThat(digitSet.size(), is(expect));
    }
}