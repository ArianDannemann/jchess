package org.jchess.model;

/**
 * This class represents a chess board
 */
public class Board
{
    private Piece[] pieces = new Piece[0]; // a list of all pieces on the board
    private Color playingSideColor = Color.WHITE; // which color is currently moving
    private int movesPlayed = 0; // how many moves have been played
    private CastlingStatus[] castlingStatuses = { CastlingStatus.KINGANDQUEENSIDE, CastlingStatus.KINGANDQUEENSIDE }; // which side can castle in what way
    private Position enPassantPosition; // only set if a pawn moved two spaces, can be attacked by another pawn

    public Piece[] getPieces()
    {
        return this.pieces;
    }

    public int getMovesPlayed()
    {
        return movesPlayed;
    }

    public Color getPlayingSideColor()
    {
        return playingSideColor;
    }

    public CastlingStatus[] getCastlingStatuses()
    {
        return this.castlingStatuses;
    }

    public CastlingStatus getWhiteCastlingStatus()
    {
        return this.castlingStatuses[0];
    }

    public CastlingStatus getBlackCastlingStatus()
    {
        return this.castlingStatuses[1];
    }

    public Position getEnPassanPosition()
    {
        return this.enPassantPosition;
    }

    public void setBlackCastlingStatus(CastlingStatus castlingStatus)
    {
        this.castlingStatuses[1] = castlingStatus;
    }

    public void setPieces(Piece[] pieces)
    {
        this.pieces = pieces;
    }

    public void setPlayingSideColor(Color playingSideColor)
    {
        this.playingSideColor = playingSideColor;
    }

    public void setCastlingStatuses(CastlingStatus[] castlingStatuses)
    {
        this.castlingStatuses = castlingStatuses;
    }

    public void setWhiteCastlingStatus(CastlingStatus castlingStatus)
    {
        this.castlingStatuses[0] = castlingStatus;
    }

    public void setEnPassantPosition(Position enPassantPosition)
    {
        this.enPassantPosition = enPassantPosition;
    }

    public void setMovesPlayed(int movesPlayed)
    {
        this.movesPlayed = movesPlayed;
    }
}
