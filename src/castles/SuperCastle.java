package castles;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class SuperCastle {

    public BufferedImage image;
    public String name;
    public int worldX, worldY;
    public ArrayList<Building> buildings = new ArrayList<>();

    public void loadBuildings(){
        try{
            Building building = new Building("strzelnica");
            building.image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/buildings/strzelnica.png")));
            buildings.add(building);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2, GamePanel gp) {
        g2.drawImage(image, worldX - gp.player.worldX + gp.player.screenX - gp.player.mapOffsetX, worldY - gp.player.worldY + gp.player.screenY - gp.player.mapOffsetY, 3*gp.tileSize, 2*gp.tileSize, null);
    }
}
