package org.jchess.model;

import org.jchess.exceptions.PieceOutOfBoundsExceptions;

/**
 * This class represents a chess piece
 */
public class Piece
{
    private Position position;
    private PieceType type;
    private Color color;

    private boolean hasMoved = false;

    public Piece(Position position, PieceType type, Color color)
    {
        this.setPosition(position);
        this.type = type;
        this.color = color;
    }

    public Position getPosition()
    {
        return this.position;
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

    public void setHasMoved(boolean hasMoved)
    {
        this.hasMoved = hasMoved;
    }
}
