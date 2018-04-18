/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connect4;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 *
 * @author AlexiaDura
 */
public class Disk extends Circle {

    private static final int TILE_SIZE = 86;

    public final boolean red;

    public Disk(boolean red) {
        super(TILE_SIZE / 2, red ? Color.CORNFLOWERBLUE : Color.RED);
        this.red = red;
        setCenterX(TILE_SIZE / 2);
        setCenterY(TILE_SIZE / 2);
    }

}
