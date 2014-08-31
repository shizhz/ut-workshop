package org.shizhz.game.v2;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.shizhz.game.v2.domain.Answer;
import org.shizhz.game.v2.domain.AnswerGenerator;
import org.shizhz.game.v2.domain.Comparator;
import org.shizhz.game.v2.ui.UIStrategy;
import org.shizhz.game.v2.ui.impl.GameCLI;

import java.io.BufferedReader;
import java.io.PrintStream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.never;
import static org.mockito.MockitoAnnotations.initMocks;

public class FightGameRunnerTest {
    private static final String PROMPT_TEMP = "Please input a number(%s) ,%s:";

    private static final String WIN_BY_BEAT_COMPETITOR_TEMP = "Congratulation, %s.";

    private static final String WIN_BY_CORRECT_GUESS_TEMP = "You got it. Congratulation，%s!";
    @Mock
    private BufferedReader bufferedReader;

    @Mock
    private PrintStream printStream;

    @Mock
    private AnswerGenerator answerGenerator;

    private Comparator comparator;

    private UIStrategy ui;

    private GameRunner fightGameRunner;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        given(answerGenerator.generateAnswer()).willReturn(Answer.of("1234"));

        ui = new GameCLI(bufferedReader, printStream);
        comparator = new Comparator();
        fightGameRunner = FightGameRunner.newInstance().withUI(ui).withAnswerGenerator(answerGenerator).withNumberComparator(comparator);
    }

    @Test
    public void should_begin_fight_round_from_userA_and_then_userB_with_correct_prompt() throws Exception {
        // given
        given(bufferedReader.readLine()).willReturn("8765", "4356");

        // when
        fightGameRunner.run();

        // then
        InOrder inOrder = inOrder(bufferedReader, printStream);

        inOrder.verify(printStream).print(String.format(PROMPT_TEMP, 6, "UserA"));
        inOrder.verify(bufferedReader).readLine();
        inOrder.verify(printStream).print(String.format(PROMPT_TEMP, 6, "UserB"));
    }

    @Test
    public void should_userB_win_with_correct_prompt_and_message_when_userA_runs_out_all_of_chances() throws Exception {
        // given
        given(bufferedReader.readLine()).willReturn("1567", "1435", "1639", "9876", "8765", "1239", "1235");

        // when
        fightGameRunner.run();

        // then
        InOrder inOrder = inOrder(bufferedReader, printStream);

        inOrder.verify(printStream).print(String.format(PROMPT_TEMP, 6, "UserA"));
        inOrder.verify(bufferedReader).readLine();
        inOrder.verify(printStream).print(String.format(PROMPT_TEMP, 5, "UserB"));
        inOrder.verify(bufferedReader).readLine();
        inOrder.verify(printStream).print(String.format(PROMPT_TEMP, 4, "UserA"));
        inOrder.verify(bufferedReader).readLine();
        inOrder.verify(printStream).print(String.format(PROMPT_TEMP, 4, "UserB"));
        inOrder.verify(bufferedReader).readLine();
        inOrder.verify(printStream).print(String.format(PROMPT_TEMP, 3, "UserA"));
        inOrder.verify(bufferedReader).readLine();
        inOrder.verify(printStream).print(String.format(PROMPT_TEMP, 3, "UserB"));
        inOrder.verify(bufferedReader).readLine();
        inOrder.verify(printStream).print(String.format(PROMPT_TEMP, 1, "UserA"));
        inOrder.verify(bufferedReader).readLine();
        inOrder.verify(printStream).println(String.format(WIN_BY_BEAT_COMPETITOR_TEMP, "UserB"));
        inOrder.verify(bufferedReader, never()).readLine();
    }

    @Test
    public void should_userA_win_with_correct_prompt_and_message_when_userB_runs_out_all_of_chances() throws Exception {
        // given
        given(bufferedReader.readLine()).willReturn("1567", "1435", "1634", "9876", "1256", "9876");

        // when
        fightGameRunner.run();

        // then
        InOrder inOrder = inOrder(bufferedReader, printStream);

        inOrder.verify(printStream).print(String.format(PROMPT_TEMP, 6, "UserA"));
        inOrder.verify(bufferedReader).readLine();
        inOrder.verify(printStream).print(String.format(PROMPT_TEMP, 5, "UserB"));
        inOrder.verify(bufferedReader).readLine();
        inOrder.verify(printStream).print(String.format(PROMPT_TEMP, 4, "UserA"));
        inOrder.verify(bufferedReader).readLine();
        inOrder.verify(printStream).print(String.format(PROMPT_TEMP, 3, "UserB"));
        inOrder.verify(bufferedReader).readLine();
        inOrder.verify(printStream).print(String.format(PROMPT_TEMP, 3, "UserA"));
        inOrder.verify(bufferedReader).readLine();
        inOrder.verify(printStream).print(String.format(PROMPT_TEMP, 1, "UserB"));
        inOrder.verify(bufferedReader).readLine();
        inOrder.verify(printStream).println(String.format(WIN_BY_BEAT_COMPETITOR_TEMP, "UserA"));
        inOrder.verify(bufferedReader, never()).readLine();
    }

    @Test
    public void should_userA_win_with_correct_prompt_and_message_if_userA_make_a_perfect_guess() throws Exception {
        // given
        given(bufferedReader.readLine()).willReturn("1567", "1435", "1634", "9876", "1234");

        // when
        fightGameRunner.run();

        // then
        InOrder inOrder = inOrder(bufferedReader, printStream);

        inOrder.verify(printStream).print(String.format(PROMPT_TEMP, 6, "UserA"));
        inOrder.verify(bufferedReader).readLine();
        inOrder.verify(printStream).print(String.format(PROMPT_TEMP, 5, "UserB"));
        inOrder.verify(bufferedReader).readLine();
        inOrder.verify(printStream).print(String.format(PROMPT_TEMP, 4, "UserA"));
        inOrder.verify(bufferedReader).readLine();
        inOrder.verify(printStream).print(String.format(PROMPT_TEMP, 3, "UserB"));
        inOrder.verify(bufferedReader).readLine();
        inOrder.verify(printStream).print(String.format(PROMPT_TEMP, 3, "UserA"));
        inOrder.verify(bufferedReader).readLine();
        inOrder.verify(printStream).println(String.format(WIN_BY_CORRECT_GUESS_TEMP, "UserA"));
        inOrder.verify(bufferedReader, never()).readLine();
    }

    @Test
    public void should_userB_win_with_correct_prompt_and_message_if_userB_make_a_perfect_guess() throws Exception {
        // given
        given(bufferedReader.readLine()).willReturn("1567", "1435", "1634", "1234");

        // when
        fightGameRunner.run();

        // then
        InOrder inOrder = inOrder(bufferedReader, printStream);

        inOrder.verify(printStream).print(String.format(PROMPT_TEMP, 6, "UserA"));
        inOrder.verify(bufferedReader).readLine();
        inOrder.verify(printStream).print(String.format(PROMPT_TEMP, 5, "UserB"));
        inOrder.verify(bufferedReader).readLine();
        inOrder.verify(printStream).print(String.format(PROMPT_TEMP, 4, "UserA"));
        inOrder.verify(bufferedReader).readLine();
        inOrder.verify(printStream).print(String.format(PROMPT_TEMP, 3, "UserB"));
        inOrder.verify(printStream).println(String.format(WIN_BY_CORRECT_GUESS_TEMP, "UserB"));
        inOrder.verify(bufferedReader, never()).readLine();
    }

    @Test
    public void should_userB_win_with_correct_prompt_and_message_if_two_users_missed_all_guesses() throws Exception {
        // given
        given(bufferedReader.readLine()).willReturn("9876");

        // when
        fightGameRunner.run();

        // then
        InOrder inOrder = inOrder(bufferedReader, printStream);

        inOrder.verify(printStream).print(String.format(PROMPT_TEMP, 6, "UserA"));
        inOrder.verify(bufferedReader).readLine();
        inOrder.verify(printStream).print(String.format(PROMPT_TEMP, 6, "UserB"));
        inOrder.verify(bufferedReader).readLine();
        inOrder.verify(printStream).print(String.format(PROMPT_TEMP, 5, "UserA"));
        inOrder.verify(bufferedReader).readLine();
        inOrder.verify(printStream).print(String.format(PROMPT_TEMP, 5, "UserB"));
        inOrder.verify(bufferedReader).readLine();
        inOrder.verify(printStream).print(String.format(PROMPT_TEMP, 4, "UserA"));
        inOrder.verify(bufferedReader).readLine();
        inOrder.verify(printStream).print(String.format(PROMPT_TEMP, 4, "UserB"));
        inOrder.verify(bufferedReader).readLine();
        inOrder.verify(printStream).print(String.format(PROMPT_TEMP, 3, "UserA"));
        inOrder.verify(bufferedReader).readLine();
        inOrder.verify(printStream).print(String.format(PROMPT_TEMP, 3, "UserB"));
        inOrder.verify(bufferedReader).readLine();
        inOrder.verify(printStream).print(String.format(PROMPT_TEMP, 2, "UserA"));
        inOrder.verify(bufferedReader).readLine();
        inOrder.verify(printStream).print(String.format(PROMPT_TEMP, 2, "UserB"));
        inOrder.verify(bufferedReader).readLine();
        inOrder.verify(printStream).print(String.format(PROMPT_TEMP, 1, "UserA"));
        inOrder.verify(bufferedReader).readLine();
        inOrder.verify(printStream).println(String.format(WIN_BY_BEAT_COMPETITOR_TEMP, "UserB"));
        inOrder.verify(bufferedReader, never()).readLine();
    }

    @Test
    public void should_support_fight_mode() throws Exception {
        // given

        // when
        boolean supportFightMode = fightGameRunner.supportMode(GameMode.FIGHT);

        // then
        assertThat(supportFightMode, is(true));
    }
}