package org.jchess.control;

import org.jchess.exceptions.InvalidFENException;
import org.jchess.exceptions.PieceNotFoundException;
import org.jchess.model.Board;
import org.jchess.model.CastlingStatus;
import org.jchess.model.Color;
import org.jchess.model.Move;
import org.jchess.model.Piece;
import org.jchess.model.PieceType;
import org.jchess.model.Position;

/**
 * This class manages all interactions with chess boards
 */
public class BoardManager
{
    /**
     * Generates a board with the standard chess position
     * @return A default chess board
     */
    public static Board generateBoard()
    {
        return generateBoard("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
    }

    /**
     * Generates a board and places pieces on it according to FEN notation
     * @param FEN The standard FEN notation for a chess position
     * @return A board with placed pieces
     */
    public static Board generateBoard(String FEN)
    {
        Board board = new Board();
        String[] FENParts = FEN.split(" ");

        BoardManager.addPiecesFromFEN(board, FENParts[0]);
        BoardManager.setPlayingSideFromFEN(board, FENParts[1]);
        BoardManager.setCastlingStatusesFromFEN(board, FENParts[2]);
        BoardManager.setEnPassantPositionFromFEN(board, FENParts[3]);
        BoardManager.setMovesPlayerFromFEN(board, FENParts[5]);

        return board;
    }

    /**
     * Creates a copy of a given board that can be altered without changing the original
     * @param board The board we want to copy
     * @return An independent copy of the given board
     */
    public static Board copyBoard(Board board)
    {
        Piece[] pieces = board.getPieces();
        Board copiedBoard = new Board();

        copiedBoard.setPlayingSideColor(board.getPlayingSideColor());
        copiedBoard.setCastlingStatuses(board.getCastlingStatuses());
        copiedBoard.setEnPassantPosition(board.getEnPassanPosition());
        copiedBoard.setMovesPlayed(board.getMovesPlayed());

        for (Piece piece : pieces)
        {
            Piece copiedPiece = new Piece(piece.getPosition(), piece.getType(), piece.getColor());
            BoardManager.addPiece(copiedBoard, copiedPiece);
            copiedPiece.setHasMoved(piece.getHasMoved());
            copiedPiece.setIsInCheck(piece.getIsInCheck());
        }

        return copiedBoard;
    }

    /**
     * Moves a piece from <i>oldPosition</i> to <i>newPosition</i>
     * @param board The board that the piece is on
     * @param oldPosition The current position of the piece
     * @param newPosition The position where it should move to
     * @return <i>true</i> if the piece was moved, <i>false</i> if the piece could not be moved. This may be the case if the move is considered illegal according to chess rules
     */
    public static boolean movePiece(Board board, Position oldPosition, Position newPosition)
    {
        return BoardManager.movePiece(board, BoardManager.getPieceAtPosition(board, oldPosition), newPosition);
    }

    /**
     * Moves a piece according to standard chess notation taken from <i>moveString</i>
     * @param board The board that the piece is on
     * @param moveString The current position of the piece
     * @return <i>true</i> if the piece was moved, <i>false</i> if the piece could not be moved. This may be the case if the move is considered illegal according to chess rules
     */
    public static boolean movePiece(Board board, String moveString)
    {
        Move move = MoveManager.getMoveFromString(board, moveString);
        return BoardManager.movePiece(board, move.getPiece(), move.getPosition());
    }

    /**
     * Moves a given piece to <i>newPosition</i>
     * @param board The board that the piece is on
     * @param piece The piece that should be moved
     * @param newPosition The position where it should move to
     * @return <i>true</i> if the piece was moved, <i>false</i> if the piece could not be moved. This may be the case if the move is considered illegal according to chess rules
     */
    public static boolean movePiece(Board board, Piece piece, Position newPosition)
    {
        Piece pieceToMove = piece;
        Piece pieceToAttack = BoardManager.getPieceAtPosition(board, newPosition);

        if (piece == null)
        {
            throw new PieceNotFoundException();
        }

        if (!MoveManager.isMoveLegal(board, piece, newPosition)
            || pieceToMove.getColor() != board.getPlayingSideColor())
        {
            return false;
        }

        if (pieceToAttack != null && pieceToAttack.getColor() == pieceToMove.getColor())
        {
            return false;
        }

        if (pieceToAttack != null)
        {
            BoardManager.removePiece(board, pieceToAttack);
        }

        // If the move is a castling move...
        if (pieceToMove.getType() == PieceType.KING
            && (newPosition.getFile() > pieceToMove.getPosition().getFile() + 1
            || newPosition.getFile() < pieceToMove.getPosition().getFile() - 1))
        {
            // ...also move the rook
            BoardManager.getPieceAtPosition(board, new Position(pieceToMove.getPosition(), newPosition.getFile() > 5 ? 3 : -4, 0)).setPosition(new Position(pieceToMove.getPosition(), newPosition.getFile() > 5 ? 1 : -1, 0));
        }

        // Check if a pawn moved two spaces
        if (pieceToMove.getType() == PieceType.PAWN
            && (pieceToMove.getPosition().getRank() > newPosition.getRank() + 1
                || pieceToMove.getPosition().getRank() < newPosition.getRank() - 1))
        {
            // Note that the pawn can now be attacked through en passant
            board.setEnPassantPosition(board.getPlayingSideColor() == Color.WHITE ? new Position(newPosition, 0, -1) : new Position(newPosition, 0, 1));
        }

        // If a piece except a pawn moves or a pawn moved less than two spaces...
        if (pieceToMove.getType() != PieceType.PAWN
            || (pieceToMove.getType() == PieceType.PAWN
                && pieceToMove.getPosition().getRank() != newPosition.getRank() + 2
                && pieceToMove.getPosition().getRank() != newPosition.getRank() - 2))
        {
            // ...remove the en passant position from the board
            board.setEnPassantPosition(null);
        }

        // If an en passant position is attacked, remove the pawn that is standing above or below
        if (Position.equals(newPosition, board.getEnPassanPosition()))
        {
            BoardManager.removePiece(board, board.getPlayingSideColor() == Color.WHITE ? new Position(board.getEnPassanPosition(), 0, -1) : new Position(board.getEnPassanPosition(), 0, 1));
        }

        pieceToMove.setPosition(newPosition);
        pieceToMove.setHasMoved(true);

        BoardManager.switchPlayingSideColor(board);
        BoardManager.updateCheckedPiecesStatus(board);
        board.setMovesPlayed(board.getMovesPlayed() + 1);

        return true;
    }

    /**
     * Moves a piece according to UCI notation
     * @param board The board that the piece is on
     * @param moveString The move string (i.e. "e2e4")
     * @return <i>true</i> if the piece was moved, <i>false</i> if the piece could not be moved. This may be the case if the move is considered illegal according to chess rules
     */
    public static boolean movePieceUCI(Board board, String moveString)
    {
        Move move = MoveManager.getUCIMoveFromString(board, moveString);
        return BoardManager.movePiece(board, move.getPiece(), move.getPosition());
    }

    /**
     * Updates the board to check if one of the kings is in check
     * @param board The board that should be updated
     */
    public static void updateCheckedPiecesStatus(Board board)
    {
        for (Piece piece : board.getPieces())
        {
            if (piece.getType() != PieceType.KING)
            {
                continue;
            }

            piece.setIsInCheck(false);

            for (Piece otherPiece : board.getPieces())
            {
                if (MoveManager.isMoveLegal(board, otherPiece, piece.getPosition()))
                {
                    piece.setIsInCheck(true);
                }
            }
        }
    }

    /**
     * Checks whether the king of a side on a board is in check or not
     * @param board The board that the king is on
     * @param color The color of the side we want to check
     * @return <i>true</i> if the king is in check, <i>false</i> if the king is not in check
     */
    public static boolean isSideInCheck(Board board, Color color)
    {
        BoardManager.updateCheckedPiecesStatus(board);

        for (Piece piece : board.getPieces())
        {
            if (piece.getType() == PieceType.KING
                && piece.getColor() == color)
            {
                return piece.getIsInCheck();
            }
        }

        return false;
    }

    /**
     * Switches the color of the side that is currently playing
     * @param board The board we want to switch the color on
     */
    public static void switchPlayingSideColor(Board board)
    {
        board.setPlayingSideColor(board.getPlayingSideColor() == Color.WHITE ? Color.BLACK : Color.WHITE);
    }

    /**
     * Adds a piece to a board
     * @param board The board that the piece should be added to
     * @param position The position of the new piece
     * @param type The type of the new piece
     * @param color The color of the new piece
     * @return <i>true</i> if the piece was added, <i>false</i> if the piece could not be added. This may be because there already is a piece at the specified location
     */
    public static boolean addPiece(Board board, Position position, PieceType type, Color color)
    {
        return addPiece(board, new Piece(position, type, color));
    }

    /**
     * Adds a piece to a board
     * @param board The board that the piece should be added to
     * @param piece The piece that should be added
     * @return <i>true</i> if the piece was added, <i>false</i> if the piece could not be added. This may be because there already is a piece at the specified location
     */
    public static boolean addPiece(Board board, Piece piece)
    {
        Piece[] oldPieces = board.getPieces();
        Piece[] newPieces = new Piece[oldPieces.length + 1];
        Piece newPiece = piece;
        int i = 0;

        for (i = 0; i < oldPieces.length; i ++)
        {
            // Check if a piece is already standing at the new position
            if (Position.equals(oldPieces[i].getPosition(), newPiece.getPosition()))
            {
                return false;
            }

            newPieces[i] = oldPieces[i];
        }

        newPieces[newPieces.length - 1] = newPiece;

        board.setPieces(newPieces);

        return true;
    }

    /**
     * Removes the piece standing at <i>position</i> from a board
     * @param board The board the piece is standing on
     * @param position The position the piece is at
     * @return <i>true</i> if the piece was removed, <i>false</i> if the piece could not be removed. This may be because there is no piece at the specified location
     */
    public static boolean removePiece(Board board, Position position)
    {
        return removePiece(board, BoardManager.getPieceAtPosition(board, position));
    }

    /**
     * Removes a piece from a board
     * @param board The piece from which the board should be removed
     * @param piece The piece that should be removed
     * @return <i>true</i> if the piece was removed, <i>false</i> if the piece could not be removed. This may be because there is no piece at the specified location
     */
    public static boolean removePiece(Board board, Piece piece)
    {
        Piece[] oldPieces = board.getPieces();
        Piece[] newPieces = new Piece[oldPieces.length - 1];
        Piece pieceToRemove = piece;
        int i = 0;
        int c = 0;
        boolean removedPiece = false;

        // Check if there is a piece at the specified position
        if (piece == null)
        {
            throw new PieceNotFoundException();
        }

        for (i = 0; i < oldPieces.length; i ++)
        {
            // If a piece is not standing at the position where it should be removed...
            if (!Position.equals(oldPieces[i].getPosition(), pieceToRemove.getPosition()))
            {
                // ...add it to the new array
                newPieces[c] = oldPieces[i];

                c ++;
            }
            else
            {
                // ...otherwise, set the removed piece to true
                removedPiece = true;
            }
        }

        // Update the board
        board.setPieces(newPieces);

        return removedPiece;
    }

    /**
     * Generates a list of pieces from the standard FEN notation
     * @param FENPart The part of the FEN string that shows the piece position
     */
    public static void addPiecesFromFEN(Board board, String FENPart)
    {
        int file = 0;
        int rank = 7;

        for (char pieceChar : FENPart.toCharArray())
        {
            //int pieceValue = pieceChar;
            // 47: Slash
            if (pieceChar == 47)
            {
                continue;
            }

            // Check if the char is a letter which represents a piece
            if (StringHelper.isCharLetter(pieceChar))
            {
                Position position = new Position(file, rank);
                PieceType type = StringHelper.getTypeFromAbbreviation(pieceChar);
                Color color = pieceChar < 91 ? Color.WHITE : Color.BLACK;

                // An invalid character abbriviation was used
                if (type == null)
                {
                    throw new InvalidFENException();
                }

                BoardManager.addPiece(board, position, type, color);
            }
            // Check if the character is a number which represents empty spaces
            else if (StringHelper.isCharNumber(pieceChar))
            {
                int freeSpaces = Integer.parseInt(pieceChar + "");
                file += freeSpaces - 1;
            }
            else
            {
                // An invalid character was used (neither a piece nor a number)
                throw new InvalidFENException();
            }

            file ++;

            if (file == 8)
            {
                file = 0;
                rank --;
            }
        }
    }

    /**
     * Gets a piece at a given position
     * @param board The board on which the piece should be located
     * @param position The position of the piece
     * @return The piece at the given position
     */
    public static Piece getPieceAtPosition(Board board, Position position)
    {
        Piece[] pieces = board.getPieces();

        for (Piece piece : pieces)
        {
            if (piece != null
                && piece.getPosition().getFile() == position.getFile()
                && piece.getPosition().getRank() == position.getRank())
            {
                return piece;
            }
        }

        return null;
    }

    /**
     * Gets the currently playing side from a given part of the FEN string and writes the information to <i>board</i>
     * @param board The board we want to write the information to
     * @param FENPart The part of the FEN responsible for indicating the currently playing side
     */
    public static void setPlayingSideFromFEN(Board board, String FENPart)
    {
        board.setPlayingSideColor(FENPart.toCharArray()[0] == 'w' ? Color.WHITE : Color.BLACK);
    }

    /**
     * Gets the current castling status from a given part of the FEN string and writes the information to <i>board</i>
     * @param board The board we want to write the information to
     * @param FENPart The part of the FEN responsible for indicating the castling status of both sides
     */
    public static void setCastlingStatusesFromFEN(Board board, String FENPart)
    {
        board.setWhiteCastlingStatus(CastlingStatus.NONE);
        board.setBlackCastlingStatus(CastlingStatus.NONE);

        if (FENPart.contains("K"))
        {
            board.setWhiteCastlingStatus(CastlingStatus.KINGSIDE);
        }
        if (FENPart.contains("Q"))
        {
            board.setWhiteCastlingStatus(FENPart.contains("K") ? CastlingStatus.KINGANDQUEENSIDE : CastlingStatus.QUEENSIDE);
        }

        if (FENPart.contains("k"))
        {
            board.setBlackCastlingStatus(CastlingStatus.KINGSIDE);
        }
        if (FENPart.contains("q"))
        {
            board.setWhiteCastlingStatus(FENPart.contains("K") ? CastlingStatus.KINGANDQUEENSIDE : CastlingStatus.QUEENSIDE);
        }
    }

    /**
     * Gets the en passant status from a given part of the FEN string and writes the information to <i>board</i>
     * @param board The board we want to write the information to
     * @param FENPart The part of the FEN responsible for indicating the en passant position
     */
    public static void setEnPassantPositionFromFEN(Board board, String FENPart)
    {
        if (!FENPart.equals("-"))
        {
            board.setEnPassantPosition(new Position(FENPart));
        }
        else
        {
            board.setEnPassantPosition(null);
        }
    }

    /**
     * Gets the current halfmove clock from a given part of the FEN string and writes the information to <i>board</i>
     * @param board The board we want to write the information to
     * @param FENPart The part of the FEN responsible for indicating the halfmove clock
     */
    public static void setMovesPlayerFromFEN(Board board, String FENPart)
    {
        board.setMovesPlayed(Integer.parseInt(FENPart));
    }
}
