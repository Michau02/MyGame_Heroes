package main;

import object.SuperObject;

import java.awt.*;

public class UI {

    GamePanel gp;
    Font arialITALIC_18, italicBOLD_20, italicBOLD_40;
    FontMetrics fontMetrics;

    public UI(GamePanel gp){
        this.gp = gp;

        arialITALIC_18 = new Font("Arial", Font.ITALIC, 18);
        italicBOLD_20 =new Font("Italic", Font.BOLD, 20);
        italicBOLD_40 =new Font("Italic", Font.BOLD, 40);
        fontMetrics = gp.getFontMetrics(italicBOLD_40);
    }
    public void draw(Graphics2D g2){
        drawResources(g2);
        if(gp.gameState == gp.castleState){
            drawCastlePanel(g2);
        }
        else if(gp.gameState == gp.pauseState){
            drawPausePanel(g2);
        }
        else if(gp.gameState == gp.settingsState){
            drawSettingsPanel(g2);
        }
    }
    public void drawResources(Graphics2D g2){
        g2.setColor(new Color(100,75,60,130));
        g2.fillRoundRect(-1, (gp.maxWorldRow-1)*gp.tileSize, 27*gp.tileSize, gp.tileSize, 45,45);

        for (SuperObject superObject : gp.objInv) {
            if (superObject != null)
                superObject.draw(g2, gp);
        }

        g2.setFont(arialITALIC_18);
        g2.setColor(Color.white);
        g2.drawString("x " + gp.player.wood, gp.tileSize + gp.tileSize/2, (int)(((double)gp.maxWorldRow-0.25)*gp.tileSize));
        g2.drawString("x " + gp.player.gold, 3*gp.tileSize + gp.tileSize/2, (int)(((double)gp.maxWorldRow-0.25)*gp.tileSize));

        //FPSy
        String s = "FPS: " + gp.FPS;
        g2.setColor(Color.BLUE);
        g2.setFont(italicBOLD_20);
        g2.drawString(s, gp.screenWidth - g2.getFontMetrics().stringWidth(s), g2.getFontMetrics().getAscent());
    }
    public void drawCastlePanel(Graphics2D g2){

    }
    public void drawPausePanel(Graphics2D g2){
        g2.setFont(italicBOLD_40);
        g2.setColor(Color.WHITE);
        String pause = "PAUSE";
        g2.drawString(pause, gp.screenWidth/2 - fontMetrics.stringWidth(pause)/2, (gp.screenHeight - fontMetrics.getAscent())/2);
    }
    public void drawSettingsPanel(Graphics2D g2){

    }
}
