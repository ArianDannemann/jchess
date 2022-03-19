package org.jchess;

import java.util.Scanner;

import org.jchess.control.BoardManager;
import org.jchess.exceptions.PieceNotFoundException;
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
        startGame();
    }

    public static void startGame()
    {
        Board board = BoardManager.generateBoard();
        Scanner scanner = new Scanner(System.in);

        UI.printBoard(board);
        UI.printBoardWithValidMoves(board, BoardManager.getPieceAtPosition(board, new Position("e1")));

        while (true)
        {
            String input;

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
