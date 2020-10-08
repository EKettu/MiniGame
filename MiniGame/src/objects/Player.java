package objects;

import javafx.scene.shape.Polygon;

public class Player extends GameObject {
    
    public Player(int x, int y) {
        super(new Polygon(0, 0, 20, 0, 20, 20, 0, 20), javafx.scene.paint.Color.BLACK, x, y);
    }
    
}

