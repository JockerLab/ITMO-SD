package token;

public class Divide implements Operation {
    public String toString() {
        return "DIV";
    }

    @Override
    public int calc(int first, int second) {
        return first / second;
    }

    @Override
    public int prior() {
        return 1;
    }
}
