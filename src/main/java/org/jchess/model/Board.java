package org.jchess.model;

/**
 * This class represents a chess board
 */
public class Board
{
    // TODO: Add support for playing side color

    // A list of all the pieces on the board
    private Piece[] pieces = new Piece[0];

    public void setPieces (Piece[] pieces)
    {
        this.pieces = pieces;
    }

    public Piece[] getPieces ()
    {
        return this.pieces;
    }
}
