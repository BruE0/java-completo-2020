package application;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import boardgame.Piece;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.Color;

public class UI {

    // https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    public static final String ANSI_WHITE_UNDERLINE = "\033[4;37m";

    // https://stackoverflow.com/questions/2979383/java-clear-the-console
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static ChessPosition readChessPosition(Scanner sc) {
        try {
            String input = sc.nextLine();
            char column = input.charAt(0);
            int row = Integer.parseInt(input.substring(1, 2));
            return new ChessPosition(column, row);
        } catch (RuntimeException e) {
            throw new InputMismatchException("Error reading ChessPosition. Valid values are a1 to h8");
        }
    }

    public static void printMatch(ChessMatch chessMatch) {
        printBoard(chessMatch.getPieces());
        System.out.println();
        printCapturedPieces(chessMatch.getCapturedPieces());
        System.out.println("\nTurn : " + chessMatch.getTurn());

        if (!chessMatch.getCheckMate()) {
            System.out.print("Waiting player: ");
            String textColor = chessMatch.getcurrentPlayer() == Color.BLACK ? ANSI_YELLOW : ANSI_WHITE;
            System.out.println(textColor + chessMatch.getcurrentPlayer() + ANSI_RESET);
            if (chessMatch.getCheck()) {
                System.out.println(ANSI_YELLOW_BACKGROUND + ANSI_BLACK + "CHECK!" + ANSI_RESET);
            }
        } else {
            String ansiPlayerColor = chessMatch.getcurrentPlayer() == Color.WHITE ? ANSI_WHITE : ANSI_YELLOW;
            System.out.print(ANSI_RED_BACKGROUND + "CHECKMATE!!" + ANSI_RESET + "\n");
            System.out.println("Winner: " + ansiPlayerColor + chessMatch.getcurrentPlayer() + ANSI_RESET);
        }
    }

    public static void printBoard(ChessPiece[][] pieces) {
        System.out.println(ANSI_GREEN + "  a b c d e f g h" + ANSI_RESET);
        for (int i = 0; i < pieces.length; ++i) {
            System.out.print(ANSI_GREEN + (pieces.length - i) + ANSI_RESET + " ");
            for (int j = 0; j < pieces[i].length; ++j) {
                printPiece(pieces[i][j], false);
            }
            System.out.println(ANSI_GREEN + (pieces.length - i) + ANSI_RESET + " ");
        }
        System.out.println(ANSI_GREEN + "  a b c d e f g h" + ANSI_RESET);
    }

    public static void printBoard(ChessPiece[][] pieces, boolean[][] possibleMoves) {
        System.out.println(ANSI_GREEN + "  a b c d e f g h" + ANSI_RESET);
        for (int i = 0; i < pieces.length; ++i) {
            System.out.print(ANSI_GREEN + (pieces.length - i) + ANSI_RESET + " ");
            for (int j = 0; j < pieces[i].length; ++j) {
                printPiece(pieces[i][j], possibleMoves[i][j]);
            }
            System.out.println(ANSI_GREEN + (pieces.length - i) + ANSI_RESET + " ");
        }
        System.out.println(ANSI_GREEN + "  a b c d e f g h" + ANSI_RESET);
    }

    private static void printPiece(ChessPiece piece, boolean shouldColorBackground) {
        if (shouldColorBackground) {
            System.out.print(ANSI_BLUE_BACKGROUND);
        }
        if (piece == null) {
            System.out.print("-" + ANSI_RESET);
        } else if (piece.getColor() == Color.WHITE) {
            System.out.print(ANSI_WHITE + piece + ANSI_RESET);
        } else if (piece.getColor() == Color.BLACK) {
            System.out.print(ANSI_YELLOW + piece + ANSI_RESET);
        }
        System.out.print(" ");
    }

    private static void printCapturedPieces(List<Piece> list) {
        List<Piece> whites = list.stream().filter(x -> ((ChessPiece) x).getColor() == Color.WHITE)
                .collect(Collectors.toList());
        List<Piece> blacks = list.stream().filter(x -> ((ChessPiece) x).getColor() == Color.BLACK)
                .collect(Collectors.toList());
        System.out.println(ANSI_WHITE_UNDERLINE + "Captured pieces:" + ANSI_RESET);
        System.out.println("White: " + ANSI_WHITE + whites + ANSI_RESET);
        System.out.println("Black: " + ANSI_YELLOW + blacks + ANSI_RESET);
    }
}