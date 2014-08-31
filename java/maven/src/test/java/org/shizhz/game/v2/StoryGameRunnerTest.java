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
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class StoryGameRunnerTest {
    private static final String WELCOME = "Welcome!";
    private static final String PROMPT_TEMP = "Please input your number(%s): ";
    private static final int MAX_TIMES = 6;
    public static final String CONGRATULATIONS = "Congratulations!";
    public static final String GAME_OVER = "Game Over";

    @Mock
    private BufferedReader bufferedReader;

    @Mock
    private PrintStream printStream;

    @Mock
    private AnswerGenerator answerGenerator;

    private Comparator comparator;

    private UIStrategy ui;

    private GameRunner storyGameRunner;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        given(answerGenerator.generateAnswer()).willReturn(Answer.of("1234"));

        ui = new GameCLI(bufferedReader, printStream);
        comparator = new Comparator();
        storyGameRunner = StoryGameRunner.newInstance().withUI(ui).withAnswerGenerator(answerGenerator).withNumberComparator(comparator);

    }

    @Test
    public void should_print_welcome_message_when_game_start_for_story_mode() throws Exception {
        // given
        given(bufferedReader.readLine()).willReturn("1234");

        // when
        storyGameRunner.run();

        // then
        verify(printStream).println(WELCOME);
    }

    @Test
    public void should_print_prompt_with_max_times_after_welcome_message() throws Exception {
        // given
        given(bufferedReader.readLine()).willReturn("1284");

        // when
        storyGameRunner.run();

        // then
        InOrder inOrder = inOrder(bufferedReader, printStream);
        inOrder.verify(printStream).println(WELCOME);
        inOrder.verify(printStream).print(String.format(PROMPT_TEMP, MAX_TIMES));
    }

    @Test
    public void should_print_guess_result_to_end_user_and_display_next_prompt() throws Exception {
        // given
        given(bufferedReader.readLine()).willReturn("1245");

        // when
        storyGameRunner.run();

        // then
        InOrder inOrder = inOrder(bufferedReader, printStream);
        inOrder.verify(printStream).println(WELCOME);
        inOrder.verify(printStream).print(String.format(PROMPT_TEMP, MAX_TIMES));
        inOrder.verify(bufferedReader).readLine();
        inOrder.verify(printStream).println("2A1B");
        inOrder.verify(printStream).print(String.format(PROMPT_TEMP, MAX_TIMES - 1));
    }

    @Test
    public void should_terminate_game_at_the_time_when_guess_correctly_and_print_congratulations_massage() throws Exception {
        // given
        when(bufferedReader.readLine()).thenReturn("5678", "1245", "1243", "1234");

        // when
        storyGameRunner.run();

        // then
        InOrder inOrder = inOrder(bufferedReader, printStream);
        inOrder.verify(printStream).println(WELCOME);
        inOrder.verify(printStream).print(String.format(PROMPT_TEMP, MAX_TIMES));
        inOrder.verify(bufferedReader).readLine();
        inOrder.verify(printStream).println("0A0B");
        inOrder.verify(printStream).print(String.format(PROMPT_TEMP, MAX_TIMES - 1));
        inOrder.verify(bufferedReader).readLine();
        inOrder.verify(printStream).println("2A1B");
        inOrder.verify(printStream).print(String.format(PROMPT_TEMP, MAX_TIMES - 2));
        inOrder.verify(bufferedReader).readLine();
        inOrder.verify(printStream).println("2A2B");
        inOrder.verify(printStream).print(String.format(PROMPT_TEMP, MAX_TIMES - 3));
        inOrder.verify(bufferedReader).readLine();
        inOrder.verify(printStream).println(CONGRATULATIONS);
        inOrder.verify(printStream, never()).println("4A0B");
        inOrder.verify(bufferedReader, never()).readLine();
        inOrder.verify(printStream, never()).println(GAME_OVER);
        inOrder.verify(printStream, never()).print(String.format(PROMPT_TEMP, MAX_TIMES - 4));
    }

    @Test
    public void should_print_game_over_if_chances_run_out() throws Exception {
        // given
        when(bufferedReader.readLine()).thenReturn("5678", "1245", "1243", "4321", "1245", "2341");

        // when
        storyGameRunner.run();

        // then
        InOrder inOrder = inOrder(bufferedReader, printStream);
        inOrder.verify(printStream).println(WELCOME);
        inOrder.verify(printStream).print(String.format(PROMPT_TEMP, MAX_TIMES));
        inOrder.verify(bufferedReader).readLine();
        inOrder.verify(printStream).println("0A0B");
        inOrder.verify(printStream).print(String.format(PROMPT_TEMP, MAX_TIMES - 1));
        inOrder.verify(bufferedReader).readLine();
        inOrder.verify(printStream).println("2A1B");
        inOrder.verify(printStream).print(String.format(PROMPT_TEMP, MAX_TIMES - 2));
        inOrder.verify(bufferedReader).readLine();
        inOrder.verify(printStream).println("2A2B");
        inOrder.verify(printStream).print(String.format(PROMPT_TEMP, MAX_TIMES - 3));
        inOrder.verify(bufferedReader).readLine();
        inOrder.verify(printStream).println("0A4B");
        inOrder.verify(printStream).print(String.format(PROMPT_TEMP, MAX_TIMES - 4));
        inOrder.verify(bufferedReader).readLine();
        inOrder.verify(printStream).println("2A1B");
        inOrder.verify(printStream).print(String.format(PROMPT_TEMP, MAX_TIMES - 5));
        inOrder.verify(bufferedReader).readLine();
        inOrder.verify(printStream).println("0A4B");
        inOrder.verify(printStream).println(GAME_OVER);

        inOrder.verify(bufferedReader, never()).readLine();
        inOrder.verify(printStream, never()).print(String.format(PROMPT_TEMP, MAX_TIMES - 6));
    }

    @Test
    public void should_support_story_mode() throws Exception {
        // given

        // when
        boolean supportStoryMode = storyGameRunner.supportMode(GameMode.STORY);
        boolean supportFightMode = storyGameRunner.supportMode(GameMode.FIGHT);

        // then
        assertThat(supportStoryMode, is(true));
        assertThat(supportFightMode, is(false));
    }
}
