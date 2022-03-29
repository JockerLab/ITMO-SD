package drawing;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class FxDrawing extends Application implements DrawingApi {
    private static GraphicsContext context;
    private final int WIDTH = 700;
    private final int HEIGHT = 700;
    private final float THICKNESS = 2.0f;

    public FxDrawing() {
        if (context == null) {
            Canvas canvas = new Canvas(WIDTH, HEIGHT);
            context = canvas.getGraphicsContext2D();
            context.setLineWidth(THICKNESS);
        }
    }

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        root.getChildren().add(context.getCanvas());
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void run() {
        Thread thread = new Thread(FxDrawing::launch);
        thread.start();
    }

    @Override
    public long getDrawingAreaWidth() {
        return WIDTH;
    }

    @Override
    public long getDrawingAreaHeight() {
        return HEIGHT;
    }

    @Override
    public void drawCircle(long x, long y, long r) {
        context.setFill(Color.BLACK);
        context.fillOval(x - r, y - r, 2.0d * r, 2.0d * r);
    }

    @Override
    public void drawLine(long x1, long y1, long x2, long y2) {
        context.setStroke(Color.BLACK);
        context.strokeLine(x1, y1, x2, y2);
    }
}
