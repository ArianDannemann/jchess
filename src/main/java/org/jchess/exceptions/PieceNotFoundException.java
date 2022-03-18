package org.jchess.exceptions;

import org.jchess.model.Strings;

public class PieceNotFoundException extends RuntimeException
{
    public PieceNotFoundException()
    {
        super(Strings.PIECE_NOT_FOUND);
    }
}
