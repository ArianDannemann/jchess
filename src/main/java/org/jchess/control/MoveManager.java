package org.jchess.control;

import java.util.ArrayList;

import org.jchess.exceptions.PieceNotFoundException;
import org.jchess.model.Board;
import org.jchess.model.CastlingStatus;
import org.jchess.model.Color;
import org.jchess.model.FileRankHintType;
import org.jchess.model.Move;
import org.jchess.model.Piece;
import org.jchess.model.PieceType;
import org.jchess.model.Position;

/**
 * This class handles behaviour related to the movement of chess pieces
 */
public class MoveManager
{
    /**
     * Checks if a move is considere legal according to chess ruels
     * @param board The board the move is played on
     * @param piece The pieces that should be moved
     * @param position The position to which the piece should be moved
     * @return Whether or not the move can legally be played
     */
    public static boolean isMoveLegal(Board board, Piece piece, Position position)
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

    /**
     * Gets a list of all legal moves for a given chess piece
     * @param board The board on which the piece is standing
     * @param piece The piece of which we want to know the legal moves
     * @return List of all legal moves of the given piece
     */
    public static Position[] getLegalMoves(Board board, Piece piece)
    {
        ArrayList<Position> legalMoves = new ArrayList<>();
        Position[] result;

        if (piece == null)
        {
            throw new PieceNotFoundException();
        }

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

                if (piece.getHasMoved())
                {
                    break;
                }

                // --- Castling ---

                // Get how many squares are empty left and right of the king
                Position[] lineMovesLeft = castRayLine(board, piece.getPosition(), new Position(-1, 0), piece.getColor());
                Position[] lineMovesRight = castRayLine(board, piece.getPosition(), new Position(1, 0), piece.getColor());

                if ((piece.getColor() == Color.WHITE && piece.getPosition().getRank() != 0)
                    || (piece.getColor() == Color.BLACK && piece.getPosition().getRank() != 7))
                {
                    break;
                }

                Piece leftMostPiece = BoardManager.getPieceAtPosition(board, new Position(piece.getPosition(), -4, 0));
                Piece rightMostPiece = BoardManager.getPieceAtPosition(board, new Position(piece.getPosition(), 3, 0));

                if (lineMovesLeft != null
                    && lineMovesLeft.length == 3
                    && leftMostPiece != null
                    && leftMostPiece.getType() == PieceType.ROOK
                    && !leftMostPiece.getHasMoved())
                {
                    // Check if the side can actually castle
                    if ((board.getPlayingSideColor() == Color.WHITE
                        && (board.getWhiteCastlingStatus() == CastlingStatus.QUEENSIDE
                            || board.getWhiteCastlingStatus() == CastlingStatus.KINGANDQUEENSIDE))
                        || (board.getPlayingSideColor() == Color.BLACK
                            && (board.getBlackCastlingStatus() == CastlingStatus.QUEENSIDE
                                || board.getBlackCastlingStatus() == CastlingStatus.KINGANDQUEENSIDE)))
                    {
                        legalMoves.add(new Position(piece.getPosition(), -2, 0));
                    }
                }

                if (lineMovesRight != null
                    && lineMovesRight.length == 2
                    && rightMostPiece != null
                    && rightMostPiece.getType() == PieceType.ROOK
                    && !rightMostPiece.getHasMoved())
                {
                    if ((board.getPlayingSideColor() == Color.WHITE
                        && (board.getWhiteCastlingStatus() == CastlingStatus.KINGSIDE
                            || board.getWhiteCastlingStatus() == CastlingStatus.KINGANDQUEENSIDE))
                        || (board.getPlayingSideColor() == Color.BLACK
                            && (board.getBlackCastlingStatus() == CastlingStatus.KINGSIDE
                                || board.getBlackCastlingStatus() == CastlingStatus.KINGANDQUEENSIDE)))
                    {
                        legalMoves.add(new Position(piece.getPosition(), 2, 0));
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

                if (BoardManager.getPieceAtPosition(board, leftAttackPosition) != null
                    || Position.equals(leftAttackPosition, board.getEnPassanPosition()))
                {
                    tryAddLegalMove(board, legalMoves, leftAttackPosition, piece.getColor());
                }
                if (BoardManager.getPieceAtPosition(board, rightAttackPosition) != null
                    || Position.equals(rightAttackPosition, board.getEnPassanPosition()))
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

    public static Move getUCIMoveFromString(Board board, String moveString)
    {
        Move move = new Move();

        // Get the first two chars
        String firstTwoChars = moveString.substring(0, 2);
        // Get the last two chars
        String lastTwoChars = moveString.substring(2, 4);

        // Get the piece at the first two chars position
        Piece piece = BoardManager.getPieceAtPosition(board, new Position(firstTwoChars));

        // Get where the piece should move
        Position destination = new Position(lastTwoChars);

        // Set the values
        move.setPiece(piece);
        move.setPosition(destination);

        return move;
    }

    /**
     * Figures out what piece is supposed to move to what position from a given string that follows standard chess notation
     * @param board The board the piece is on
     * @param moveString The input string
     * @return A <i>Move</i> object that contains the piece that should move and the position it should move to
     */
    public static Move getMoveFromString(Board board, String moveString)
    {
        Move move = new Move(); // The move to be returned
        Piece movingPiece = null; // The piece that is moving
        PieceType movingPieceType = PieceType.PAWN; // the type of the piece we want to move
        Position positionToMoveTo; // the position we want to move to
        Position movingPiecePosition = new Position(); // the position of the moving piece (if specified)
        // If the position of the moving piece wasn't specified fully, we get a hint at either file or rank
        FileRankHintType fileRankHint = FileRankHintType.NONE;

        boolean isPromotion = false;
        PieceType promotionType = PieceType.NONE;

        // Remove any character that isn't giving us necessary information
        moveString = moveString.replace("x", "");
        moveString = moveString.replace("+", "");
        moveString = moveString.replace("#", "");

        // Check if the move is a pawn promotion
        if (moveString.contains("="))
        {
            isPromotion = true;
            promotionType = StringHelper.getTypeFromAbbreviation(moveString.toCharArray()[moveString.toCharArray().length - 1]);
        }

        // Check if the move is a castling move
        if (moveString.equals("O-O") || moveString.equals("O-O-O"))
        {
            movingPiece = BoardManager.getPieceAtPosition(board, board.getPlayingSideColor() == Color.WHITE ? new Position("e1") : new Position("e8"));
            movingPiecePosition = moveString.equals("O-O") ? new Position(movingPiece.getPosition(), 2, 0) : new Position(movingPiece.getPosition(), -2, 0);

            if (isMoveLegal(board, movingPiece, movingPiecePosition))
            {
                move.setPiece(movingPiece);
                move.setPosition(movingPiecePosition);

                return move;
            }
        }

        char[] moveStringChars = moveString.toCharArray();

        // If the move is a promotion...
        if (isPromotion)
        {
            // ...we can safely remove that part of the string
            moveString = moveString.substring(0, moveStringChars.length - 2);
            moveStringChars = moveString.toCharArray();
        }

        // Figure out the type of piece to move
        for (char moveStringChar : moveStringChars)
        {
            // If the char is uppercase...
            if (StringHelper.isCharUppercaseLetter(moveStringChar))
            {
                movingPieceType = StringHelper.getTypeFromAbbreviation(moveStringChar);
            }
        }

        // If the piece is not a pawn...
        if (movingPieceType != PieceType.PAWN)
        {
            // Remove the first element of our string because we already extruded that information
            moveString = moveString.substring(1, moveStringChars.length);
            moveStringChars = moveString.toCharArray();
        }

        // The position will always be the last to characters in the string
        positionToMoveTo = new Position(moveStringChars[moveStringChars.length - 2] + "" + moveStringChars[moveStringChars.length - 1]);

        // Remove the position information of our string because we already extruded that information
        moveString = moveString.substring(0, moveStringChars.length - 2);
        moveStringChars = moveString.toCharArray();

        // If there is only part of the position of our moving piece given...
        if (moveStringChars.length == 1)
        {
            char positionHintChar = moveStringChars[0];

            // ...figure out which part
            fileRankHint = StringHelper.isCharLetter(positionHintChar) ? FileRankHintType.FILE : FileRankHintType.RANK;

            // Set the information of the position that we know
            if (fileRankHint == FileRankHintType.FILE)
            {
                movingPiecePosition.setFile(Position.getFileFromChar(positionHintChar));
            }
            else
            {
                movingPiecePosition.setRank(Position.getRankFromChar(positionHintChar));
            }
        }
        // If the position of our moving piece is given...
        else if (moveStringChars.length == 2)
        {
            // ...save the position
            movingPiecePosition = new Position(moveStringChars[0] + "" + moveStringChars[1]);
        }

        // Loop through all of the pieces...
        for (Piece piece : board.getPieces())
        {
            // Check if the piece meets all criteria set by the move string
            if (piece.getColor() == board.getPlayingSideColor()
                && piece.getType() == movingPieceType
                && MoveManager.isMoveLegal(board, piece, positionToMoveTo)
                && ((movingPiecePosition.getFile() == -1 && movingPiecePosition.getRank() == -1)
                    || (movingPiecePosition.getFile() != -1 && movingPiecePosition.getRank() == -1 && piece.getPosition().getFile() == movingPiecePosition.getFile())
                    || (movingPiecePosition.getFile() == -1 && movingPiecePosition.getRank() != -1 && piece.getPosition().getRank() == movingPiecePosition.getRank())
                    || (movingPiecePosition.getFile() != -1 && movingPiecePosition.getRank() != -1 && piece.getPosition() == movingPiecePosition)))
            {
                movingPiece = piece;
            }
        }

        // Change the type of the piece if we are promoting
        if (isPromotion)
        {
            movingPiece.setType(promotionType);
        }

        // Save the values to the move
        move.setPiece(movingPiece);
        move.setPosition(positionToMoveTo);

        return move;
    }

    /**
     * Checks if a given position is being attacked by the opposite side
     * @param board The board to check
     * @param position The position to check
     * @return True if the position is being attacked, false otherwise
     */
    public static boolean isPositionAttacked(Board board, Position position)
    {
        for (Piece piece : board.getPieces())
        {
            if (piece.getColor() != board.getPlayingSideColor()
                && MoveManager.isMoveLegal(board, piece, position))
            {
                return true;
            }
        }

        return false;
    }

    /**
     * Works like {@link #tryAddLegalMove(Board, ArrayList, Position, Color)} but scans all position in a line from a given origin in a given direction. It will stop scanning once a piece obscure the given path
     * @param board The board on which we want to move
     * @param legalMoves A list of all legal moves so far, this is were the new move will be added to
     * @param origin The position from which we want to cast a line trace
     * @param direction The direction in which we want to cast a line trace
     * @param originatingPieceColor The color of the moving piece
     */
    private static void tryAddLegalMoveLine(Board board, ArrayList<Position> legalMoves, Position origin, Position direction, Color originatingPieceColor)
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

    /**
     * Similar to {@link #tryAddLegalMoveLine(Board, ArrayList, Position, Position, Color)} but doesn't add the result to a list of moves but instead returns it as an array
     */
    private static Position[] castRayLine(Board board, Position origin, Position direction, Color originatingPieceColor)
    {
        ArrayList<Position> legalMoves = new ArrayList<>();
        Position[] result = null;
        int i = 0;
        int j = 0;

        for (i = 1; i < 8; i ++)
        {
            Position position = new Position(origin, direction.getFile() * i, direction.getRank() * i);
            Piece pieceAtPosition = BoardManager.getPieceAtPosition(board, position);
            boolean success = tryAddLegalMove(board, legalMoves, position, originatingPieceColor);

            if (!success || (pieceAtPosition != null && pieceAtPosition.getColor() != originatingPieceColor))
            {
                result = new Position[legalMoves.size()];

                for (j = 0; j < legalMoves.size(); j ++)
                {
                    result[j] = legalMoves.get(j);
                }

                return result;
            }
        }

        return result;
    }

    /**
     * Tries to add a possible legal move to a list of moves. Used for dynamically adding canditates of legal moves while generating the list of legal moves for a piece
     * @param board The board on which we want to move
     * @param legalMoves A list of all legal moves so far, this is were the new move will be added to
     * @param position The position we are trying to reach
     * @param originatingPieceColor The color of the moving piece
     * @return Whether the move was added as a legal move
     */
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
