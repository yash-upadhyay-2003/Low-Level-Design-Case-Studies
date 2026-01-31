package LLDCaseStudies.tictactoe.coreclasses;

import LLDCaseStudies.tictactoe.dataclasses.Player;
import LLDCaseStudies.tictactoe.enums.GAMESTATUS;
import LLDCaseStudies.tictactoe.enums.Symbol;
import LLDCaseStudies.tictactoe.exception.InvalidMoveException;
import LLDCaseStudies.tictactoe.observer.GameObserver;
import LLDCaseStudies.tictactoe.strategies.ColumnWinningStrategy;
import LLDCaseStudies.tictactoe.strategies.DiagonalStrategy;
import LLDCaseStudies.tictactoe.strategies.RowWinningStrategy;
import LLDCaseStudies.tictactoe.strategies.WinningStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Game {
    private final Board board;
    private final Player[] players;
    private int currentPlayerIndex;
    private GAMESTATUS status;
    private final List<WinningStrategy> winningStrategies;
    private final List<GameObserver> observers;

    public Game(Player player1, Player player2, int boardSize) {
        this.board = new Board(boardSize);
        this.players = new Player[]{player1, player2};
        this.currentPlayerIndex = 0;
        this.status = GAMESTATUS.IN_PROGRESS;
        this.winningStrategies = initializeStrategies();
        this.observers = new CopyOnWriteArrayList<>();
    }

    private List<WinningStrategy> initializeStrategies() {
        List<WinningStrategy> strategies = new ArrayList<>();
        strategies.add(new RowWinningStrategy());
        strategies.add(new ColumnWinningStrategy());
        strategies.add(new DiagonalStrategy());
        return strategies;
    }

    public synchronized void makeMove(int row, int col) {
        // Check if game is already over
        if (status != GAMESTATUS.IN_PROGRESS) {
            throw new InvalidMoveException("Game is already over!");
        }

        // Validate the move
        if (!board.isCellEmpty(row, col)) {
            throw new InvalidMoveException(
                    "Cell (" + row + ", " + col + ") is already occupied"
            );
        }

        // Place the symbol
        Player currentPlayer = players[currentPlayerIndex];
        board.placeSymbol(row, col, currentPlayer.getSymbol());

        // Check for win
        if (checkWin(row, col, currentPlayer.getSymbol())) {
            status = (currentPlayer.getSymbol() == Symbol.X)
                    ? GAMESTATUS.WINNER_X
                    : GAMESTATUS.WINNER_O;
            notifyObservers();
            return;
        }

        // Check for draw
        if (board.isFull()) {
            status = GAMESTATUS.DRAW;
            notifyObservers();
            return;
        }

        // Switch to next player
        currentPlayerIndex = (currentPlayerIndex + 1) % 2;
    }

    private boolean checkWin(int row, int col, Symbol symbol) {
        for (WinningStrategy strategy : winningStrategies) {
            if (strategy.checkWin(board, row, col, symbol)) {
                return true;
            }
        }
        return false;
    }

    public void addObserver(GameObserver observer) {
        observers.add(observer);
    }

    public void notifyObservers() {
        for (GameObserver observer : observers) {
            observer.update(this);
        }
    }

    public Board getBoard() { return board; }
    public Player getCurrentPlayer() { return players[currentPlayerIndex]; }
    public GAMESTATUS getStatus() { return status; }

    public Player getWinner() {
        if (status == GAMESTATUS.WINNER_X) {
            return players[0].getSymbol() == Symbol.X ? players[0] : players[1];
        } else if (status == GAMESTATUS.WINNER_O) {
            return players[0].getSymbol() == Symbol.O ? players[0] : players[1];
        }
        return null;
    }

    public void printBoard() {
        board.printBoard();
    }
}
