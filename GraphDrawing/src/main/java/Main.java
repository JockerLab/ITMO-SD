import drawing.AwtDrawing;
import drawing.DrawingApi;
import drawing.FxDrawing;
import graph.EdgeGraph;
import graph.Graph;
import graph.MatrixGraph;

public class Main {
    public static void main(String[] args) {
        if (args == null) {
            System.err.println("Arguments cannot be null");
            return;
        }
        if (args.length != 2) {
            System.err.println("Incorrect arguments amount");
            return;
        }

        DrawingApi drawingApi;
        Graph graph;
        switch (args[0]) {
            case "awt":
                drawingApi = new AwtDrawing();
                break;
            case "fx":
                FxDrawing.run();
                drawingApi = new FxDrawing();
                break;
            default:
                System.err.println("Incorrect drawing api: " + args[0]);
                return;
        }
        switch (args[1]) {
            case "edge":
                graph = new EdgeGraph(drawingApi);
                break;
            case "matrix":
                graph = new MatrixGraph(drawingApi);
                break;
            default:
                System.err.println("Incorrect drawing api: " + args[0]);
                return;
        }
        graph.drawGraph();
    }
}
