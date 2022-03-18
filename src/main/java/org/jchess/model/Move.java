package org.jchess.model;

public class Move
{
    private Piece piece;
    private Position position;

    public Move()
    {
        this.setPiece(null);
        this.setPosition(null);
    }

    public Move(Piece piece, Position position)
    {
        this.setPiece(piece);
        this.setPosition(position);
    }

    public Position getPosition()
    {
        return position;
    }

    public void setPosition(Position position)
    {
        this.position = position;
    }

    public Piece getPiece()
    {
        return piece;
    }

    public void setPiece(Piece piece)
    {
        this.piece = piece;
    }
}
