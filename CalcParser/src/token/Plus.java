package token;

public class Plus implements Operation {
    public String toString() {
        return "PLUS";
    }

    @Override
    public int calc(int first, int second) {
        return first + second;
    }

    @Override
    public int prior() {
        return 0;
    }
}
