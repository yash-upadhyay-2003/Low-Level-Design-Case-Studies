package LLDCaseStudies.tictactoe.strategies;

import LLDCaseStudies.tictactoe.coreclasses.Board;
import LLDCaseStudies.tictactoe.enums.Symbol;

public class ColumnWinningStrategy implements WinningStrategy {

    @Override
    public boolean checkWin(Board board, int row, int col, Symbol symbol) {
        if (symbol == Symbol.EMPTY) return false;
        int size = board.getSize();
        for (int r = 0; r < size; r++) {
            if (board.getCell(r, col).getSymbol() != symbol) {
                return false;
            }
        }
        return true;
    }
}

