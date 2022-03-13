package org.jchess;

import org.jchess.control.BoardManager;
import org.jchess.model.Board;
import org.jchess.model.Position;
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
        Board board2 = BoardManager.copyBoard(board);
        BoardManager.movePiece(board2, new Position(4, 0), new Position(4, 4));
        BoardManager.removePiece(board, new Position(0, 0));
        UI.printBoard(board);
        UI.printBoard(board2);
    }
}
