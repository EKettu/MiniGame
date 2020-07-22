package objects;

import javafx.scene.shape.Polygon;

public class Player extends GameObject {
    
    public Player(int x, int y) {
        super(new Polygon(0, 0, 10, 0, 10, 10, 0, 10), javafx.scene.paint.Color.BLACK, x, y);
    }
    
}

