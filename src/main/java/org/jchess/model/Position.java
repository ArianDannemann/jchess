package org.jchess.model;

/**
 * This class represents a position of a single piece on a chess board inside a coordinate system, were <i>a1</i> is <i>(0; 0)</i> and <i>h8</i> is <i>(7; 7)</i>
 */
public class Position
{
    private int file;
    private int rank;

    public Position (Position origin, int fileOffset, int rankOffset)
    {
        this.file = origin.getFile() + fileOffset;
        this.rank = origin.getRank() + rankOffset;
    }

    public Position (int file, int rank)
    {
        this.file = file;
        this.rank = rank;
    }

    public static boolean equals (Position a, Position b)
    {
        return a.getFile() == b.getFile() && a.getRank() == b.getRank();
    }

    public void setFile (int file)
    {
        this.file = file;
    }

    public int getFile ()
    {
        return this.file;
    }

    public void setRank (int rank)
    {
        this.rank = rank;
    }

    public int getRank ()
    {
        return this.rank;
    }
}
