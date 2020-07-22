package minigame;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import objects.Item;
import objects.Monster;
import objects.Player;

public class MiniGame extends Application {
    
    private List<Monster> monsters;
    private static Random pieceLottery;
    
    private static Item item;
    
    private static int boardWidth;
    private static int boardLength;

    @Override
    public void start(Stage stage) {
        
        boardWidth = 100;
        boardLength = 100;
        Pane board = new Pane();
        board.setPrefSize(boardWidth, boardLength);

        Player player = new Player(1, 1);
        item = createItem();
        board.getChildren().add(player.getGameObject());
        board.getChildren().add(item.getGameObject());

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
                if(player.collide(item)) {
                    board.getChildren().remove(item.getGameObject());
                    Item newItem = createItem();
                    item.setGameObject(newItem.getGameObject());
                    board.getChildren().add(item.getGameObject());
                }
            }
        }
                .start();
    }
    
    private static Item createItem() {
        pieceLottery = new Random();
        int itemX = pieceLottery.nextInt(boardWidth);
        int itemY = pieceLottery.nextInt(boardLength);
        
        Item item = new Item(itemX, itemY);
        return item;
    }

    public static void main(String[] args) {
        launch(MiniGame.class);
    }
}