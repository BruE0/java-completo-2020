package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Queen;
import chess.pieces.Rook;

public class ChessMatch {

    private int turn;
    private Color currentPlayer;
    private Board board;
    private boolean check;
    private boolean checkMate;
    private ChessPiece enPassantVulnerable;
    private ChessPiece promoted;

    private List<Piece> piecesOnTheBoard = new ArrayList<>();
    private List<Piece> capturedPieces = new ArrayList<>();

    public ChessMatch() {
        board = new Board(8, 8);
        turn = 1;
        currentPlayer = Color.WHITE;
        initialSetup();
    }

    public int getTurn() {
        return turn;
    }

    public Color getcurrentPlayer() {
        return currentPlayer;
    }

    public boolean getCheck() {
        return check;
    }

    public boolean getCheckMate() {
        return checkMate;
    }

    public List<Piece> getCapturedPieces() {
        return capturedPieces;
    }

    public ChessPiece getEnPassantVulnerable() {
        return enPassantVulnerable;
    }

    public ChessPiece getPromoted() {
        return promoted;
    }

    public ChessPiece[][] getPieces() {
        ChessPiece[][] matrix = new ChessPiece[board.getRows()][board.getColumns()];
        for (int i = 0; i < board.getRows(); ++i) {
            for (int j = 0; j < board.getColumns(); ++j) {
                matrix[i][j] = (ChessPiece) board.piece(i, j);
            }
        }
        return matrix;
    }

    public boolean[][] possibleMoves(ChessPosition sourcePosition) {
        Position position = sourcePosition.toPosition();
        validateSourcePosition(position);
        return board.piece(position).possibleMoves();
    }

    public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
        Position source = sourcePosition.toPosition();
        Position target = targetPosition.toPosition();
        validateSourcePosition(source);
        validateTargetPosition(source, target);
        Piece capturedPiece = makeMove(source, target);
        if (testCheck(currentPlayer)) {
            undoMove(source, target, capturedPiece);
            throw new ChessException("You cannot put yourself in check");
        }
        ChessPiece movedPiece = (ChessPiece) board.piece(target);

        // #specialmove promotion
        promoted = null;
        if (movedPiece instanceof Pawn) {
            if ((movedPiece.getColor() == Color.WHITE && target.getRow() == 0)
                    || (movedPiece.getColor() == Color.BLACK && target.getRow() == 7)) {
                promoted = movedPiece;
                promoted = replacePromotedPiece("Q");
            }
        }

        check = testCheck(opponentOf(currentPlayer));

        if (testCheckMate(opponentOf(currentPlayer))) {
            checkMate = true;
        } else {
            nextTurn();
        }

        // #specialmove enPassant
        if (movedPiece instanceof Pawn && Math.abs(target.getRow() - source.getRow()) == 2) {
            enPassantVulnerable = movedPiece;
        } else {
            enPassantVulnerable = null;
        }

        return (ChessPiece) capturedPiece;
    }

    public ChessPiece replacePromotedPiece(String type) {
        if (promoted == null) {
            throw new IllegalStateException("There is no piece to be promoted");
        }
        if (!type.equals("B") && !type.equals("N") && !type.equals("R") && !type.equals("Q")) {
            return promoted;
        }
        Position promotedPosition = promoted.getChessPosition().toPosition();
        Piece removedPiece = board.removePiece(promotedPosition);
        piecesOnTheBoard.remove(removedPiece);

        ChessPiece newPiece = newPiece(type, promoted.getColor());
        board.placePiece(newPiece, promotedPosition);

        piecesOnTheBoard.add(newPiece);

        return newPiece;
    }

    private ChessPiece newPiece(String type, Color color) {
        if (type.equals("Q"))
            return new Queen(board, color);
        if (type.equals("N"))
            return new Knight(board, color);
        if (type.equals("B"))
            return new Bishop(board, color);
        return new Rook(board, color);
    }

    private Piece makeMove(Position source, Position target) {
        ChessPiece movingPiece = (ChessPiece) board.removePiece(source);
        movingPiece.increaseMoveCount();
        Piece capturedPiece = board.removePiece(target);
        board.placePiece(movingPiece, target);

        if (capturedPiece != null) {
            piecesOnTheBoard.remove(capturedPiece);
            capturedPieces.add(capturedPiece);
        }

        // #specialmove castling kingside rook
        if (movingPiece instanceof King && target.getColumn() - source.getColumn() == 2) {
            Position sourceRook = new Position(source.getRow(), source.getColumn() + 3);
            Position targetRook = new Position(source.getRow(), source.getColumn() + 1);
            makeMove(sourceRook, targetRook);

        }
        // #specialmove castling queenside rook
        else if (movingPiece instanceof King && target.getColumn() - source.getColumn() == -2) {
            Position sourceRook = new Position(source.getRow(), source.getColumn() - 4);
            Position targetRook = new Position(source.getRow(), source.getColumn() - 1);
            makeMove(sourceRook, targetRook);
        }
        // #specialmove enPassant
        else if (movingPiece instanceof Pawn
                && capturedPiece == null
                && source.getColumn() != target.getColumn()
                && ((ChessPiece) board.piece(source.getRow(), target.getColumn())).equals(getEnPassantVulnerable())
                && movingPiece.getColor() != getEnPassantVulnerable().getColor()) {
            capturedPiece = board.removePiece(new Position(source.getRow(), target.getColumn()));
            piecesOnTheBoard.remove(capturedPiece);
            capturedPieces.add(capturedPiece);
        }
        return capturedPiece;

    }

    private void undoMove(Position source, Position target, Piece capturedPiece) {
        ChessPiece movingPiece = (ChessPiece) board.removePiece(target);
        movingPiece.decreaseMoveCount();
        board.placePiece(movingPiece, source);

        if (capturedPiece != null) {
            board.placePiece(capturedPiece, target);
            capturedPieces.remove(capturedPiece);
            piecesOnTheBoard.add(capturedPiece);
        }

        // #specialmove castling kingside rook
        if (movingPiece instanceof King && target.getColumn() - source.getColumn() == 2) {
            Position sourceRook = new Position(source.getRow(), source.getColumn() + 3);
            Position targetRook = new Position(source.getRow(), source.getColumn() + 1);
            undoMove(sourceRook, targetRook, null);

        }
        // #specialmove castling queenside rook
        else if (movingPiece instanceof King && target.getColumn() - source.getColumn() == -2) {
            Position sourceRook = new Position(source.getRow(), source.getColumn() - 4);
            Position targetRook = new Position(source.getRow(), source.getColumn() - 1);
            undoMove(sourceRook, targetRook, null);
        }
        // #specialmove enPassant
        else if (movingPiece instanceof Pawn
                && source.getColumn() != target.getColumn()
                && ((ChessPiece) capturedPiece).equals(enPassantVulnerable)) {
            board.removePiece(target);
            board.placePiece(capturedPiece, new Position(source.getRow(), target.getColumn()));
        }
    }

    private void validateSourcePosition(Position source) {
        if (!board.isThereAPiece(source)) {
            throw new ChessException("There is no piece on source position");
        }
        if (currentPlayer != ((ChessPiece) board.piece(source)).getColor()) {
            throw new ChessException("The chosen piece is not yours");
        }
        if (!board.piece(source).isThereAnyPossibleMove()) {
            throw new ChessException("There is no possible move for the chosen piece");
        }
    }

    private void validateTargetPosition(Position source, Position target) {
        if (!board.piece(source).isPossibleMove(target)) {
            throw new ChessException("The chosen piece cannot move to target position");
        }
    }

    private void nextTurn() {
        ++turn;
        currentPlayer = currentPlayer == Color.BLACK ? Color.WHITE : Color.BLACK;
    }

    private void placeNewPiece(char column, int row, ChessPiece piece) {
        board.placePiece(piece, new ChessPosition(column, row).toPosition());
        piecesOnTheBoard.add(piece);
    }

    private Color opponentOf(Color color) {
        return color == Color.WHITE ? Color.BLACK : Color.WHITE;
    }

    private ChessPiece getKing(Color color) {
        return piecesOnTheBoard.parallelStream()
                .map(piece -> (ChessPiece) piece)
                .filter(piece -> piece instanceof King && piece.getColor() == color)
                .findAny().orElseThrow(() -> new IllegalStateException("There is no " + color + " king on the board"));
    }

    private boolean testCheck(Color color) {
        Position kingPosition = getKing(color).getChessPosition().toPosition();
        Stream<ChessPiece> opponentPieces = piecesOnTheBoard.parallelStream()
                .map(piece -> (ChessPiece) piece)
                .filter(piece -> piece.getColor() == opponentOf(color));

        return opponentPieces.anyMatch(piece -> {
            boolean[][] matrix = piece.possibleMoves();
            return matrix[kingPosition.getRow()][kingPosition.getColumn()];
        });
    }

    private boolean testCheckMate(Color color) {
        if (!testCheck(color)) {
            return false;
        }
        List<ChessPiece> friendPieces = piecesOnTheBoard.stream()
                .map(piece -> (ChessPiece) piece)
                .filter(piece -> piece.getColor() == color)
                .collect(Collectors.toList());

        for (ChessPiece piece : friendPieces) {
            boolean[][] matrix = piece.possibleMoves();
            Position source = piece.getChessPosition().toPosition();
            for (int i = 0; i < board.getRows(); ++i) {
                for (int j = 0; j < board.getColumns(); ++j) {
                    if (matrix[i][j]) {
                        Position target = new Position(i, j);
                        Piece capturedPiece = makeMove(source, target);
                        boolean testCheck = testCheck(color);
                        undoMove(source, target, capturedPiece);
                        if (!testCheck) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;

    }

    private void initialSetup() {
        placeNewPiece('a', 1, new Rook(board, Color.WHITE));
        placeNewPiece('b', 1, new Knight(board, Color.WHITE));
        placeNewPiece('c', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('d', 1, new Queen(board, Color.WHITE));
        placeNewPiece('e', 1, new King(board, Color.WHITE, this));
        placeNewPiece('f', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('g', 1, new Knight(board, Color.WHITE));
        placeNewPiece('h', 1, new Rook(board, Color.WHITE));
        placeNewPiece('a', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('b', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('c', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('d', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('e', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('f', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('g', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('h', 2, new Pawn(board, Color.WHITE, this));

        placeNewPiece('a', 8, new Rook(board, Color.BLACK));
        placeNewPiece('b', 8, new Knight(board, Color.BLACK));
        placeNewPiece('c', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('d', 8, new Queen(board, Color.BLACK));
        placeNewPiece('e', 8, new King(board, Color.BLACK, this));
        placeNewPiece('f', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('g', 8, new Knight(board, Color.BLACK));
        placeNewPiece('h', 8, new Rook(board, Color.BLACK));
        placeNewPiece('a', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('b', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('c', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('d', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('e', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('f', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('g', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('h', 7, new Pawn(board, Color.BLACK, this));
    }
}
