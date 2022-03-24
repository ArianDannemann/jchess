package org.jchess;

import org.jchess.control.GameManager;

/**
 * This class is used to show a simple example of the JChess libary
 *
 * @author Arian Dannemann
 * @version 0.2
 */
public class App
{
    public static void main(String[] args)
    {
        startGame();
    }

    public static void startGame()
    {
        GameManager.startGame();
    }
}
