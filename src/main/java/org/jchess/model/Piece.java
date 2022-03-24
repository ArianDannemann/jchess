package org.jchess.model;

import org.jchess.exceptions.PieceOutOfBoundsExceptions;

/**
 * This class represents a chess piece
 */
public class Piece
{
    private Position position; // the position of the piece
    private PieceType type; // the type of the piece
    private Color color; // the color of the piece

    private boolean hasMoved = false; // has the piece moved atleast once?
    private boolean isInCheck = false; // is the piece currently in check? (only set for kings)

    /**
     * Create a new chess piece
     * @param position The position of the piece
     * @param type The type of the piece
     * @param color The color of the piece
     */
    public Piece(Position position, PieceType type, Color color)
    {
        this.setPosition(position);
        this.type = type;
        this.color = color;
    }

    public boolean getIsInCheck()
    {
        return isInCheck;
    }

    public Position getPosition()
    {
        return this.position;
    }

    public PieceType getType()
    {
        return this.type;
    }

    public Color getColor()
    {
        return this.color;
    }

    public boolean getHasMoved()
    {
        return this.hasMoved;
    }

    public void setPosition(Position position)
    {
        // Check if the new position is in bounds
        if (position.getFile() < 0 || position.getFile() > 7
            || position.getRank() < 0 || position.getRank() > 7)
        {
            throw new PieceOutOfBoundsExceptions();
        }
        else {
            this.position = position;
        }
    }

    public void setHasMoved(boolean hasMoved)
    {
        this.hasMoved = hasMoved;
    }

    public void setIsInCheck(boolean isInCheck)
    {
        this.isInCheck = isInCheck;
    }

    public void setType(PieceType type)
    {
        this.type = type;
    }
}
