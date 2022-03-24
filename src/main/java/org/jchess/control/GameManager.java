package org.jchess.control;

import java.util.Scanner;

import org.jchess.exceptions.PieceNotFoundException;
import org.jchess.model.Board;
import org.jchess.view.UI;

public class GameManager
{
    private static Board board;

    public static void startGame()
    {
        UI.println("Starting game...");
        board = BoardManager.generateBoard();
        gameLoop();
    }

    private static void gameLoop()
    {
        try (Scanner scanner = new Scanner(System.in))
        {
            UI.printBoard(board);

            while (true)
            {
                String input;

                UI.print("Your input: ");
                input = scanner.nextLine();

                try
                {
                    BoardManager.movePiece(board, input);
                }
                catch (PieceNotFoundException exception)
                {
                    UI.println("There is no piece at the specified position!");
                    continue;
                }

                UI.printBoard(board);
            }
        }
    }
}
