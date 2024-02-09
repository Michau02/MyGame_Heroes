package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class OBJ_Gold extends SuperObject{
    int counter = 0;
    int innerCounter = 0;
    BufferedImage image1, image2, image3;

    public OBJ_Gold(){
        name = "Gold";
        try{
            image1 = ImageIO.read(getClass().getResourceAsStream("/res/objects/Gold_1.png"));
            image2 = ImageIO.read(getClass().getResourceAsStream("/res/objects/Gold_2.png"));
            image3 = ImageIO.read(getClass().getResourceAsStream("/res/objects/Gold_3.png"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    @Override
    public void draw(Graphics2D g2, GamePanel gp){
        if(counter == 40){
            innerCounter++;
            image = image3;

            if(innerCounter <= 16){
                image = image1;
            }
            else if(innerCounter <= 31){
                image = image2;
            }
            else if(innerCounter <= 46){
                image = image1;
            }
            else if(innerCounter <= 61){
                image = image2;
            }
            else if(innerCounter <= 76){
                image = image1;
            }
            else{
                innerCounter = 0;
                counter = 0;
            }

        }
        else {
            counter++;
            image = image3;
        }
        g2.drawImage(image, worldX - gp.player.worldX + gp.player.screenX - gp.player.mapOffsetX, worldY - gp.player.worldY + gp.player.screenY - gp.player.mapOffsetY, gp.tileSize, gp.tileSize, null);
    }
}
