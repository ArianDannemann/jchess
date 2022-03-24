package org.jchess.model;

public class Move
{
    private Piece piece;
    private Position position;

    private boolean isEnPassant = false;
    private Position enPassantPosition;

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

    public Piece getPiece()
    {
        return piece;
    }

    public boolean getIsEnPassant()
    {
        return isEnPassant;
    }

    public Position getEnPassantPosition()
    {
        return enPassantPosition;
    }

    public void setPosition(Position position)
    {
        this.position = position;
    }

    public void setPiece(Piece piece)
    {
        this.piece = piece;
    }

    public void setIsEnPassant(boolean isEnPassant)
    {
        this.isEnPassant = isEnPassant;
    }

    public void setEnPassantPosition(Position enPassantPosition)
    {
        this.enPassantPosition = enPassantPosition;
    }
}
