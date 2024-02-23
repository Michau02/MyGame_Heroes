package main;

import castles.SuperCastle;
import object.SuperObject;

import java.awt.*;

public class UI {

    GamePanel gp;
    Font arialITALIC_18, italicBOLD_20, italicBOLD_40;
    FontMetrics fontMetrics, italicBOLD_20Metrics;
    static String name;
    static String type = "null", date = "null";
    int italicBOLD_20Width, italicBOLD_20Height;

    public UI(GamePanel gp){
        this.gp = gp;

        arialITALIC_18 = new Font("Arial", Font.ITALIC, 18);
        italicBOLD_20 =new Font("Italic", Font.BOLD, 20);
        italicBOLD_40 =new Font("Italic", Font.BOLD, 40);
        fontMetrics = gp.getFontMetrics(italicBOLD_40);
        italicBOLD_20Metrics = gp.getFontMetrics(italicBOLD_20);
        italicBOLD_20Height = italicBOLD_20Metrics.getAscent();
    }
    public void draw(Graphics2D g2){
        drawInterface(g2);
        if(gp.mouseHandler.rightButtonPressed){
            if((gp.mouseX >= 0 && gp.mouseX <= gp.screenWidth && gp.mouseY >= 0 && gp.mouseY <= gp.screenHeight))
                drawProperties(g2);
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
            drawMenu(g2);
        }
    }
    public void drawProperties(Graphics2D g2){
        g2.setFont(italicBOLD_20);
        type = "null";
        for(SuperObject object : gp.obj){
            if(object.worldX/gp.tileSize == gp.mouseX && object.worldY/gp.tileSize == gp.mouseY){
                name = object.name;
                type = "object";
                break;
            }
        }
        for(SuperCastle castle : gp.cas){
            if(castle.worldX/gp.tileSize == gp.mouseX || (castle.worldX+gp.tileSize)/gp.tileSize == gp.mouseX || (castle.worldX+2*gp.tileSize)/gp.tileSize == gp.mouseX){
                if(castle.worldY/gp.tileSize == gp.mouseY || (castle.worldY+gp.tileSize)/gp.tileSize == gp.mouseY) {
                    name = castle.name;
                    type = "object";
                    break;
                }
            }
        }
        if(!type.equals("object"))
            name = gp.tileManager.tile[gp.tileManager.mapTileNum[gp.mouseX][gp.mouseY]].name;

        italicBOLD_20Width = italicBOLD_20Metrics.stringWidth(name);

        g2.setColor(Color.gray);
        g2.fillRect(gp.mouseX*gp.tileSize - gp.player.worldX + gp.player.screenX - gp.player.mapOffsetX - italicBOLD_20Width*7/12, gp.mouseY*gp.tileSize - gp.player.worldY + gp.player.screenY - gp.player.mapOffsetY - italicBOLD_20Height/3*2, italicBOLD_20Width*3/2, 2*italicBOLD_20Height);

        g2.setColor(Color.BLACK);
        g2.drawRect(gp.mouseX*gp.tileSize - gp.player.worldX + gp.player.screenX - gp.player.mapOffsetX - italicBOLD_20Width*7/12, gp.mouseY*gp.tileSize - gp.player.worldY + gp.player.screenY - gp.player.mapOffsetY - italicBOLD_20Height/3*2, italicBOLD_20Width*3/2, 2*italicBOLD_20Height);
        g2.drawString(name, gp.mouseX*gp.tileSize - gp.player.worldX + gp.player.screenX - gp.player.mapOffsetX-italicBOLD_20Width/3,gp.mouseY*gp.tileSize - gp.player.worldY + gp.player.screenY - gp.player.mapOffsetY + 2*italicBOLD_20Height/3);

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
    public void drawInterface(Graphics2D g2){
        drawResources(g2);
        drawCalendar(g2);
        drawMovementPoints(g2);
    }
    public void drawMovementPoints(Graphics2D g2){
        g2.setColor(Color.white);
        g2.setFont(arialITALIC_18);
        g2.drawString("Remaining movement points: " + gp.player.remainingMovementPoints +  "/" + gp.player.movementPoints, gp.tileSize/6, (int)(((double)gp.maxWorldRow-1.25)*gp.tileSize));
    }
    public void drawCalendar(Graphics2D g2){
        g2.setFont(italicBOLD_20);
        date = "Day " + gp.day + ", Week " + gp.week + ", Month " + gp.month;

        g2.setColor(new Color(100,75,60,130));
        g2.fillRoundRect(gp.screenWidth - italicBOLD_20Metrics.stringWidth(date)*5/4, (gp.maxWorldRow-1)*gp.tileSize, italicBOLD_20Metrics.stringWidth(date)*5/4, gp.tileSize, 45,45);

        g2.setColor(Color.WHITE);
        g2.drawString(date, gp.screenWidth - italicBOLD_20Metrics.stringWidth(date)*9/8, (gp.maxWorldRow-1)*gp.tileSize + italicBOLD_20Height*3/2);
    }
    public void drawResources(Graphics2D g2){
        g2.setColor(new Color(100,75,60,130));
        g2.fillRoundRect(0, (gp.maxWorldRow-1)*gp.tileSize, gp.screenWidth/2, gp.tileSize, 45,45);

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
    public void drawMenu(Graphics2D g2){

    }
}
