package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece {

    private ChessMatch chessMatch;

    public Pawn(Board board, Color color, ChessMatch chessMatch) {
        super(board, color);
        this.chessMatch = chessMatch;
    }

    @Override
    public String toString() {
        return "P";
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] matrix = new boolean[getBoard().getRows()][getBoard().getColumns()];

        Position posCheck = new Position(0, 0);
        int rowFrontOffset = getColor() == Color.WHITE ? -1 : +1;

        // MOVEMENT
        posCheck.setValues(position.getRow() + rowFrontOffset, position.getColumn());
        if (getBoard().positionExists(posCheck) && !getBoard().isThereAPiece(posCheck)) {
            matrix[posCheck.getRow()][posCheck.getColumn()] = true;

            posCheck.setRow(position.getRow() + 2 * rowFrontOffset);
            if (getMoveCount() == 0 && getBoard().positionExists(posCheck)
                    && !getBoard().isThereAPiece(posCheck)) {
                matrix[posCheck.getRow()][posCheck.getColumn()] = true;
            }
        }

        // CAPTURE
        posCheck.setValues(position.getRow() + rowFrontOffset, position.getColumn() - 1);
        if (getBoard().positionExists(posCheck) && isThereOpponentPiece(posCheck)) {
            matrix[posCheck.getRow()][posCheck.getColumn()] = true;
        }
        posCheck.setColumn(position.getColumn() + 1);
        if (getBoard().positionExists(posCheck) && isThereOpponentPiece(posCheck)) {
            matrix[posCheck.getRow()][posCheck.getColumn()] = true;
        }

        // #specialmove EnPassant
        if (position.getRow() == (getColor() == Color.WHITE ? 3 : 4)) {
            for (int sideOffset : new int[] { -1, +1 }) {
                Position posToTheSide = new Position(position.getRow(), position.getColumn() + sideOffset);
                if (getBoard().positionExists(posToTheSide) && isThereOpponentPiece(posToTheSide) &&
                        getBoard().piece(posToTheSide) == chessMatch.getEnPassantVulnerable()) {
                    matrix[posToTheSide.getRow() + rowFrontOffset][posToTheSide.getColumn()] = true;
                }
            }
        }

        return matrix;
    }
}
