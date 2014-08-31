package org.shizhz.game.v1;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.io.BufferedReader;
import java.io.PrintStream;

public class GuessCLITest {

    private GuessCLI guessCLI;

    @Mock
    private BufferedReader reader;

    @Mock
    private PrintStream writer;

    @Before
    public void setUp() throws Exception {
        guessCLI = new GuessCLI(System.in, System.out);
    }

    @Test
    public void should_return_input_as_int() throws Exception {

    }
}