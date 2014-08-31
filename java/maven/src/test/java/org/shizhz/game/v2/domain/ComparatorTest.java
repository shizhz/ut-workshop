package org.shizhz.game.v2.domain;

import com.google.common.collect.ImmutableMap;
import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.util.collections.Sets;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ComparatorTest {

    private Comparator comparator;

    @Before
    public void setUp() throws Exception {
        comparator = new Comparator();
    }

    @Test
    public void should_return_comparing_result_with_4_precise_match_and_0_fuzzy_match_if_compare_1234_and_1234() throws Exception {
        // given
        Answer answer = Answer.of("1234");
        Guess guess = Guess.of("1234");

        // when
        ComparingResult comparingResult = comparator.compare(answer, guess);

        // then
        ComparingResult expect = ComparingResult.of(ImmutableMap.<Integer, Character>of(
                0, '1',
                1, '2',
                2, '3',
                3, '4'), null);

        assertThat(comparingResult, is(expect));
    }

    @Test
    public void should_return_comparing_result_with_0_precise_match_and_0_fuzzy_match_if_compare_1234_and_5678() throws Exception {
        // given
        Answer answer = Answer.of("1234");
        Guess guess = Guess.of("5678");

        // when
        ComparingResult comparingResult = comparator.compare(answer, guess);

        // then
        ComparingResult expect = ComparingResult.of(null, null);
        assertThat(comparingResult, is(expect));
    }

    @Test
    public void should_return_comparing_result_with_0_precise_match_and_4_fuzzy_match_if_compare_1234_and_4321() throws Exception {
        // given
        Answer answer = Answer.of("1234");
        Guess guess = Guess.of("4321");

        // when
        ComparingResult comparingResult = comparator.compare(answer, guess);

        // then
        ComparingResult expect = ComparingResult.of(null, Sets.<Character>newSet('1', '2', '3', '4'));
        assertThat(comparingResult, is(expect));
    }

    @Test
    public void should_return_comparing_result_with_2_precise_match_and_1_fuzzy_match_if_compare_1234_and_1435() throws Exception {
        // given
        Answer answer = Answer.of("1234");
        Guess guess = Guess.of("1435");

        // when
        ComparingResult comparingResult = comparator.compare(answer, guess);

        // then
        ComparingResult expect = ComparingResult.of(ImmutableMap.<Integer, Character>of(
                0, '1',
                2, '3'), Sets.<Character>newSet('4'));
        assertThat(comparingResult, is(expect));
    }
}