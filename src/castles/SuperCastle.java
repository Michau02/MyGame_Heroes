package castles;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class SuperCastle {

    public BufferedImage image;
    public String name;
    public int worldX, worldY;
    public ArrayList<Building> buildings = new ArrayList<>();
    public int income = 500;

    public void loadBuildings(){
        buildings.add(new Building("strzelnica"));
        buildings.add(new Building("stajnia"));
    }

    public void draw(Graphics2D g2, GamePanel gp) {
        g2.drawImage(image, worldX - gp.player.worldX + gp.player.screenX - gp.player.mapOffsetX, worldY - gp.player.worldY + gp.player.screenY - gp.player.mapOffsetY, 3*gp.tileSize, 2*gp.tileSize, null);
    }
}
