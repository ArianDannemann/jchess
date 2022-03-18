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
        Board board = BoardManager.generateBoard("rk2k1kr/8/8/8/8/8/8/8 w - - 0 1");

        //BoardManager.movePiece(board, new Position("a1"), new Position("a5"));

        UI.printBoard(board);
        UI.printBoardWithValidMoves(board, BoardManager.getPieceAtPosition(board, new Position("e8")));
    }
}
