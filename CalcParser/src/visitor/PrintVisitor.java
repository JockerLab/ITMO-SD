package visitor;

import token.Brace;
import token.NumberToken;
import token.Operation;
import token.Token;

import java.io.PrintStream;

public class PrintVisitor implements TokenVisitor {
    private final PrintStream output;

    public PrintVisitor(PrintStream out) {
        output = out;
    }

    private void visitAll(Token token) {
        output.print(token.toString() + " ");
    }

    @Override
    public void visit(NumberToken token) {
        visitAll(token);
    }

    @Override
    public void visit(Brace token) {
        visitAll(token);
    }

    @Override
    public void visit(Operation token) {
        visitAll(token);
    }
}
