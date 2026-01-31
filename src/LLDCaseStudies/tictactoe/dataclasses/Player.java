package LLDCaseStudies.tictactoe.dataclasses;

import LLDCaseStudies.tictactoe.enums.Symbol;

public class Player {

    String name;
    Symbol symbol;
    public Player(String name, Symbol symbol) {
        this.name = name;
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }
    public Symbol getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return this.toString();
    }
}
