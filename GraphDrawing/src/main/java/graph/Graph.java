package graph;

import drawing.DrawingApi;

public abstract class Graph {
    /**
     * Bridge to drawing api
     */
    private DrawingApi drawingApi;
    public int nodes = 0;

    public Graph(DrawingApi drawingApi) {
        this.drawingApi = drawingApi;
        loadGraph();
    }

    public abstract void drawGraph();

    public abstract void loadGraph();

    private long getCircleXPos(long width, long height, int v) {
        long wC = width / 2;
        long r = Math.min(width, height) / 4;
        double angle = (2 * v * Math.PI) / nodes;
        return wC + (int) (r * Math.cos(angle));
    }

    private long getCircleYPos(long width, long height, int v) {
        long hC = height / 2;
        long r = Math.min(width, height) / 4;
        double angle = (2 * v * Math.PI) / nodes;
        return (hC + (int) (r * Math.sin(angle)));
    }

    public void drawNodes() {
        long width = drawingApi.getDrawingAreaWidth();
        long height = drawingApi.getDrawingAreaHeight();
        long circleRadius = Math.min(width, height) / (8L * nodes);
        for (int i = 0; i < nodes; ++i) {
            drawingApi.drawCircle(
                    getCircleXPos(width, height, i),
                    getCircleYPos(width, height, i),
                    circleRadius);
        }
    }

    public void drawEdge(int p1, int p2) {
        long width = drawingApi.getDrawingAreaWidth();
        long height = drawingApi.getDrawingAreaHeight();
        drawingApi.drawLine(getCircleXPos(width, height, p1),
                getCircleYPos(width, height, p1),
                getCircleXPos(width, height, p2),
                getCircleYPos(width, height, p2));
    }
}
