package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece {

    public Pawn(Board board, Color color) {
        super(board, color);
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
        return matrix;
    }
}
