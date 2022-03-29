package graph;

public class Edge {
    public int first, second;

    public Edge(int first, int second) {
        if (first < second) {
            this.first = first;
            this.second = second;
        } else {
            this.first = second;
            this.second = first;
        }
    }
}
