package org.jchess;

import java.util.Scanner;

import org.jchess.control.BoardManager;
import org.jchess.exceptions.PieceNotFoundException;
import org.jchess.model.Board;
import org.jchess.model.Color;
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
        startGame();
    }

    public static void startGame()
    {
        Board board = BoardManager.generateBoard("1k6/p7/8/8/8/8/8/KR6 b - - 0 1");
        Scanner scanner = new Scanner(System.in);

        UI.printBoard(board);
        //UI.printBoardWithValidMoves(board, BoardManager.getPieceAtPosition(board, new Position("e1")));

        while (true)
        {
            String input;

            System.out.println("Is black in check? " + BoardManager.isSideInCheck(board, Color.BLACK));

            System.out.print("Your input: ");
            input = scanner.nextLine();

            try
            {
                BoardManager.movePiece(board, input);
            }
            catch (PieceNotFoundException exception)
            {
                System.out.println("There is no piece at the specified position!");
                continue;
            }

            UI.printBoard(board);
        }
    }
}
