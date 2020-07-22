package minigame;

import java.util.HashMap;
import java.util.Map;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import objects.Player;

public class MiniGame extends Application {

    @Override
    public void start(Stage stage) {
        Pane board = new Pane();
        board.setPrefSize(300, 300);

        Player player = new Player(100, 100);

        board.getChildren().add(player.getGameObject());

        Map<KeyCode, Boolean> keysPressed = new HashMap<>();

        Scene scene = new Scene(board);
        stage.setScene(scene);

        scene.setOnKeyPressed(event -> {
            keysPressed.put(event.getCode(), Boolean.TRUE);
        });

        scene.setOnKeyReleased(event -> {
            keysPressed.put(event.getCode(), Boolean.FALSE);
        });

        stage.show();

        Point2D movement = new Point2D(1, 0);

        new AnimationTimer() {

            @Override
            public void handle(long now
            ) {
                if (keysPressed.getOrDefault(KeyCode.A, false)) {
                    player.move(-1, 0);
                }
                if (keysPressed.getOrDefault(KeyCode.D, false)) {
                    player.move(1, 0);
                }
                if (keysPressed.getOrDefault(KeyCode.W, false)) {
                    player.move(0, -1);
                }
                if (keysPressed.getOrDefault(KeyCode.S, false)) {
                    player.move(0, 1);
                }

            }
        }
                .start();
    }

    public static void main(String[] args) {
        launch(MiniGame.class);
    }
}