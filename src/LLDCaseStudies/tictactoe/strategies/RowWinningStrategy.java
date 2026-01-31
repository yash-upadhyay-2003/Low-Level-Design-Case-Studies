package LLDCaseStudies.tictactoe.strategies;

import LLDCaseStudies.tictactoe.coreclasses.Board;
import LLDCaseStudies.tictactoe.enums.Symbol;

public class RowWinningStrategy implements WinningStrategy {

    @Override
    public boolean checkWin(Board board, int row, int col, Symbol symbol) {
        if (symbol == Symbol.EMPTY) return false;
        int size = board.getSize();
        for (int c = 0; c < size; c++) {
            if (board.getCell(row, c).getSymbol() != symbol) {
                return false;
            }
        }
        return true;
    }
}
