package org.jchess.view;

import org.jchess.model.Board;
import org.jchess.model.Color;
import org.jchess.model.Piece;

/**
 * This is a utility class for displaying information to the user
 */
public class UI
{
    /**
     * Prints the current position on a board to the console
     * @param board The board to be printed
     */
    public static void printBoard (Board board)
    {
        Piece[] pieces = board.getPieces();
        int file;
        int rank;

        System.out.println(' ');

        for (rank = 7; rank > -1; rank --)
        {
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

                    if (piece.getPosition().getFile() == file
                        && piece.getPosition().getRank() == rank)
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

        System.out.println("Piece count: " + board.getPieces().length);
    }

    /**
     * Gets the correct abbreviation for a given piece type
     * @param piece The piece for which we want to get the abbreviation
     * @return The character representing the given piece type
     */
    public static char getPieceAbbreviation (Piece piece)
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
