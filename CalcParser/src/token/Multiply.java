package token;

public class Multiply implements Operation {
    public String toString() {
        return "MUL";
    }

    @Override
    public int calc(int first, int second) {
        return first * second;
    }

    @Override
    public int prior() {
        return 1;
    }
}
