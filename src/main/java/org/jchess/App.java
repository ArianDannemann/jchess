package org.jchess;

import org.jchess.control.BoardManager;
import org.jchess.model.Board;
import org.jchess.model.Position;
import org.jchess.view.UI;

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
        Board board = BoardManager.generateBoard();

        BoardManager.movePiece(board, new Position("e2"), new Position("e4"));

        UI.printBoard(board);
    }
}
