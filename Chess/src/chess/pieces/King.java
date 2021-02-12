package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {

    private ChessMatch chessMatch;

    public King(Board board, Color color, ChessMatch chessMatch) {
        super(board, color);
        this.chessMatch = chessMatch;
    }

    @Override
    public String toString() {
        return "K";
    }

    private boolean testRookCastling(Position position) {
        ChessPiece piece = (ChessPiece) getBoard().piece(position);
        return piece != null && piece instanceof Rook
                && piece.getColor() == getColor() && piece.getMoveCount() == 0;
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

        // #specialmove Castling
        if (getMoveCount() == 0 && !chessMatch.getCheck()) {
            // #specialmove castling kingside rook
            Position posRook = new Position(position.getRow(), position.getColumn() + 3);
            Position p1 = new Position(position.getRow(), position.getColumn() + 1);
            Position p2 = new Position(position.getRow(), position.getColumn() + 2);
            if (testRookCastling(posRook) && getBoard().piece(p1) == null && getBoard().piece(p2) == null) {
                matrix[position.getRow()][position.getColumn() + 2] = true;
            }

            // #specialmove castling queenside rook
            posRook.setColumn(position.getColumn() - 4);
            p1.setColumn(position.getColumn() - 1);
            p2.setColumn(position.getColumn() - 2);
            Position p3 = new Position(position.getRow(), position.getColumn() - 3);
            if (testRookCastling(posRook) && getBoard().piece(p1) == null
                    && getBoard().piece(p2) == null
                    && getBoard().piece(p3) == null) {
                matrix[position.getRow()][position.getColumn() - 2] = true;
            }
        }

        return matrix;
    }
}
