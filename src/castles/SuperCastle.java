package castles;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SuperCastle {

    public BufferedImage image;
    public String name;
    public int worldX, worldY;
    GamePanel gp;

    public void draw(Graphics2D g2, GamePanel gp) {
        g2.drawImage(image, worldX - gp.player.worldX + gp.player.screenX - gp.player.mapOffsetX, worldY - gp.player.worldY + gp.player.screenY - gp.player.mapOffsetY, 3*gp.tileSize, 2*gp.tileSize, null);
        this.gp = gp;
    }
}
