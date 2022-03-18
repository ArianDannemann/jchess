package org.jchess.control;

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
}
