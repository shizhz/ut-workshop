package org.shizhz.game.v2;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ValidationUtilsTest {

    @Test
    public void should_return_false_if_input_length_is_valid_but_has_duplicate_digits() throws Exception {
        // given
        String invalidNumber = "1233";

        // when
        boolean isValid = ValidationUtils.isValid(invalidNumber);

        // then
        assertThat(isValid, is(false));

    }

    @Test
    public void should_return_false_if_input_has_no_duplicate_digits_but_length_is_invalid() throws Exception {
        // given
        String invalidNumber = "123";

        // when
        boolean isValid = ValidationUtils.isValid(invalidNumber);

        // then
        assertThat(isValid, is(false));
    }


    @Test
    public void should_return_false_if_input_cannot_convert_to_a_number() throws Exception {
        // given
        String nan = "a123";

        // when
        boolean isValid = ValidationUtils.isValid(nan);

        // then
        assertThat(isValid, is(false));
    }

    @Test
    public void should_return_false_if_input_is_null() throws Exception {
        // given
        String nullValue = null;

        // when
        boolean isValid = ValidationUtils.isValid(nullValue);

        // then
        assertThat(isValid, is(false));
    }

    @Test
    public void should_return_false_if_input_is_empty_string() throws Exception {
        // given
        String empty = "";

        // when
        boolean isValid = ValidationUtils.isValid(empty);

        // then
        assertThat(isValid, is(false));
    }

    @Test
    public void should_return_true_if_input_has_no_duplicate_digit_and_length_is_valid() throws Exception {
        // given
        String validNumber = "1234";

        // when
        boolean isValid = ValidationUtils.isValid(validNumber);

        // then
        assertThat(isValid, is(true));
    }
}