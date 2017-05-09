/*
 * Code used in the "Software Engineering" course.
 *
 * Copyright 2017 by Claudio Cusano (claudio.cusano@unipv.it)
 * Dept of Electrical, Computer and Biomedical Engineering,
 * University of Pavia.
 */
package net;

/**
 *
 * @author Elisa
 */
import hangman.Player;
import hangman.Game;
import hangman.Hangman;
import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Manage a player playing with the terminal.
 *
 * @author Claudio Cusano <claudio.cusano@unipv.it>
 */
public class RemotePlayer extends Player {

    PrintWriter out;
    BufferedReader in;
    Socket socket;
    Hangman hangman;

    public RemotePlayer(Socket socket, Hangman hangman) throws IOException {
        this.hangman = hangman;
        this.socket = socket;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream(), true);
    }

    @Override
    public void run() {
        try {
            hangman.playGame(this);
        } catch (IOException ex) {
            Logger.getLogger(RemotePlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(Game game) {
        switch (game.getResult()) {
            case FAILED:
                out.println(game.getResult().toString() + ";" + game.getSecretWord());
                break;
            case SOLVED:
                out.println(game.getResult().toString() + ";" + game.getSecretWord());
                break;
            case OPEN:
                int rem = Game.MAX_FAILED_ATTEMPTS - game.countFailedAttempts();
                out.println(game.getResult().toString() + ";" + rem + ";" + game.countFailedAttempts() + ";" + game.getKnownLetters());

                break;
        }
    }

    /**
     * Ask the user to guess a letter.
     *
     * @param game
     * @return
     */
    @Override
    public char chooseLetter(Game game) throws IOException {

        String line = in.readLine();
        return line.charAt(0);

    }
}
