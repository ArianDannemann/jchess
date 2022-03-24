package org.jchess.model;

/**
 * Represents a possible move that could be played
 */
public class Move
{
    private Piece piece;
    private Position position;

    /**
     * Create an empty theoretical move
     * NOTE: These are not being played by themself, this class is only to contain information
     */
    public Move()
    {
        this.setPiece(null);
        this.setPosition(null);
    }

    /**
     * Create a theoretical move
     * NOTE: These are not being played by themself, this class is only to contain information
     * @param piece The piece that should move
     * @param position The position the piece should move to
     */
    public Move(Piece piece, Position position)
    {
        this.setPiece(piece);
        this.setPosition(position);
    }

    public Position getPosition()
    {
        return position;
    }

    public Piece getPiece()
    {
        return piece;
    }

    public void setPosition(Position position)
    {
        this.position = position;
    }

    public void setPiece(Piece piece)
    {
        this.piece = piece;
    }
}
