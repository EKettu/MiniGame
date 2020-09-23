package minigame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Button;
import javafx.scene.control.TextField; 
import javafx.stage.Stage;
import objects.Hunter;
import objects.Item;
import objects.Monster;
import objects.Player;

public class MiniGame extends Application {

    private List<Monster> monsters;
    private static Random pieceLottery;
    private static BorderPane border;
    private static Pane board;
    private static Item item;
    private static Player player;

    private static int boardWidth;
    private static int boardLength;

    private boolean hunterAlive;
    private static Hunter hunter;

    @Override
    public void start(Stage stage) {

        Map<KeyCode, Boolean> keysPressed = new HashMap<>();
        border = new BorderPane();
        boardWidth = 150;
        boardLength = 150;
        board = new Pane();
        board.setPrefSize(boardWidth, boardLength);

        Button start = new Button("New Game");
        Button quit = new Button("Quit");
        TextField text = new TextField("Points: 0");
        
        AtomicInteger points = new AtomicInteger();
        border.setLeft(start);
        border.setRight(quit);
        border.setCenter(text);
        border.setBottom(board);
        Scene scene = new Scene(border);
        scene.setOnKeyPressed(event -> {
            keysPressed.put(event.getCode(), Boolean.TRUE);
        });
        scene.setOnKeyReleased(event -> {
            keysPressed.put(event.getCode(), Boolean.FALSE);
        });
        quit.setOnAction((event) -> {
            stage.close();
        });
        start.setOnAction((event) -> {
            if(monsters!=null) {
                clearTheBoard();
            }
            initializeTheBoard();
            stage.setScene(scene);
            stage.show();

            new AnimationTimer() {

                @Override
                public void handle(long now
                ) {
                    movePlayer(keysPressed, player);
                    if (hunterAlive) {
                        hunter.hunt();
                        if (player.collide(hunter)) {
                            stop();
                        }
                    }
                    if (player.collide(item)) {
                        if (hunterAlive) {
                            board.getChildren().remove(hunter.getGameObject());
                            hunterAlive = false;
                        }
                        if (hunterSpawned()) {
                            addHunter(board, hunter, player);
                        }
                        addItem(board, item);
                        addMonster(board);
                        text.setText("Points: " + points.addAndGet(10));
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

        });

        stage.setScene(scene);
        stage.show();
    }

    private void clearTheBoard() {
        board.getChildren().remove(player.getGameObject());
        board.getChildren().remove(item.getGameObject());
        monsters.forEach(monster -> {
            board.getChildren().remove(monster.getGameObject());
        });
        if (hunterAlive) {
            board.getChildren().remove(hunter.getGameObject());
        }

    }

    private void initializeTheBoard() {
        monsters = new ArrayList<Monster>();
        hunterAlive = false;
        player = new Player(1, 1);
        item = createItem();
        Monster monster = createMonster();
        monsters.add(monster);
        hunter = new Hunter(100, 100);

        board.getChildren().add(player.getGameObject());
        board.getChildren().add(item.getGameObject());
        board.getChildren().add(monster.getGameObject());
    }

    private static Item createItem() {
        pieceLottery = new Random();
        int itemX = pieceLottery.nextInt(boardWidth - 2);
        int itemY = pieceLottery.nextInt(boardLength - 2);

        Item item = new Item(itemX, itemY);
        return item;
    }

    private static Monster createMonster() {
        pieceLottery = new Random();
        int monsterX = pieceLottery.nextInt(boardWidth - 5);
        int monsterY = pieceLottery.nextInt(boardLength - 5);
        while (isMonsterTooClose(monsterX, monsterY)) {
            monsterX = pieceLottery.nextInt(boardWidth - 5);
            monsterY = pieceLottery.nextInt(boardLength - 5);
        }
        Monster monster = new Monster(monsterX, monsterY);
        return monster;
    }

    private static boolean isMonsterTooClose(int monsterX, int monsterY) {
        if ((monsterX > player.getGameObject().getTranslateX() - 2
                && monsterX < player.getGameObject().getTranslateX() + 2) && 
                (monsterY > player.getGameObject().getTranslateY() - 2
                && monsterY < player.getGameObject().getTranslateY() + 2)) {
            return true;
        }
        return false;
    }

    private void movePlayer(Map<KeyCode, Boolean> keysPressed, Player player) {
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

    private boolean hunterSpawned() {
        int probability = 1 + pieceLottery.nextInt(6);
        if (probability == 5) {
            hunterAlive = true;
            return true;
        }
        return false;
    }

    private void addItem(Pane board, Item item) {
        board.getChildren().remove(item.getGameObject());
        Item newItem = createItem();
        item.setGameObject(newItem.getGameObject());
        board.getChildren().add(item.getGameObject());
    }

    private void addMonster(Pane board) {
        Monster newMonster = createMonster();
        board.getChildren().add(newMonster.getGameObject());
        monsters.add(newMonster);
    }

    private void addHunter(Pane board, Hunter hunter, Player player) {
        board.getChildren().remove(hunter.getGameObject());
        Hunter newHunter = new Hunter(100, 100);
        hunter.setGameObject(newHunter.getGameObject());
        hunter.setTarget(player.getGameObject());
        board.getChildren().add(hunter.getGameObject());
    }

    public static void main(String[] args) {
        launch(MiniGame.class);
    }
}
