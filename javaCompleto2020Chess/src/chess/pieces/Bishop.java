package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Bishop extends ChessPiece {

    public Bishop(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "B";
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] matrix = new boolean[getBoard().getRows()][getBoard().getColumns()];
        Position positionCheck = new Position(0, 0);

        int[][] directions = new int[][] { { -1, -1 }, { +1, +1 }, { +1, -1 }, { -1, +1 } };
        for (int i = 0; i < directions.length; ++i) {
            int rowPlus = directions[i][0];
            int columnPlus = directions[i][1];
            positionCheck.setValues(position.getRow() + rowPlus, position.getColumn() + columnPlus);
            while (getBoard().positionExists(positionCheck) && !getBoard().isThereAPiece(positionCheck)) {
                matrix[positionCheck.getRow()][positionCheck.getColumn()] = true;
                positionCheck.setValues(positionCheck.getRow() + rowPlus, positionCheck.getColumn() + columnPlus);
            }
            if (getBoard().positionExists(positionCheck) && isThereOpponentPiece(positionCheck)) {
                matrix[positionCheck.getRow()][positionCheck.getColumn()] = true;
            }
        }

        return matrix;
    }
}
