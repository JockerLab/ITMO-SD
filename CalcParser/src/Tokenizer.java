import token.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Tokenizer {
    private State state = State.START;
    private final InputStream input;
    private int number;
    private boolean isSaved = false;
    private int value;

    public Tokenizer(InputStream input) {
        this.input = input;
    }

    private int getSymbol() {
        try {
            if (isSaved) {
                isSaved = false;
                return value;
            }
            return input.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void save(int current) {
        value = current;
        isSaved = true;
    }

    private Token getTokenFromStart() {
        int current = getSymbol();
        switch ((char) current) {
            case '(':
                return new Left();
            case ')':
                return new Right();
            case '+':
                return new Plus();
            case '-':
                return new Minus();
            case '*':
                return new Multiply();
            case '/':
                return new Divide();
            case (char) -1:
                state = State.END;
                return nextToken();
            default:
                if (Character.isWhitespace((char) current)) {
                    return nextToken();
                }
                if (Character.isDigit((char) current)) {
                    state = State.NUMBER;
                    number = current - (int) '0';
                    return nextToken();
                }
                state = State.ERROR;
                return nextToken();
        }
    }

    private Token getTokenFromNumber() {
        int current = getSymbol();
        while (Character.isDigit((char) current)) {
            number = number * 10 + current - (int) '0';
            current = getSymbol();
        }
        state = State.START;
        save(current);
        return new NumberToken(number);
    }

    private Token nextToken() {
        Token newToken;
        switch (state) {
            case START:
                newToken = getTokenFromStart();
                break;
            case NUMBER:
                newToken = getTokenFromNumber();
                break;
            case ERROR:
                throw new RuntimeException("Invalid symbol");
            case END:
                return null;
            default:
                throw new RuntimeException("Unexpected state: " + state);
        }
        return newToken;
    }

    public List<Token> getTokens() {
        List<Token> list = new ArrayList<>();
        while (true) {
            Token token = nextToken();
            if (token == null) {
                return list;
            } else {
                list.add(token);
            }
        }
    }
}
