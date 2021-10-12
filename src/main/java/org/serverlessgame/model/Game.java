package org.serverlessgame.model;

public class Game {
    private int id;
    private int position;

    public Game(int id){
        this.id = id;
    }

    public void move(int dist){
        position += dist;
    }

    public int getPosition(){
        return position;
    }
}
