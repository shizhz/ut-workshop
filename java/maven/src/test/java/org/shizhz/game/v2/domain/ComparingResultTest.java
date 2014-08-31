package org.shizhz.game.v2.domain;

import com.google.common.collect.ImmutableMap;
import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.util.collections.Sets;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class ComparingResultTest {

    private ComparingResult comparingResult1;
    private ComparingResult comparingResult2;

    @Before
    public void setUp() throws Exception {
        comparingResult1 = ComparingResult.of(ImmutableMap.<Integer, Character>of(
                0, '1',
                1, '2',
                2, '3',
                3, '4' ), null);
        comparingResult2 = ComparingResult.of(ImmutableMap.<Integer, Character>of(
                        0, '1',
                        3, '3'), Sets.<Character>newSet('4'));
    }

    @Test
    public void should_construct_comparing_result_correctly() throws Exception {
        // given

        // when

        // then
        assertThat(comparingResult1.numberOfPreciseMatch(), is(4));
        assertThat(comparingResult1.numberOfFuzzyMatch(), is(0));

        assertThat(comparingResult1.fuzzyMatches('1'), is(false));
        assertThat(comparingResult1.fuzzyMatches('2'), is(false));
        assertThat(comparingResult1.fuzzyMatches('3'), is(false));
        assertThat(comparingResult1.fuzzyMatches('4'), is(false));

        assertThat(comparingResult1.preciseMatchesAt(0), is(true));
        assertThat(comparingResult1.preciseMatchesAt(1), is(true));
        assertThat(comparingResult1.preciseMatchesAt(2), is(true));
        assertThat(comparingResult1.preciseMatchesAt(3), is(true));

        assertThat(comparingResult1.preciseMatchedCharAt(0), is('1'));
        assertThat(comparingResult1.preciseMatchedCharAt(1), is('2'));
        assertThat(comparingResult1.preciseMatchedCharAt(2), is('3'));
        assertThat(comparingResult1.preciseMatchedCharAt(3), is('4'));

        assertThat(comparingResult2.numberOfFuzzyMatch(), is(1));
        assertThat(comparingResult2.numberOfPreciseMatch(), is(2));

        assertThat(comparingResult2.fuzzyMatches('1'), is(false));
        assertThat(comparingResult2.fuzzyMatches('3'), is(false));
        assertThat(comparingResult2.fuzzyMatches('4'), is(true));

        assertThat(comparingResult2.preciseMatchesAt(0), is(true));
        assertThat(comparingResult2.preciseMatchesAt(1), is(false));
        assertThat(comparingResult2.preciseMatchesAt(2), is(false));
        assertThat(comparingResult2.preciseMatchesAt(3), is(true));

        assertThat(comparingResult2.preciseMatchedCharAt(0), is('1'));
        assertThat(comparingResult2.preciseMatchedCharAt(1), is(nullValue()));
        assertThat(comparingResult2.preciseMatchedCharAt(2), is(nullValue()));
        assertThat(comparingResult2.preciseMatchedCharAt(3), is('3'));
    }

    @Test
    public void should_present_as_correct_string() throws Exception {
        // given

        // when
        String cr1String = comparingResult1.toString();
        String cr2String = comparingResult2.toString();

        // then
        assertThat(cr1String, is("4A0B"));
        assertThat(cr2String, is("2A1B"));
    }

    @Test
    public void should_return_correct_precise_matched_char_by_index() throws Exception {
        // given
        int index = 1;

        //when
        Character character1 = comparingResult1.preciseMatchedCharAt(index);
        Character character2 = comparingResult2.preciseMatchedCharAt(index);

        // then
        assertThat(character1, is('2'));
        assertThat(character2, is(nullValue()));
    }

    @Test
    public void should_return_0_for_precise_matched_number_and_fuzzy_matched_number_if_construct_comparing_result_with_null() throws Exception {
        // given
        ComparingResult comparingResult = ComparingResult.of(null, null);

        // when
        int preciseMatchedNumber = comparingResult.numberOfPreciseMatch();
        int fuzzyMatchedNumber = comparingResult.numberOfFuzzyMatch();

        // then
        assertThat(preciseMatchedNumber, is(0));
        assertThat(fuzzyMatchedNumber, is(0));
    }

    @Test
    public void should_two_comparing_results_equal_if_has_same_precise_match_and_fuzzy_match() throws Exception {
        // given
        ComparingResult comparingResult3 = ComparingResult.of(ImmutableMap.<Integer, Character>of(
                        0, '1',
                        3, '3'), Sets.<Character>newSet('4'));

        // when
        boolean equals = comparingResult2.equals(comparingResult3);

        // then
        assertThat(equals, is(true));
    }

    @Test
    public void should_two_comparing_results_not_equal_if_precise_match_is_same_but_fuzzy_match_is_not() throws Exception {
        // given
        ComparingResult comparingResult3 = ComparingResult.of(ImmutableMap.<Integer, Character>of(
                        1, '1',
                        3, '3'), Sets.<Character>newSet('5'));


        // when
        boolean equals = comparingResult2.equals(comparingResult3);

        // then
        assertThat(equals, is(false));
    }

    @Test
    public void should_two_comparing_results_not_equal_if_fuzzy_match_is_same_but_precise_match_is_not() throws Exception {
        // given
        ComparingResult comparingResult3 = ComparingResult.of(ImmutableMap.<Integer, Character>of(
                        1, '1',
                        2, '3'), Sets.<Character>newSet('4'));

        // when
        boolean equals = comparingResult2.equals(comparingResult3);

        // then
        assertThat(equals, is(false));
    }

    @Test
    public void should_return_false_if_compere_one_comparing_result_to_null() throws Exception {
        // given
        ComparingResult nullValue = null;

        // when
        boolean equals = comparingResult2.equals(nullValue);

        // then
        assertThat(equals, is(false));
    }

    @Test
    public void should_return_false_if_compare_one_comparing_result_to_object_with_other_type() throws Exception {
        // given
        Object obj = new Object();

        // when
        boolean equals = comparingResult2.equals(obj);

        // then
        assertThat(equals, is(false));
    }
}