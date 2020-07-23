package minigame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
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

        monsters = new ArrayList<Monster>();
        boardWidth = 150;
        boardLength = 150;
        Pane board = new Pane();
        board.setPrefSize(boardWidth, boardLength);

        Player player = new Player(1, 1);
        item = createItem();
        Monster monster = createMonster();
        monsters.add(monster);

        board.getChildren().add(player.getGameObject());
        board.getChildren().add(item.getGameObject());
        board.getChildren().add(monster.getGameObject());

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
                    if (player.getGameObject().getBoundsInParent().getMinX() > 0) {
                        player.move(-1, 0);
                    }
                }
                if (keysPressed.getOrDefault(KeyCode.D, false)) {
                    if (player.getGameObject().getBoundsInParent().getMaxX() < boardWidth) {
                        player.move(1, 0);
                    }
                }
                if (keysPressed.getOrDefault(KeyCode.W, false)) {
                    if (player.getGameObject().getBoundsInParent().getMinY() > 0) {
                        player.move(0, -1);
                    }
                }
                if (keysPressed.getOrDefault(KeyCode.S, false)) {
                    if (player.getGameObject().getBoundsInParent().getMaxY() < boardLength) {
                        player.move(0, 1);
                    }
                }
                if (player.collide(item)) {
                    board.getChildren().remove(item.getGameObject());
                    Item newItem = createItem();
                    item.setGameObject(newItem.getGameObject());
                    board.getChildren().add(item.getGameObject());
                    Monster newMonster = createMonster();
                    board.getChildren().add(newMonster.getGameObject());
                    monsters.add(newMonster);
                }
                monsters.forEach(monster -> {
                    moveMonster(monster);
                    if (player.collide(monster)) {
                        stop();
                    }
                });
            }
        }
                .start();

    }

    private static Item createItem() {
        pieceLottery = new Random();
        int itemX = pieceLottery.nextInt(boardWidth - 1);
        int itemY = pieceLottery.nextInt(boardLength - 1);

        Item item = new Item(itemX, itemY);
        return item;
    }

    private static Monster createMonster() {
        pieceLottery = new Random();
        int monsterX = pieceLottery.nextInt(boardWidth - 5);
        int monsterY = pieceLottery.nextInt(boardLength - 5);

        Monster monster = new Monster(monsterX, monsterY);
        return monster;
    }

    private static void moveMonster(Monster monster) {
        pieceLottery = new Random();
        int direction = 1 + pieceLottery.nextInt(4);
        if (direction == 1) {
            if (monster.getGameObject().getBoundsInParent().getMinX() > 0) {
                monster.move(-1, 0);
            }
        }
        if (direction == 2) {
            if (monster.getGameObject().getBoundsInParent().getMaxX() < boardWidth) {
                monster.move(1, 0);
            }
        }
        if (direction == 3) {
            if (monster.getGameObject().getBoundsInParent().getMinY() > 0) {
                monster.move(0, -1);
            }
        }
        if (direction == 4) {
            if (monster.getGameObject().getBoundsInParent().getMaxY() < boardLength) {
                monster.move(0, 1);
            }
        }
    }

    public static void main(String[] args) {
        launch(MiniGame.class);
    }
}
