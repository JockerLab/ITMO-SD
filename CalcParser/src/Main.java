import token.Token;
import visitor.CalcVisitor;
import visitor.ParserVisitor;
import visitor.PrintVisitor;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Tokenizer tokenizer = new Tokenizer(System.in);
        ParserVisitor parser = new ParserVisitor();
        PrintVisitor print = new PrintVisitor(System.out);
        CalcVisitor calc = new CalcVisitor();
        List<Token> tokens = tokenizer.getTokens();
        for (Token token : tokens) {
            token.accept(parser);
        }
        parser.afterAll();
        List<Token> notation = parser.getNotation();
        for (Token token : notation) {
            token.accept(print);
        }
        for (Token token : notation) {
            token.accept(calc);
        }
        System.out.println("\n" + calc.getResult());
    }
}
