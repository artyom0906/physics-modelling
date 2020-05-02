package sample;

import com.sun.javafx.geom.Vec2d;
import com.sun.scenario.effect.ImageData;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import toxi.geom.Vec2D;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    Canvas canvas = new Canvas();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        //Image image = new Image(Main.class.getResourceAsStream("icon.jpg"));
        gc.setLineWidth(2.0);
        gc.setFill(Color.RED);
        final List<Point>[] points = new List[]{new ArrayList<>()};
        points[0].add(new Point(new Vec2D(0, 0), new Vec2D(1, 1)));
        points[0].add(new Point(new Vec2D(200, 0), new Vec2D(-1, 1)));
       /* points[0].add(new Point(new Vec2D(200, 100), new Vec2D(-1, 0)));
        points[0].add(new Point(new Vec2D(400, 100), new Vec2D(1, 0)));*/
        Random r = new Random();
        double t = r.nextFloat() * 2 * Math.PI - Math.PI;
        float vx = 0.5f;
        float vy = 0.5f;
        //points[0].add(new Point(new Vec2D(r.nextFloat() * 600, r.nextFloat()*400), new Vec2D((float) (vx * Math.sin(t)), (float) (vy * Math.cos(t)))));
        for (int i = 0; i < 100; i++) {
            t = r.nextFloat() * 2 * Math.PI - Math.PI;
            //points[0].add(new Point(new Vec2D(r.nextFloat() * 600, r.nextFloat()*400), new Vec2D((float) (vx * Math.sin(t)), (float) (vy * Math.cos(t)))));
        }
        new Thread(() -> {
            while (!Thread.interrupted()) {
                try {
                    gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                    //gc.drawImage(image, 0, 0);

                    points[0].forEach(point -> {
                        gc.fillOval(point.pos.x, point.pos.y, 10, 10);
                    });

                    Thread.sleep(10);
                    List<Point> points1 = new ArrayList(points[0]);
                    int p = 0;
                    for (Point point : points[0]) {
                        point.move();
                        if (point.pos.x >= 600 || point.pos.x <= 0)
                            point.getSpeed().reflect(new Vec2D(0, -1));
                        if (point.pos.y >= 400 || point.pos.y <= 0)
                            point.getSpeed().reflect(new Vec2D(-1, 0));
                        for(Point point1: points[0]) {
                            if (point.isCollide(point1) && !point.equals(point1)) {
                                points1.get(p).changeDirection(point1);
                            }
                        }
                        p++;
                    }
                    points[0] = points1;
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }).start();
    }
}
