package graph;

import drawing.DrawingApi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class EdgeGraph extends Graph {
    private List<Edge> list;
    private final String EDGE_FILE = "graph_edge.txt";


    public EdgeGraph(DrawingApi drawingApi) {
        super(drawingApi);
    }

    public void drawGraph() {
        for (Edge e : list) {
            drawEdge(e.first, e.second);
        }
        drawNodes();
    }

    public void loadGraph() {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(EDGE_FILE)) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
                list = new ArrayList<>();
                String line;
                boolean isDimension = true;
                int edges = 0;
                while ((line = reader.readLine()) != null) {
                    String[] input = line.trim().split(" ");
                    if (isDimension) {
                        nodes = Integer.parseInt(input[0]);
                        edges = Integer.parseInt(input[1]);
                        isDimension = false;
                    } else {
                        Edge edge = new Edge(Integer.parseInt(input[0]) - 1, Integer.parseInt(input[1]) - 1);
                        list.add(edge);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
