package org.jchess.exceptions;

import org.jchess.model.Strings;

/**
 * This exception is called when the user tries to place a piece out of the bounds of the chess board
 */
public class PieceOutOfBoundsExceptions extends RuntimeException
{
    public PieceOutOfBoundsExceptions ()
    {
        super(Strings.PIECE_OUT_OF_BOUNDS);
    }
}
