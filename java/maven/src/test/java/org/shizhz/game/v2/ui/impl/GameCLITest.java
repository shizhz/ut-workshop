package org.shizhz.game.v2.ui.impl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.shizhz.game.v2.GameMode;
import org.shizhz.game.v2.domain.Guess;

import java.io.BufferedReader;
import java.io.PrintStream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class GameCLITest {

    @Mock
    private BufferedReader bufferedReader;
    @Mock
    private PrintStream printStream;
    private GameCLI gameCLI;
    private static final String MODE_MENU_MESSAGE = "Please choose game mode (1.Story, 2.Fight): ";

    // GameCLI gameCLI = new GameCLI(System.in, System.out);
    // #1. prepare for game start
    // gameCLI.gameStart();
    // Test case: none

    // #2. get next guess, should do all validation process here.
    // Guess guess = gameCLI.nextGuess(prompt);
    // TC #1: should print prompt message as expect
    // TC #2: should get a valid guess when call of this method is returned

    // #3. display message.
    // gameCLI.displayMessage(String message);

    // #3. work needs to be done when game is over
    // gameCLI.gameOver();


    @Before
    public void setUp() throws Exception {
        initMocks(this);

        given(bufferedReader.readLine()).willReturn("1234");

        gameCLI = new GameCLI(bufferedReader, printStream);
    }

    @Test
    public void should_print_prompt_message_when_try_to_get_next_guess() throws Exception {
        // given
        String prompt = "Prompt > ";

        // when
        gameCLI.nextGuess(prompt);

        // then
        verify(printStream, times(1)).print(prompt);
    }

    @Test
    public void should_get_valid_guess_when_try_to_get_next_guess() throws Exception {
        // given
        String prompt = "Prompt > ";

        // when
        Guess guess = gameCLI.nextGuess(prompt);

        // then
        InOrder inOrder = inOrder(printStream, bufferedReader);
        inOrder.verify(printStream, times(1)).print(prompt);
        inOrder.verify(bufferedReader, times(1)).readLine();
        inOrder.verifyNoMoreInteractions();

        assertThat(guess, is(Guess.of("1234")));
        assertThat(guess.isValid(), is(true));
    }

    @Test
    public void should_loop_to_read_input_until_valid_guess_is_obtained() throws Exception {
        // given
        String prompt = "Prompt > ";
        when(bufferedReader.readLine()).thenReturn("123", "adsf", "12ad4", "1234");

        // when
        Guess guess = gameCLI.nextGuess(prompt);

        // then
        InOrder inOrder = inOrder(bufferedReader, printStream);
        inOrder.verify(printStream).print(prompt);
        inOrder.verify(bufferedReader).readLine();
        inOrder.verify(printStream).print(prompt);
        inOrder.verify(bufferedReader).readLine();
        inOrder.verify(printStream).print(prompt);
        inOrder.verify(bufferedReader).readLine();
        inOrder.verify(printStream).print(prompt);
        inOrder.verify(bufferedReader).readLine();
        inOrder.verifyNoMoreInteractions();

        assertThat(guess, is(Guess.of("1234")));
        assertThat(guess.isValid(), is(true));
    }

    @Test
    public void should_display_message_correctly() throws Exception {
        // given
        String message = "Message";

        // when
        gameCLI.displayMessage(message);

        // then
        verify(printStream, times(1)).println(message);
    }

    @Test
    public void should_close_reader_and_writer_when_game_over() throws Exception {
        // given

        // when
        gameCLI.gameOver();

        // then
        verify(bufferedReader).close();

        InOrder inOrder = inOrder(printStream);
        inOrder.verify(printStream).flush();
        inOrder.verify(printStream).close();
        verify(bufferedReader).close();
    }

    @Test
    public void should_print_menu_message_when_ask_user_choose_game_mode() throws Exception {
        // given
        given(bufferedReader.readLine()).willReturn("1");

        // when
        gameCLI.chooseMode(MODE_MENU_MESSAGE);

        // then
        verify(printStream).print(MODE_MENU_MESSAGE);
    }

    @Test
    public void should_return_story_mode_when_user_input_1() throws Exception {
        // given
        given(bufferedReader.readLine()).willReturn("1");

        // when
        GameMode gameMode = gameCLI.chooseMode(MODE_MENU_MESSAGE);

        // then
        assertThat(gameMode, is(GameMode.STORY));
    }

    @Test
    public void should_return_flight_mode_when_user_input_2() throws Exception {
        // given
        given(bufferedReader.readLine()).willReturn("2");

        // when
        GameMode gameMode = gameCLI.chooseMode(MODE_MENU_MESSAGE);

        // then
        assertThat(gameMode, is(GameMode.FIGHT));
    }

    @Test
    public void should_loop_util_user_choose_right_game_mode() throws Exception {
        // given
        when(bufferedReader.readLine()).thenReturn("3", " 4", "abc", "1");

        // when
        GameMode gameMode = gameCLI.chooseMode(MODE_MENU_MESSAGE);

        // then
        InOrder inOrder = inOrder(bufferedReader, printStream);
        inOrder.verify(printStream).print(MODE_MENU_MESSAGE);
        inOrder.verify(bufferedReader).readLine();
        inOrder.verify(printStream).print(MODE_MENU_MESSAGE);
        inOrder.verify(bufferedReader).readLine();
        inOrder.verify(printStream).print(MODE_MENU_MESSAGE);
        inOrder.verify(bufferedReader).readLine();
        inOrder.verify(printStream).print(MODE_MENU_MESSAGE);
        inOrder.verify(bufferedReader).readLine();
        inOrder.verifyNoMoreInteractions();

        assertThat(gameMode, is(GameMode.STORY));
    }
}
