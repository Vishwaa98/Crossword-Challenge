package com.example.crosswordchallenge;

import com.example.crosswordchallenge.Direction;

public class Possibility {
    Direction direction;
    int row;
    int col;

    public Possibility(Direction direction, int row, int col){
        this.direction = direction;
        this.row = row;
        this.col = col;
    }

    public Direction getDirection(){
        return direction;
    }

    public int getRow(){
        return row;
    }

    public int getCol() {
        return col;
    }
}
