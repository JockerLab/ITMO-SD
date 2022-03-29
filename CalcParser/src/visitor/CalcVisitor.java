package visitor;

import token.Brace;
import token.NumberToken;
import token.Operation;
import token.Token;

import java.util.Stack;

public class CalcVisitor implements TokenVisitor {
    private final Stack<Integer> stack = new Stack<>();

    public int getResult() {
        if (stack.size() > 1) {
            throw new RuntimeException("Incorrect expression");
        }
        return stack.peek();
    }

    @Override
    public void visit(NumberToken token) {
        stack.push(token.getNumber());
    }

    @Override
    public void visit(Brace token) {
        throw new RuntimeException("Incorrect expression");
    }

    @Override
    public void visit(Operation token) {
        if (stack.size() < 2) {
            throw new RuntimeException("Incorrect expression");
        }
        int first = stack.pop();
        int second = stack.pop();
        stack.push(token.calc(second, first));
    }
}
