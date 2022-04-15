package org.jchess.control;

import java.util.Scanner;

import org.jchess.model.Board;
import org.jchess.view.UI;

/**
 * A simple example application that will start a game and let the user input moves for both sided
 */
public class GameManager
{
    private static Board board; // the board we are playing on

    /**
     * Initializes our game
     */
    public static void startGame()
    {
        UI.println("Starting game...");
        board = BoardManager.generateBoard();
        gameLoop();
    }

    /**
     * The core game loop
     */
    private static void gameLoop()
    {
        try (Scanner scanner = new Scanner(System.in))
        {
            // Print the current state of the board
            UI.printBoard(board);

            while (true)
            {
                String input;

                // Get the user input
                UI.print("Your input: ");
                input = scanner.nextLine();

                // Try to move a piece according to the user input
                try
                {
                    //BoardManager.movePiece(board, input);
                    BoardManager.movePieceUCI(board, input);
                }
                catch (Exception exception)
                {
                    UI.println(exception.getMessage());
                    continue;
                }

                // Show the new position of the board
                UI.printBoard(board);
            }
        }
    }
}
