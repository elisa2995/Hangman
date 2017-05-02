/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net;

import hangman.Hangman;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Claudio Cusano <claudio.cusano@unipv.it>
 */
public class HangmanServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        Hangman game = new Hangman();
        int port=8888;
        ServerSocket serverSocket=new ServerSocket(port);
        Socket socket=serverSocket.accept();
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
        RemotePlayer player=new RemotePlayer(out,in);       
        
        game.playGame(player);
    }
    
}
