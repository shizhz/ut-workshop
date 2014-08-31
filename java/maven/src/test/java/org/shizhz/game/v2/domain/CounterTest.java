package org.shizhz.game.v2.domain;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CounterTest {

    private Counter counter;

    @Before
    public void setUp() throws Exception {
        counter = Counter.of(4);
    }

    @Test
    public void should_reduce_times_correctly() throws Exception {
        // given

        // when
        counter.reduce(2);

        // then
        assertThat(counter.times(), is(2));
    }

    @Test
    public void should_return_false_if_no_times_left() throws Exception {
        // given

        // when
        counter.reduce(4);

        // then
        assertThat(counter.hasChanceLeft(), is(false));
    }

    @Test
    public void should_return_true_if_still_has_times_left() throws Exception {
        // given

        // when
        counter.reduce(3);

        // then
        assertThat(counter.hasChanceLeft(), is(true));
    }
}