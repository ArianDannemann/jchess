package org.jchess;

import org.jchess.control.BoardManager;
import org.jchess.model.Board;
import org.jchess.view.UI;

/**
 * This class is used to show a simple example of the JChess libary
 *
 * @author Arian Dannemann
 * @version 0.1
 */
public class App
{
    public static void main (String[] args)
    {
        Board board = BoardManager.generateBoard();
        UI.printBoard(board);

        // This is a test
    }
}
