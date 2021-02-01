package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {

    public King(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "K";
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] matrix = new boolean[getBoard().getRows()][getBoard().getColumns()];

        int row = position.getRow();
        int column = position.getColumn();
        Position posCheck = new Position(0, 0);

        for (int i = row - 1; i <= row + 1; ++i) {
            posCheck.setRow(i);
            for (int j = column - 1; j <= column + 1; ++j) {
                posCheck.setColumn(j);
                if ((i == row && j == column) || (!getBoard().positionExists(posCheck))) {
                    continue;
                }
                if (!getBoard().isThereAPiece(posCheck) || isThereOpponentPiece(posCheck)) {
                    matrix[i][j] = true;
                }
            }
        }
        return matrix;
    }
}
