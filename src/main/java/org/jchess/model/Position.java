package org.jchess.model;

/**
 * This class represents a position of a single piece on a chess board inside a coordinate system, were <i>a1</i> is <i>(0; 0)</i> and <i>h8</i> is <i>(7; 7)</i>
 */
public class Position
{
    private int file;
    private int rank;

    /**
     * Create an empty position
     */
    public Position()
    {
        this.file = -1;
        this.rank = -1;
    }

    public Position(Position origin, int fileOffset, int rankOffset)
    {
        this.file = origin.getFile() + fileOffset;
        this.rank = origin.getRank() + rankOffset;
    }

    /**
     * Create a new position by specifying coordinates
     * @param file The x coordinate starting at 0
     * @param rank The y coordinate starting at 0
     */
    public Position(int file, int rank)
    {
        this.file = file;
        this.rank = rank;
    }

    /**
     * Create an ew position by specifying its position in standard chess notation
     * @param position The position string, i.e. "e4", "g8", ...
     */
    public Position(String position)
    {
        char[] positionChars = position.toCharArray();
        this.file = Position.getFileFromChar(positionChars[0]);
        this.rank = Position.getRankFromChar(positionChars[1]);
    }

    /**
     * Compares two positions to see if they are equal
     * @param a The first position
     * @param b The second position
     * @return a == b
     */
    public static boolean equals (Position a, Position b)
    {
        if (a == null || b == null) { return false; }
        return a.getFile() == b.getFile() && a.getRank() == b.getRank();
    }

    @Override
    public String toString()
    {
        return this.getFileAsChar() + "" + this.getRankAsChar();
    }

    public String getPositionAsString()
    {
        return "(" + this.getFile() + ", " + this.getRank() + ")";
    }

    public char getFileAsChar()
    {
        return (char) (97 + this.getFile());
    }

    public char getRankAsChar()
    {
        return (char) (49 + this.getRank());
    }

    public static int getFileFromChar(char fileChar)
    {
        return fileChar - 97;
    }

    public static int getRankFromChar(char rankChar)
    {
        return rankChar - 49;
    }

    public void setFile(int file)
    {
        this.file = file;
    }

    public int getFile()
    {
        return this.file;
    }

    public void setRank(int rank)
    {
        this.rank = rank;
    }

    public int getRank()
    {
        return this.rank;
    }
}
