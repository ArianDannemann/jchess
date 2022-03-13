package org.jchess;

import org.jchess.control.BoardManager;
import org.jchess.model.Board;
import org.jchess.model.Color;
import org.jchess.model.Piece;
import org.jchess.model.PieceType;
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
        Board board = new Board();
        Piece testPiece = new Piece(new Position(2, 6), PieceType.PAWN, Color.BLACK);

        BoardManager.addPiece(board, testPiece);
        BoardManager.addPiece(board, new Position(1, 5), PieceType.PAWN, Color.WHITE);
        //BoardManager.addPiece(board, new Position(0, 1), PieceType.PAWN, Color.WHITE);

        UI.printBoard(board);
        UI.printBoardWithValidMoves(board, testPiece);
    }
}
