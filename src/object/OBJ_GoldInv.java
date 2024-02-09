package object;

import main.GamePanel;

import javax.imageio.ImageIO;

import java.awt.*;
import java.io.IOException;

public class OBJ_GoldInv extends SuperObject{

    public OBJ_GoldInv(){
        name = "GoldInv";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/Gold_4.png"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void draw(Graphics2D g2, GamePanel gp) {
        g2.drawImage(image, worldX, worldY, gp.tileSize, gp.tileSize, null);
    }
}