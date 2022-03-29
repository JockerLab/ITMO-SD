package graph;

import drawing.DrawingApi;

import java.io.*;

public class MatrixGraph extends Graph {
    private int[][] matrix;
    private final String MATRIX_FILE = "graph_matrix.txt";

    public MatrixGraph(DrawingApi drawingApi) {
        super(drawingApi);
    }

    public void drawGraph() {
        for (int i = 0; i < nodes; ++i) {
            for (int j = 0; j < i; ++j) {
                if (matrix[i][j] == 1) {
                    drawEdge(i, j);
                }
            }
        }
        drawNodes();
    }

    public void loadGraph() {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(MATRIX_FILE)) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
                String line;
                boolean isDimension = true;
                int i = 0;
                while ((line = reader.readLine()) != null) {
                    if (isDimension) {
                        nodes = Integer.parseInt(line.trim());
                        matrix = new int[nodes][];
                        isDimension = false;
                    } else {
                        matrix[i] = new int[nodes];
                        String[] input = line.trim().split(" ");
                        for (int j = 0; j < nodes; j++) {
                            matrix[i][j] = Integer.parseInt(input[j]);
                        }
                        i++;
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
