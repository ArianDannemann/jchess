package org.jchess.model;

/**
 * This class represents a chess board
 */
public class Board
{
    // A list of all the pieces on the board
    private Piece[] pieces = new Piece[0];
    private Color playingSideColor = Color.WHITE;
    private CastlingStatus[] castlingStatuses = { CastlingStatus.KINGANDQUEENSIDE, CastlingStatus.KINGANDQUEENSIDE };

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

    public CastlingStatus[] getCastlingStatuses()
    {
        return this.castlingStatuses;
    }

    public void setCastlingStatuses(CastlingStatus[] castlingStatuses)
    {
        this.castlingStatuses = castlingStatuses;
    }

    public CastlingStatus getWhiteCastlingStatus()
    {
        return this.castlingStatuses[0];
    }

    public void setWhiteCastlingStatus(CastlingStatus castlingStatus)
    {
        this.castlingStatuses[0] = castlingStatus;
    }

    public CastlingStatus getBlackCastlingStatus()
    {
        return this.castlingStatuses[1];
    }

    public void setBlackCastlingStatus(CastlingStatus castlingStatus)
    {
        this.castlingStatuses[1] = castlingStatus;
    }
}
