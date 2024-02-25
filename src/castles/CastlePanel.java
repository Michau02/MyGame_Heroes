package castles;

import main.GamePanel;

import java.awt.*;

public class CastlePanel {
    GamePanel gp;
    public int width, height, curveAngle = 45;
    public CastlePanel(GamePanel gp){
        this.gp = gp;
        width = 10*gp.tileSize;
        height = Math.min((gp.screenHeight - 3 * gp.tileSize), 24*gp.tileSize);
    }
/*    ImageIcon icon = new ImageIcon("\"/res/castles/people/Zamek_Ludzie_Tier1_1.png\"");
    JButton button = new JButton(icon);*/

//INACZEJ TO ZROBIC (RYI SNOW INVENTORY)
    public void draw(Graphics2D g2) {

        /* // wszystko docelowo nie tu wiec nom
        for(Building building : gp.cas[0].buildings){
            g2.setColor(Color.white);
            g2.drawString(building.name, 50,50);
        }
         */

    }
}
