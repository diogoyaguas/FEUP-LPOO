package com.cluedo.game.model.Tile;

import com.cluedo.game.model.Board;



/**
 * This class represents a single tile on the game board
 *
 */
public class Tile extends Position {

    public int x, y;


    /**
     * class constructor
     * @param x
     * @param y
     */
    public Tile(int x, int y) {
        super();

        int width = Board.BOARD_WIDTH;
        int height = Board.BOARD_HEIGHT;
        if (x < 0 || x > width - 1 || y < 0 || y > height - 1) {
            return;
        }

        this.x = x;
        this.y = y;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + x;
        result = prime * result + y;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Tile other = (Tile) obj;
        return x == other.x && y == other.y;
    }

}
