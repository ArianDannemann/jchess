package org.jchess.control;

import org.jchess.model.PieceType;

/**
 * A class to help with different string operations
 */
public class StringHelper
{
    public static boolean isCharUppercaseLetter(char ch)
    {
        return (ch > 64 && ch < 91);
    }

    public static boolean isCharLowecaseLetter(char ch)
    {
        return (ch > 96 && ch < 123);
    }

    public static boolean isCharLetter(char ch)
    {
        return isCharUppercaseLetter(ch) || isCharLowecaseLetter(ch);
    }

    public static boolean isCharNumber(char ch)
    {
        return (ch > 47 && ch < 58);
    }

    /**
     * Gets the piece type that is represented by the given character
     * @param abbreviation The character that represents the piece type
     * @return The piece type represented by the character
     */
    public static PieceType getTypeFromAbbreviation(char abbreviation)
    {
        switch (abbreviation)
        {
            case 'R':
            case 'r':
                return PieceType.ROOK;

            case 'N':
            case 'n':
                return PieceType.KNIGHT;

            case 'B':
            case 'b':
                return PieceType.BISHOP;

            case 'Q':
            case 'q':
                return PieceType.QUEEN;

            case 'K':
            case 'k':
                return PieceType.KING;

            case 'P':
            case 'p':
                return PieceType.PAWN;

            default:
                return null;
        }
    }
}
