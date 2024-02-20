package main;

import castles.SuperCastle;
import object.SuperObject;

import java.awt.*;

public class UI {

    GamePanel gp;
    Font arialITALIC_18, italicBOLD_20, italicBOLD_40;
    FontMetrics fontMetrics;
    static String name;
    static String type = "null";

    public UI(GamePanel gp){
        this.gp = gp;

        arialITALIC_18 = new Font("Arial", Font.ITALIC, 18);
        italicBOLD_20 =new Font("Italic", Font.BOLD, 20);
        italicBOLD_40 =new Font("Italic", Font.BOLD, 40);
        fontMetrics = gp.getFontMetrics(italicBOLD_40);
    }
    public void draw(Graphics2D g2){
        //trzeba jeszcze ustawic czcionke tu i zmienic x, y, width, height w fillRect i drawString w zaleznosci od fontMetrics ustawionej czcionki
        drawResources(g2);
        if(gp.mouseHandler.rightButtonPressed){
            type = "null";
            g2.setColor(Color.gray);
            g2.fillRect(gp.mouseX*gp.tileSize - gp.player.worldX + gp.player.screenX - gp.player.mapOffsetX - gp.tileSize/2, gp.mouseY*gp.tileSize - gp.player.worldY + gp.player.screenY - gp.player.mapOffsetY-gp.tileSize, 2*gp.tileSize, gp.tileSize);
            for(SuperObject object : gp.obj){
                if(object.worldX/gp.tileSize == gp.mouseX && object.worldY/gp.tileSize == gp.mouseY){
                    name = object.name;
                    type = "object";
                    break;
                }
            }
            for(SuperCastle castle : gp.cas){ // tu znajduje tylko lewy gorny rog z jakiegos powodu
                /*if(castle.worldX/gp.tileSize == gp.mouseX && castle.worldY/gp.tileSize == gp.mouseY ||
                        (castle.worldX+gp.tileSize)/gp.tileSize == gp.mouseX && castle.worldY/gp.tileSize == gp.mouseY ||
                        (castle.worldX+2*gp.tileSize)/gp.tileSize == gp.mouseX && castle.worldY/gp.tileSize == gp.mouseY ||
                        castle.worldX/gp.tileSize == gp.mouseX && (castle.worldY+gp.tileSize)/gp.tileSize == gp.mouseY ||
                        (castle.worldX+2*gp.tileSize)/gp.tileSize == gp.mouseX && (castle.worldY+gp.tileSize)/gp.tileSize == gp.mouseY ||

                        ){*/
                if(castle.worldX/gp.tileSize == gp.mouseX || (castle.worldX+gp.tileSize)/gp.tileSize == gp.mouseX || (castle.worldX+2*gp.tileSize)/gp.tileSize == gp.mouseX){
                    if(castle.worldY/gp.tileSize == gp.mouseY || (castle.worldY+gp.tileSize)/gp.tileSize == gp.mouseY) {
                        name = castle.name;
                        type = "object";
                        break;
                    }
                }
            }
            g2.setColor(Color.BLACK);
            if(type.equals("object"))
                g2.drawString(name, gp.mouseX*gp.tileSize - gp.player.worldX + gp.player.screenX - gp.player.mapOffsetX,gp.mouseY*gp.tileSize - gp.player.worldY + gp.player.screenY - gp.player.mapOffsetY - gp.tileSize/3);
            else{
                g2.drawString(gp.tileManager.tile[gp.tileManager.mapTileNum[gp.mouseX][gp.mouseY]].name, gp.mouseX*gp.tileSize - gp.player.worldX + gp.player.screenX - gp.player.mapOffsetX,gp.mouseY*gp.tileSize - gp.player.worldY + gp.player.screenY - gp.player.mapOffsetY - gp.tileSize/3);
            }
        }
        if(gp.gameState == gp.castleState){
            drawCastlePanel(g2);
        }
        else if(gp.gameState == gp.pauseState){
            drawPausePanel(g2);
        }
        else if(gp.gameState == gp.settingsState){
            drawSettingsPanel(g2);
        }
        else if(gp.gameState == gp.menuState){
            drawSettingsPanel(g2);
        }
    }

    public void finishedCampaign(Graphics2D g2){
        //drawing
        g2.setFont(new Font("Arial", Font.PLAIN, 40));
        g2.setColor(Color.RED);
        g2.drawString("You won!", 8*gp.tileSize, 8*gp.tileSize);
        gp.gameState = gp.menuState;
    }
    public void menu(Graphics2D g2){
        //do zmiany ale chciałem sprawdzic tylko czy wgl tu przechodzi
        g2.setColor(Color.WHITE);
        g2.drawString("MENU", 5*gp.tileSize, 5*gp.tileSize);
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
