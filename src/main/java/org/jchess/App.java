package org.jchess;

import java.util.Scanner;

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
        Board board = BoardManager.generateBoard("r1bqkbnr/pp1p1ppp/2n1p3/2p5/3PP3/2N5/PPP1NPPP/R1BQKB1R b KQkq - 0 4");
        Scanner scanner = new Scanner(System.in);

        UI.printBoard(board);

        while (true)
        {
            String input;

            System.out.print("Your input: ");
            input = scanner.nextLine();

            BoardManager.movePiece(board, input);
            UI.printBoard(board);
        }
    }
}
