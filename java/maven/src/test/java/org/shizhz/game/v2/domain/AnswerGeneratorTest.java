package org.shizhz.game.v2.domain;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by zzshi on 8/30/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class AnswerGeneratorTest {


    private AnswerGenerator answerGenerator;

    @Before
    public void setUp() throws Exception {
        answerGenerator = AnswerGenerator.newInstance();
    }

    @Test
    @Repeat(value = 1000)
    public void should_generate_valid_answer_each_time() throws Exception {
        // given

        // when
        Answer answer = answerGenerator.generateAnswer();

        // then
        assertThat(answer.isValid(), is(true));
    }
}
