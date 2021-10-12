package org.serverlessgame.model;

public class StaticGame {
    public static int position;

    public static void move(int dist){
        position += dist;
    }

    public static int getPosition(){
        return position;
    }
}
