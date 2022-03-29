package token;

import visitor.TokenVisitor;

public class NumberToken implements Token {
    private final int number;

    public NumberToken(int number) {
        this.number = number;
    }

    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }

    public int getNumber() {
        return number;
    }

    public String toString() {
        return "NUMBER(" + Integer.toString(number) + ")";
    }
}
