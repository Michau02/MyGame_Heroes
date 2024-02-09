package tile;

import main.GamePanel;

import java.awt.image.BufferedImage;

public class Tile {
    GamePanel gp;

    public Tile(){

    }
    public BufferedImage image;
    public boolean collision = false;
    public double movementFactor = 1.0; // dla piasku np 1.5 albo na bagnach 2.0 no ogolnie utrudnienia w ruchu ale do ustawienia w TileManager dla kazdej plytki osobno

}
