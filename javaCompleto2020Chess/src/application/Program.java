package application;

import java.util.InputMismatchException;
import java.util.Scanner;

import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPosition;

public class Program {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ChessMatch chessMatch = new ChessMatch();

        while (!chessMatch.getCheckMate()) {
            try {
                UI.clearScreen();
                UI.printMatch(chessMatch);
                System.out.print("\nSource: ");
                ChessPosition source = UI.readChessPosition(sc);

                boolean[][] possibleMoves = chessMatch.possibleMoves(source);
                UI.clearScreen();
                UI.printBoard(chessMatch.getPieces(), possibleMoves);
                System.out.print("\nTarget: ");
                ChessPosition target = UI.readChessPosition(sc);

                chessMatch.performChessMove(source, target);

                if (chessMatch.getPromoted() != null) {
                    System.out.print(UI.ANSI_CYAN
                            + "Enter piece for promotion (B/N/R/Q) [default = Q]: "
                            + UI.ANSI_RESET);
                    String type = sc.nextLine().trim().toUpperCase();
                    if (type.equals("B") || type.equals("N") || type.equals("R") || type.equals("Q")) {
                        chessMatch.replacePromotedPiece(type);
                    } else {
                        System.out.println("Invalid value! Automatically promoted to Queen");
                        sc.nextLine();
                    }
                }

            } catch (ChessException | InputMismatchException e) {
                System.out.println(e.getMessage());
                sc.nextLine();
            }
        }
        UI.clearScreen();
        UI.printMatch(chessMatch);
    }
}
