package org.shizhz.game.v2;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.shizhz.game.v2.domain.AnswerGenerator;
import org.shizhz.game.v2.domain.Comparator;
import org.shizhz.game.v2.ui.UIStrategy;
import org.shizhz.game.v2.ui.impl.GameCLI;

import java.io.BufferedReader;
import java.io.PrintStream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class GameTest {
    public static final String MODE_MENU = "Please choose game mode (1.Story, 2.Fight): ";

    @Mock
    private BufferedReader bufferedReader;

    @Mock
    private PrintStream printStream;

    @Mock
    private AnswerGenerator answerGenerator;

    @Mock
    private StoryGameRunner storyGameRunner;

    @Mock
    private FightGameRunner fightGameRunner;

    private Comparator comparator;

    private UIStrategy ui;

    private Game game;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        ui = new GameCLI(bufferedReader, printStream);
        comparator = new Comparator();

        given(storyGameRunner.supportMode(GameMode.STORY)).willReturn(true);
        given(storyGameRunner.withAnswerGenerator(answerGenerator)).willReturn(storyGameRunner);
        given(storyGameRunner.withNumberComparator(comparator)).willReturn(storyGameRunner);
        given(storyGameRunner.withUI(ui)).willReturn(storyGameRunner);

        given(fightGameRunner.supportMode(GameMode.FIGHT)).willReturn(true);
        given(fightGameRunner.withUI(ui)).willReturn(fightGameRunner);
        given(fightGameRunner.withNumberComparator(comparator)).willReturn(fightGameRunner);
        given(fightGameRunner.withAnswerGenerator(answerGenerator)).willReturn(fightGameRunner);

        game = Game.newInstance().withUI(ui).withAnswerGenerator(answerGenerator).withNumberComparator(comparator);
        game.setGameRunners(new GameRunner[] {storyGameRunner, fightGameRunner});
    }

    @Test
    public void should_print_mode_choosing_message_when_game_start() throws Exception {
        // given
        given(bufferedReader.readLine()).willReturn("1");
        
        // when
        game.start();
        
        // then
        verify(printStream).print(MODE_MENU);
    }

    @Test
    public void should_game_enter_story_mode_if_user_input_1_when_asked_to_choose_mode() throws Exception {
        // given
        given(bufferedReader.readLine()).willReturn("1");

        // when
        game.start();

        // then
        assertThat(game.mode(), is(GameMode.STORY));
    }

    @Test
    public void should_game_enter_flight_mode_if_user_input_2_when_asked_to_choose_mode() throws Exception {
        // given
        given(bufferedReader.readLine()).willReturn("2");

        // when
        game.start();

        // then
        assertThat(game.mode(), is(GameMode.FIGHT));
    }

    @Test
    public void should_run_story_game_runner_if_enter_story_mode() throws Exception {
        // given
        given(bufferedReader.readLine()).willReturn("1");

        // when
        game.start();

        // then
        verify(storyGameRunner).withNumberComparator(comparator);
        verify(storyGameRunner).withAnswerGenerator(answerGenerator);
        verify(storyGameRunner).withUI(ui);
        verify(storyGameRunner, times(1)).run();
    }

    @Test
    public void should_run_fight_game_runner_if_enter_fight_mode() throws Exception {
        // given
        given(bufferedReader.readLine()).willReturn("2");

        // when
        game.start();

        // then
        verify(fightGameRunner).withNumberComparator(comparator);
        verify(fightGameRunner).withAnswerGenerator(answerGenerator);
        verify(fightGameRunner).withUI(ui);
        verify(fightGameRunner, times(1)).run();
    }
}