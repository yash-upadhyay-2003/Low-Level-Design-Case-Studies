package LLDCaseStudies.tictactoe.strategies;

import LLDCaseStudies.tictactoe.coreclasses.Board;
import LLDCaseStudies.tictactoe.enums.Symbol;

public interface WinningStrategy {
    boolean checkWin(Board board, int row, int col, Symbol symbol);
}
