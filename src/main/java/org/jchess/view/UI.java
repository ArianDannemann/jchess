package org.jchess.view;

import org.jchess.control.MoveManager;
import org.jchess.model.Board;
import org.jchess.model.Color;
import org.jchess.model.Piece;
import org.jchess.model.Position;
import org.jchess.model.Strings;

/**
 * This is a utility class for displaying information to the user
 */
public class UI
{
    public static void print(String msg)
    {
        System.out.print(Strings.APP_PREFIX + msg);
    }

    public static void println(String msg)
    {
        UI.print(msg + "\n");
    }

    /**
     * Prints the current position on a board to the console
     * @param board The board to be printed
     */
    public static void printBoard(Board board)
    {
        Piece[] pieces = board.getPieces();
        int file;
        int rank;

        System.out.println(' ');

        for (rank = 7; rank > -1; rank --)
        {
            System.out.print(" " + (rank + 1) + "  ");

            for (file = 0; file < 8; file ++)
            {
                boolean pieceFound = false;

                System.out.print(' ');

                for (Piece piece : pieces)
                {
                    if (piece == null)
                    {
                        continue;
                    }

                    if (Position.equals(piece.getPosition(), new Position(file, rank)))
                    {
                        System.out.print(UI.getPieceAbbreviation(piece));
                        pieceFound = true;

                        break;
                    }
                }

                if (!pieceFound)
                {
                    System.out.print('.');
                }

                if (file == 7)
                {
                    System.out.println(' ');
                }
            }
        }

        System.out.println(' ');
        System.out.println("     a b c d e f g h");
        System.out.println(' ');

        System.out.println("Piece count: " + board.getPieces().length);
        System.out.println("Playing side: " + board.getPlayingSideColor().toString());

        System.out.println(" ");
    }

    /**
     * Prints the current position on the given board with markers that indicate all spaces the given piece could move to
     * @param board The board we want to print
     * @param targetPiece The piece of which the legal moves should be displayed
     */
    public static void printBoardWithValidMoves(Board board, Piece targetPiece)
    {
        Piece[] pieces = board.getPieces();
        Position[] targetPositions = MoveManager.getLegalMoves(board, targetPiece);
        int file;
        int rank;

        if (targetPositions == null)
        {
            System.out.println("No target positions for piece");
            return;
        }

        System.out.println(' ');

        for (rank = 7; rank > -1; rank --)
        {
            for (file = 0; file < 8; file ++)
            {
                boolean pieceFound = false;
                char ch = ' ';

                System.out.print(' ');

                for (Piece piece : pieces)
                {
                    if (piece == null)
                    {
                        continue;
                    }

                    if (Position.equals(piece.getPosition(), new Position(file, rank)))
                    {
                        ch = UI.getPieceAbbreviation(piece);
                        pieceFound = true;

                        break;
                    }
                }

                if (!pieceFound)
                {
                    ch = '.';
                }

                for (Position targePosition : targetPositions)
                {
                    if (Position.equals(new Position(file, rank), targePosition))
                    {
                        ch = '_';
                    }
                }

                System.out.print(ch);

                if (file == 7)
                {
                    System.out.println(' ');
                }
            }
        }

        System.out.println(' ');

        System.out.println("Piece count: " + board.getPieces().length);
    }

    /**
     * Gets the correct abbreviation for a given piece type
     * @param piece The piece for which we want to get the abbreviation
     * @return The character representing the given piece type
     */
    public static char getPieceAbbreviation(Piece piece)
    {
        switch (piece.getType())
        {
            case ROOK:
                return piece.getColor() == Color.WHITE ? 'R' : 'r';

            case KNIGHT:
                return piece.getColor() == Color.WHITE ? 'N' : 'n';

            case BISHOP:
                return piece.getColor() == Color.WHITE ? 'B' : 'b';

            case QUEEN:
                return piece.getColor() == Color.WHITE ? 'Q' : 'q';

            case KING:
                return piece.getColor() == Color.WHITE ? 'K' : 'k';

            case PAWN:
                return piece.getColor() == Color.WHITE ? 'P' : 'p';

            default:
                return '\r';
        }
    }
}
