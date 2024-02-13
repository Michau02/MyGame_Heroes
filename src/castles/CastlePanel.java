package castles;

import main.GamePanel;

import javax.swing.*;
import java.awt.*;

public class CastlePanel {
    GamePanel gp;
    int width, height, curveAngle = 45;
    public CastlePanel(GamePanel gp){
        this.gp = gp;
        width = 10*gp.tileSize;
        height = gp.screenHeight - 3*gp.tileSize;
    }
/*    ImageIcon icon = new ImageIcon("\"/res/castles/people/Zamek_Ludzie_Tier1_1.png\"");
    JButton button = new JButton(icon);*/

//INACZEJ TO ZROBIC (RYI SNOW INVENTORY)
    public void draw(Graphics2D g2) {
        //panel glowny
        g2.setColor(Color.GRAY);
        g2.fillRoundRect(0, 0, width, height, curveAngle, curveAngle);

        //siatka na budynki
        //x od 1 do 4 i y od height/2 + 1/9height do 1/2height + 1/9 height + 1/3height
        //x od 6 do 9 i y od  1/2height + 2/9 height + 1/3height do height - 1.9height
    }
}
