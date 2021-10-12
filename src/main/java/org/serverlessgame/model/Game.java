package org.serverlessgame.model;

public class Game {
    private String id;
    private int position;

    public Game(String id){
        this.id = id;
    }

    public void move(int dist){
        position += dist;
    }

    public int getPosition(){
        return position;
    }
}
