package token;

public class Minus implements Operation {
    public String toString() {
        return "MINUS";
    }

    @Override
    public int calc(int first, int second) {
        return first - second;
    }

    @Override
    public int prior() {
        return 0;
    }
}
