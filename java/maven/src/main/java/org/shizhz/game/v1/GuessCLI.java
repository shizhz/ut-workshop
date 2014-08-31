package org.shizhz.game.v1;

import java.io.*;

/**
 * Created by zzshi on 8/27/14.
 */
public class GuessCLI {
    private final BufferedReader reader;
    private final BufferedWriter writer;

    public GuessCLI(InputStream is, OutputStream os) {
        this.reader = new BufferedReader(new InputStreamReader(is));
        this.writer = new BufferedWriter(new OutputStreamWriter(os));
    }

    public int readLine() throws IOException {
        return Integer.valueOf(this.reader.readLine());
    }
}
