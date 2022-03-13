package org.jchess.exceptions;

import org.jchess.model.Strings;

/**
 * This exception is called when the user inputs an incorrect FEN strings
 */
public class InvalidFENException extends RuntimeException
{
    public InvalidFENException ()
    {
        super(Strings.INVALID_FEN);
    }
}
