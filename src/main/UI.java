package main;

import castles.SuperCastle;
import object.SuperObject;

import java.awt.*;

public class UI {

    GamePanel gp;
    Font arialITALIC_18, italicBOLD_20, italicBOLD_40;
    FontMetrics fontMetrics, italicBOLD_20Metrics;
    static String name;
    static String noResources = "";
    static String type = "null", date = "null";
    int italicBOLD_20Width, italicBOLD_20Height;
    public int clicks = 0;
    Color buildingBought;

    public UI(GamePanel gp){
        this.gp = gp;

        arialITALIC_18 = new Font("Arial", Font.ITALIC, 18);
        italicBOLD_20 =new Font("Italic", Font.BOLD, 20);
        italicBOLD_40 =new Font("Italic", Font.BOLD, 40);
        fontMetrics = gp.getFontMetrics(italicBOLD_40);
        italicBOLD_20Metrics = gp.getFontMetrics(italicBOLD_20);
        italicBOLD_20Height = italicBOLD_20Metrics.getAscent();
        buildingBought = new Color(255, 215, 0, 150);
    }
    public void draw(Graphics2D g2){
        drawInterface(g2);
        if(gp.obj.size() == 0 && gp.playerMoved)
            finishedCampaign(g2);
        if(gp.mouseHandler.rightButtonPressed){
            if((gp.mouseX >= 0 && gp.mouseX <= gp.screenWidth && gp.mouseY >= 0 && gp.mouseY <= gp.screenHeight))
                drawProperties(g2);
        }
        if(gp.gameState == gp.pauseState){
            drawPausePanel(g2);
        }
        else if(gp.gameState == gp.settingsState){
            drawSettingsPanel(g2);
        }
        else if(gp.gameState == gp.menuState){
            drawMenu(g2);
        }
        else if(gp.gameState == gp.castleState){
            drawCastlePanel(g2);
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
        if(gp.keyHandler.spaceTyped){
            g2.setColor(Color.green);
            g2.fillRect(gp.player.screenX - gp.player.mapOffsetX, gp.player.screenY - gp.player.mapOffsetY -gp.tileSize/2, gp.tileSize*gp.player.remainingMovementPoints/8, gp.tileSize/6);
            g2.setColor(Color.black);
            g2.drawRect(gp.player.screenX - gp.player.mapOffsetX, gp.player.screenY - gp.player.mapOffsetY -gp.tileSize/2, gp.tileSize, gp.tileSize/6);
            for(int i = 0; i < 8; i++){
                g2.drawLine(gp.player.screenX - gp.player.mapOffsetX + (i*gp.tileSize/8), gp.player.screenY - gp.player.mapOffsetY -gp.tileSize/2, gp.player.screenX - gp.player.mapOffsetX + (i*gp.tileSize/8), gp.player.screenY - gp.player.mapOffsetY -gp.tileSize/2 + gp.tileSize/6);
            }
        }
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

        switch(gp.cas[gp.player.inTheTown].name){
            case "peopleTown":
                //panel and displaying top part of panel
                g2.setColor(Color.GRAY);
                g2.fillRoundRect(5, gp.tileSize, gp.castlePanel.width, gp.castlePanel.height, gp.castlePanel.curveAngle, gp.castlePanel.curveAngle);
                g2.setColor(Color.BLUE);
                g2.setFont(italicBOLD_40);
                g2.drawString("People town", 5+gp.castlePanel.width/2 - fontMetrics.stringWidth("People town")/2, gp.tileSize + fontMetrics.getAscent() +(int)(fontMetrics.getAscent() * 0.5));
                g2.setColor(Color.darkGray);
                g2.drawLine(5, gp.tileSize + 2 * fontMetrics.getAscent(), 5+gp.castlePanel.width,gp.tileSize + 2 * fontMetrics.getAscent());

                //middle part - buildings
                g2.setColor(Color.BLACK);
                g2.drawString("Buildings", 5+gp.castlePanel.width/2 - fontMetrics.stringWidth("Buildings")/2, gp.tileSize + 4 * fontMetrics.getAscent());

                if(gp.cas[gp.player.inTheTown].buildings.size() != 0){
                    g2.drawImage(gp.cas[gp.player.inTheTown].buildings.get(0). image, (gp.castlePanel.width-gp.tileSize*6)/2, gp.tileSize + 5 * fontMetrics.getAscent(),gp.tileSize*6, gp.tileSize*4,null);
                    g2.drawImage(gp.cas[gp.player.inTheTown].buildings.get(1). image, (gp.castlePanel.width-gp.tileSize*6)/2, 6*gp.tileSize + 5 * fontMetrics.getAscent(),gp.tileSize*6, gp.tileSize*4,null);
                }

                g2.setColor(buildingBought);
                if(gp.cas[gp.player.inTheTown].buildings.size() != 0){
                    if(gp.cas[gp.player.inTheTown].buildings.get(0).buildAlready)
                        g2.fillRect((gp.castlePanel.width-gp.tileSize*6)/2, gp.tileSize + 5 * fontMetrics.getAscent(),gp.tileSize*6, gp.tileSize*4);
                    else if(gp.cas[gp.player.inTheTown].buildings.get(1).buildAlready)
                        g2.fillRect((gp.castlePanel.width-gp.tileSize*6)/2, 6*gp.tileSize + 5 * fontMetrics.getAscent(),gp.tileSize*6, gp.tileSize*4);
                }

                //bottom part - informations about town
                g2.setColor(Color.darkGray);
                g2.drawLine(5, 11*gp.tileSize + 5 * fontMetrics.getAscent(), 5+gp.castlePanel.width, 11*gp.tileSize + 5 * fontMetrics.getAscent());
                g2.setColor(Color.black);
                g2.drawString("Town specification", 5+gp.castlePanel.width/2 - fontMetrics.stringWidth("Town specification")/2, 12*gp.tileSize + 5 * fontMetrics.getAscent()+(int)(fontMetrics.getAscent() * 0.5));

                g2.setFont(italicBOLD_20);
                g2.drawString("Income: ", gp.tileSize, 14*gp.tileSize + 5 * fontMetrics.getAscent()+(int)(fontMetrics.getAscent() * 0.5));
                g2.drawString("- " + gp.player.goldPerTurn + " gold per turn", gp.tileSize, 15*gp.tileSize + 5 * fontMetrics.getAscent()+(int)(fontMetrics.getAscent() * 0.5));
                g2.drawString("- " + gp.player.woodPerTurn + " wood per turn", gp.tileSize, 16*gp.tileSize + 5 * fontMetrics.getAscent()+(int)(fontMetrics.getAscent() * 0.5));

                g2.drawString("Buildings build: ", gp.tileSize, 18*gp.tileSize + 5 * fontMetrics.getAscent()+(int)(fontMetrics.getAscent() * 0.5));
                for(int i = 0; i < gp.cas[gp.player.inTheTown].buildings.size(); i++){
                    if(gp.cas[gp.player.inTheTown].buildings.get(i).buildAlready)
                        g2.drawString("- " +  gp.cas[gp.player.inTheTown].buildings.get(i), gp.tileSize, (19+i)*gp.tileSize + 5 * fontMetrics.getAscent()+(int)(fontMetrics.getAscent() * 0.5));
                    i++;
                }

                if(gp.mouseHandler.leftButtonReleased && (gp.mouseHandler.x >= (gp.castlePanel.width-gp.tileSize*6)/2 && gp.mouseHandler.x <= gp.tileSize*6 + (gp.castlePanel.width-gp.tileSize*6)/2 &&
                        gp.mouseHandler.y >= gp.tileSize + 5 * fontMetrics.getAscent() && gp.mouseHandler.y <= gp.tileSize + 5 * fontMetrics.getAscent()+4*gp.tileSize)){
                    clicks = clicks == -1 ? 0 : 1;

                    if(clicks == 1 && !gp.cas[gp.player.inTheTown].buildings.get(0).buildAlready){
                        askForConfirmation(g2, 0);
                    }
                    else if(clicks == 1 && gp.cas[gp.player.inTheTown].buildings.get(0).buildAlready){
                        g2.fillRoundRect(gp.screenWidth/2-3*gp.tileSize, gp.screenHeight-2*gp.tileSize,6*gp.tileSize,4*gp.tileSize, 45, 45);
                        g2.setColor(Color.black);
                        g2.setFont(italicBOLD_20);
                        g2.drawString("Building is already built.", gp.screenWidth/2-3*gp.tileSize + 3*gp.tileSize - italicBOLD_20Width/2,gp.screenHeight-2*gp.tileSize + 2*gp.tileSize - italicBOLD_20Height/2);
                    }
                }
                else if(gp.mouseHandler.leftButtonReleased && (gp.mouseHandler.gp.mouseHandler.x >= (gp.castlePanel.width-gp.tileSize*6)/2 && gp.mouseHandler.gp.mouseHandler.x <= gp.tileSize*6 + (gp.castlePanel.width-gp.tileSize*6)/2 &&
                        gp.mouseHandler.y >= 6*gp.tileSize + 5 * fontMetrics.getAscent() && gp.mouseHandler.gp.mouseHandler.y <= gp.tileSize + 5 * fontMetrics.getAscent()+4*gp.tileSize)){
                    System.out.println("building 1");
                }
                else{
                    clicks = -1;
                }

                g2.setColor(Color.darkGray);
                g2.drawString("- " + gp.player.woodPerTurn + " wood per turn", gp.tileSize, 16*gp.tileSize + 5 * fontMetrics.getAscent()+(int)(fontMetrics.getAscent() * 0.5));

                break;
            case "inferno":
                break;
        }

    }
    public void askForConfirmation(Graphics2D g2, int num){
        g2.setColor(Color.gray);
        g2.fillRoundRect(gp.screenWidth/2-5*gp.tileSize, gp.screenHeight/2-2*gp.tileSize,10*gp.tileSize,4*gp.tileSize, 45, 45);
        g2.setColor(Color.black);
        g2.drawRoundRect(gp.screenWidth/2-5*gp.tileSize, gp.screenHeight/2-2*gp.tileSize,10*gp.tileSize,4*gp.tileSize, 45, 45);
        g2.setFont(italicBOLD_20);
        g2.drawString("Are you sure you want to build this building?", gp.screenWidth/2-5*gp.tileSize + (10*gp.tileSize - italicBOLD_20Metrics.stringWidth("Are you sure you want to build this building?")) / 2,gp.screenHeight/2-2*gp.tileSize + gp.tileSize - italicBOLD_20Height/2);
        g2.drawString("Cost: " + gp.cas[gp.player.inTheTown].buildings.get(num).buildingCost.get("gold") + " gold and " + gp.cas[gp.player.inTheTown].buildings.get(num).buildingCost.get("wood") + " wood", gp.screenWidth/2-5*gp.tileSize + (10*gp.tileSize - italicBOLD_20Metrics.stringWidth("Cost: " + gp.cas[gp.player.inTheTown].buildings.get(num).buildingCost.get("gold") + " gold and " + gp.cas[gp.player.inTheTown].buildings.get(num).buildingCost.get("wood") + " wood")) / 2,gp.screenHeight/2-2*gp.tileSize + gp.tileSize + italicBOLD_20Height*3/2);
        g2.setColor(Color.red);
        g2.drawString(noResources, gp.screenWidth/2-5*gp.tileSize + (10*gp.tileSize - italicBOLD_20Metrics.stringWidth(noResources)) / 2,gp.screenHeight/2-2*gp.tileSize + 2*gp.tileSize + italicBOLD_20Height*3/2);

        if(gp.keyHandler.escapePressed) {
            clicks = -1;
            noResources = "";
            gp.keyHandler.escapePressed = false;
        }
        else if(gp.keyHandler.enterPressed){
            if(gp.cas[gp.player.inTheTown].buildings.get(num).buildingCost.get("gold") <= gp.player.gold && gp.cas[gp.player.inTheTown].buildings.get(num).buildingCost.get("wood") <= gp.player.wood ){
                gp.cas[gp.player.inTheTown].buildings.get(num).buildAlready = true;
                gp.player.gold -= gp.cas[gp.player.inTheTown].buildings.get(num).buildingCost.get("gold");
                gp.player.wood -= gp.cas[gp.player.inTheTown].buildings.get(num).buildingCost.get("wood");
                clicks = -1;
            }
            else{
                noResources = "You don't have enough resources";
            }
        }
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
