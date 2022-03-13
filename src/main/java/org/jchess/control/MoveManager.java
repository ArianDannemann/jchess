package org.jchess.control;

import java.util.ArrayList;
import java.util.List;

import org.jchess.model.Board;
import org.jchess.model.Color;
import org.jchess.model.Piece;
import org.jchess.model.Position;

/**
 * This class handles behaviour related to the movement of chess pieces
 */
public class MoveManager
{
    public static boolean isMoveLegal (Board board, Piece piece, Position position)
    {
        Position[] legalPositions = MoveManager.getLegalMoves(board, piece);

        for (Position legalPosition : legalPositions)
        {
            if (Position.equals(position, legalPosition))
            {
                return true;
            }
        }

        return false;
    }

    public static Position[] getLegalMoves (Board board, Piece piece)
    {
        ArrayList<Position> legalMoves = new ArrayList<>();
        Position[] result;

        switch (piece.getType()) {
            case KING:
                int fileOffset = 0;
                int rankOffset = 0;

                for (fileOffset = -1; fileOffset < 2; fileOffset ++)
                {
                    for (rankOffset = -1; rankOffset < 2; rankOffset ++)
                    {
                        if (fileOffset != 0 || rankOffset != 0)
                        {
                            Position position = new Position(piece.getPosition(), fileOffset, rankOffset);
                            tryAddLegalMove(board, legalMoves, position, piece.getColor());
                        }
                    }
                }
                break;

            case KNIGHT:
                tryAddLegalMove(board, legalMoves, new Position(piece.getPosition(), 2, 1), piece.getColor());
                tryAddLegalMove(board, legalMoves, new Position(piece.getPosition(), 2, -1), piece.getColor());

                tryAddLegalMove(board, legalMoves, new Position(piece.getPosition(), -2, 1), piece.getColor());
                tryAddLegalMove(board, legalMoves, new Position(piece.getPosition(), -2, -1), piece.getColor());

                tryAddLegalMove(board, legalMoves, new Position(piece.getPosition(), 1, 2), piece.getColor());
                tryAddLegalMove(board, legalMoves, new Position(piece.getPosition(), -1, 2), piece.getColor());

                tryAddLegalMove(board, legalMoves, new Position(piece.getPosition(), 1, -2), piece.getColor());
                tryAddLegalMove(board, legalMoves, new Position(piece.getPosition(), -1, -2), piece.getColor());
                break;

            case ROOK:
                tryAddLegalMoveLine(board, legalMoves, piece.getPosition(), new Position(1, 0), piece.getColor());
                tryAddLegalMoveLine(board, legalMoves, piece.getPosition(), new Position(-1, 0), piece.getColor());
                tryAddLegalMoveLine(board, legalMoves, piece.getPosition(), new Position(0, 1), piece.getColor());
                tryAddLegalMoveLine(board, legalMoves, piece.getPosition(), new Position(0, -1), piece.getColor());
                break;

            case BISHOP:
                tryAddLegalMoveLine(board, legalMoves, piece.getPosition(), new Position(1, 1), piece.getColor());
                tryAddLegalMoveLine(board, legalMoves, piece.getPosition(), new Position(-1, 1), piece.getColor());
                tryAddLegalMoveLine(board, legalMoves, piece.getPosition(), new Position(1, -1), piece.getColor());
                tryAddLegalMoveLine(board, legalMoves, piece.getPosition(), new Position(-1, -1), piece.getColor());
                break;

            case QUEEN:
                tryAddLegalMoveLine(board, legalMoves, piece.getPosition(), new Position(1, 0), piece.getColor());
                tryAddLegalMoveLine(board, legalMoves, piece.getPosition(), new Position(-1, 0), piece.getColor());
                tryAddLegalMoveLine(board, legalMoves, piece.getPosition(), new Position(0, 1), piece.getColor());
                tryAddLegalMoveLine(board, legalMoves, piece.getPosition(), new Position(0, -1), piece.getColor());

                tryAddLegalMoveLine(board, legalMoves, piece.getPosition(), new Position(1, 1), piece.getColor());
                tryAddLegalMoveLine(board, legalMoves, piece.getPosition(), new Position(-1, 1), piece.getColor());
                tryAddLegalMoveLine(board, legalMoves, piece.getPosition(), new Position(1, -1), piece.getColor());
                tryAddLegalMoveLine(board, legalMoves, piece.getPosition(), new Position(-1, -1), piece.getColor());
                break;

            case PAWN:
                int movementDirection = piece.getColor() == Color.WHITE ? 1 : -1;

                Position singleStepPosition = new Position(piece.getPosition(), 0, movementDirection);
                Position doubleStepPosition = new Position(piece.getPosition(), 0, movementDirection * 2);

                Position leftAttackPosition = new Position(piece.getPosition(), -1, movementDirection);
                Position rightAttackPosition = new Position(piece.getPosition(), 1, movementDirection);

                if (BoardManager.getPieceAtPosition(board, singleStepPosition) == null)
                {
                    tryAddLegalMove(board, legalMoves, singleStepPosition, piece.getColor());

                    if ((piece.getPosition().getRank() == 1 || piece.getPosition().getRank() == 6)
                        && BoardManager.getPieceAtPosition(board, doubleStepPosition) == null)
                    {
                        tryAddLegalMove(board, legalMoves, doubleStepPosition, piece.getColor());
                    }
                }

                if (BoardManager.getPieceAtPosition(board, leftAttackPosition) != null)
                {
                    tryAddLegalMove(board, legalMoves, leftAttackPosition, piece.getColor());
                }
                if (BoardManager.getPieceAtPosition(board, rightAttackPosition) != null)
                {
                    tryAddLegalMove(board, legalMoves, rightAttackPosition, piece.getColor());
                }

                break;

            default:
                break;
        }

        result = new Position[legalMoves.size()];
        for (int i = 0; i < legalMoves.size(); i ++)
        {
            result[i] = legalMoves.get(i);
        }

        return result;
    }

    private static void tryAddLegalMoveLine (Board board, ArrayList<Position> legalMoves, Position origin, Position direction, Color originatingPieceColor)
    {
        int i = 0;

        for (i = 1; i < 8; i ++)
        {
            Position position = new Position(origin, direction.getFile() * i, direction.getRank() * i);
            Piece pieceAtPosition = BoardManager.getPieceAtPosition(board, position);
            boolean success = tryAddLegalMove(board, legalMoves, position, originatingPieceColor);

            if (!success || (pieceAtPosition != null && pieceAtPosition.getColor() != originatingPieceColor))
            {
                return;
            }
        }
    }

    private static boolean tryAddLegalMove (Board board, ArrayList<Position> legalMoves, Position position, Color originatingPieceColor)
    {
        Piece pieceAtPosition = BoardManager.getPieceAtPosition(board, position);

        if (position.getFile() > -1 && position.getFile() < 8
            && position.getRank() > -1 && position.getRank() < 8
            && (pieceAtPosition == null || pieceAtPosition.getColor() != originatingPieceColor))
        {
            legalMoves.add(position);
            return true;
        }

        return false;
    }
}
