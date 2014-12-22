package k.voyage;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ButtonBuilder;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * @author Siryy Sergiy
 *         created in 29.11.2014
 */
public class Window extends Application {

    private Stage stage;
    private Canvas canvas;
    private double scale;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        this.canvas = new Canvas(2000, 2000);
        this.canvas.setLayoutX(100);
        this.canvas.setLayoutY(20);
        this.stage.setScene(createSceen());
        this.scale = 5;
        this.stage.show();
        Test.window = this;
    }

    private Scene createSceen() {
        Group root = new Group();
        Scene scene = new Scene(root, 800, 600);
        root.getChildren().add(canvas);

        root.getChildren().addAll(ButtonBuilder.create()
                        .text("Points")
                        .onAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                try {
                                    Test.main(new String[0]);
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                drawPoints(Test.points.points);
                            }
                        })
                        .build(),
                ButtonBuilder.create()
                        .text("Lines")
                        .onAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                drawLines(Test.points.getNext());
                                drawPoints(Test.points.points);
                                //drawLines(Test.getPath());
                            }
                        })
                        .layoutY(50)
                        .build()
        );
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                drawLines(Test.points.getNext());
                drawPoints(Test.points.points);

            }
        });
        return scene;
    }


    public void drawLines(List<Point> points) {
        canvas.getGraphicsContext2D().setFill(Color.WHITE);
        canvas.getGraphicsContext2D().fillRect(0, 0, canvas.getWidth(), canvas.getWidth());
        canvas.getGraphicsContext2D().setStroke(Color.GREEN);
        for (int i = 1; i < points.size(); i++) {
            canvas.getGraphicsContext2D().strokeLine(points.get(i - 1).x / scale, points.get(i - 1).y / scale,
                    points.get(i).x / scale, points.get(i).y / scale);
        }
        drawPoints(points);
    }

    public void drawPoints(List<Point> points) {
        canvas.getGraphicsContext2D().setFill(Color.BLACK);
        for (Point point : points) {
            canvas.getGraphicsContext2D().fillOval((point.x - 1) / scale, (point.y - 1) / scale, 2, 2);
        }
    }


    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        launch(args);
    }

}
