package org.serverlessgame.model;

public class Game {
    private int position;

    public void move(int dist){
        position += dist;
    }

    public int getPosition(){
        return position;
    }
}
