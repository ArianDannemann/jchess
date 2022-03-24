package org.jchess.exceptions;

import org.jchess.model.Strings;

/**
 * An exception that is called when a piece could not be found at a specified position
 */
public class PieceNotFoundException extends RuntimeException
{
    public PieceNotFoundException()
    {
        super(Strings.PIECE_NOT_FOUND);
    }
}
