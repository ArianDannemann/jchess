package org.jchess.control;

import org.jchess.model.PieceType;

/**
 * A class to help with different string operations
 */
public class StringHelper
{
    /**
     * Checks if a character is an uppercase letter
     * @param ch The character to check
     * @return True if the character is an uppercase letter, false otherwise
     */
    public static boolean isCharUppercaseLetter(char ch)
    {
        return (ch > 64 && ch < 91);
    }

    /**
     * Checks if a character is a lowercase letter
     * @param ch The character to check
     * @return True if the character is a lowercase letter, false otherwise
     */
    public static boolean isCharLowecaseLetter(char ch)
    {
        return (ch > 96 && ch < 123);
    }

    /**
     * Checks if a character is a letter
     * @param ch The character to check
     * @return True if the character is a letter, false otherwise
     */
    public static boolean isCharLetter(char ch)
    {
        return isCharUppercaseLetter(ch) || isCharLowecaseLetter(ch);
    }

    /**
     * Checks if a character is a digit
     * @param ch The character to check
     * @return True if the character is a digit, false otherwise
     */
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
