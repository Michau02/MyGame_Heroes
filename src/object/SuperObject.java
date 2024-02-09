package object;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SuperObject {

    public BufferedImage image;
    public String name;
    public int worldX, worldY;

    public void draw(Graphics2D g2, GamePanel gp) {
        g2.drawImage(image, worldX - gp.player.worldX + gp.player.screenX - gp.player.mapOffsetX, worldY - gp.player.worldY + gp.player.screenY - gp.player.mapOffsetY, gp.tileSize, gp.tileSize, null);
    }

}