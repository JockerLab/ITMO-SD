package visitor;

import token.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ParserVisitor implements TokenVisitor {
    private final Stack<Token> stack = new Stack<>();
    private final List<Token> output = new ArrayList<>();

    public void afterAll() {
        while (!stack.empty()) {
            output.add(stack.pop());
        }
    }

    public List<Token> getNotation() {
        return output;
    }

    @Override
    public void visit(NumberToken token) {
        output.add(token);
    }

    @Override
    public void visit(Brace token) {
        if (token instanceof Left) {
            stack.push(token);
        } else {
            while (!stack.empty()) {
                if (stack.peek() instanceof Left) {
                    break;
                }
                output.add(stack.pop());
            }
            if (stack.empty()) {
                throw new RuntimeException("Incorrect expression");
            } else {
                stack.pop();
            }
        }
    }

    @Override
    public void visit(Operation token) {
        while (!stack.empty() && (stack.peek() instanceof Operation) && ((Operation) stack.peek()).prior() > token.prior()) {
            output.add(stack.pop());
        }
        stack.push(token);
    }
}
