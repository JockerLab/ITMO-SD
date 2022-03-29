package drawing;


public interface DrawingApi {
    long getDrawingAreaWidth();
    long getDrawingAreaHeight();
    void drawCircle(long x, long y, long r);
    void drawLine(long x1, long y1, long x2, long y2);
}
