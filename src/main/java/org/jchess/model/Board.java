package org.jchess.model;

/**
 * This class represents a chess board
 */
public class Board
{
    // A list of all the pieces on the board
    private Piece[] pieces = new Piece[0];
    private Color playingSideColor = Color.WHITE;

    public void setPieces(Piece[] pieces)
    {
        this.pieces = pieces;
    }

    public Piece[] getPieces()
    {
        return this.pieces;
    }

    public Color getPlayingSideColor()
    {
        return playingSideColor;
    }

    public void setPlayingSideColor(Color playingSideColor)
    {
        this.playingSideColor = playingSideColor;
    }
}
