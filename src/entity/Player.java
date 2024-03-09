package entity;

import castles.SuperCastle;
import main.GamePanel;
import main.MouseHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Player extends Entity{
    GamePanel gp;
    MouseHandler mh;
    int counter = 0, moveX, moveY;
    public int indexOfObjectToRemove, xLocationOfObjectToRemove, yLocationOfObjectToRemove;
    public boolean isGoingToObject = false, justLeft = false;
    boolean pickedUpAnObject;
    public int inTheTown = -1;
    int pickedUpAnObjectCounter = 0;
    String pickedUpObjectsName, showPickedUpObjectMessage;
    int showPickedUpObjectX, showPickedUpObjectY;
    int moveMapSpeed = 3; //scale given by player to calculate speed of moving map -> speed * moveMapSpeed;
    public int mapOffsetY = 0, mapOffsetX = 0;
    public ArrayList <SuperCastle> playersCastles = new ArrayList<>();

    public int wood = 0, gold = 0, woodPerTurn = 0, goldPerTurn = 0;

    public int screenX;
    public int screenY;

    public Player(GamePanel gp, MouseHandler mh){
        this.gp = gp;
        this.mh = mh;

        screenX = ((gp.screenWidth/2)/gp.tileSize) * gp.tileSize;
        screenY = ((gp.screenHeight/2)/gp.tileSize) * gp.tileSize;

        setDefaultValues();
        getPlayerImage();
    }
    public void countIncome(){
        for(SuperCastle castle : playersCastles){
            goldPerTurn += castle.income;
        }
    }
    public void setDefaultValues(){
        worldX = 7*gp.tileSize;
        worldY = 10*gp.tileSize;
        speed = 2; // 1, 2, 3
        direction = "left";
        movementPoints = 8;
        manaPoints = 10;
        remainingMovementPoints = movementPoints;
        remainingManaPoints = 0;
    }
    public void getPlayerImage(){
        try{

            leftStanding = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/leftStanding.png")));
            leftWalk1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/leftWalk1.png")));
            leftWalk2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/leftStanding.png")));

            rightStanding = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/rightStanding.png")));
            rightWalk1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/leftWalk1.png")));
            rightWalk2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/leftStanding.png")));

            upStanding = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/leftStanding.png")));
            upWalk1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/leftWalk1.png")));
            upWalk2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/leftStanding.png")));

            downStanding = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/rightStanding.png")));
            downWalk1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/leftWalk1.png")));
            downWalk2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/leftStanding.png")));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void update() {

        if(gp.mouseMotionHandler.moveUp || gp.keyHandler.upPressed)
            mapOffsetY -= speed*moveMapSpeed;
        if(gp.mouseMotionHandler.moveDown || gp.keyHandler.downPressed)
            mapOffsetY += speed*moveMapSpeed;
        if(gp.mouseMotionHandler.moveLeft || gp.keyHandler.leftPressed)
            mapOffsetX -= speed*moveMapSpeed;
        if(gp.mouseMotionHandler.moveRight || gp.keyHandler.rightPressed)
            mapOffsetX += speed*moveMapSpeed;
        if (going) {
            counter++;
            goAnimation();
        } else {
            isPlayerInTheTown(worldX/gp.tileSize, worldY/gp.tileSize);
            System.out.println("Town index: " + inTheTown);
            System.out.println("Clicks: " + gp.ui.clicks);
            if(inTheTown != -1 && !justLeft) {
                gp.gameState = gp.castleState;
            }

            if (gp.keyHandler.endTurnReleased) {
                newTurn();
            }
            if (gp.keyHandler.movePressed) {
                if(isGoingToObject) {
                    if (gp.tileManager.finalPathList.size() - remainingMovementPoints <= 0)
                        gp.tileManager.finalPathList.remove(gp.tileManager.finalPathList.size() - 1);
                }
                gp.keyHandler.movePressed = false;
                going = true;
            }
            if (gp.mouseHandler.leftButtonReleased && (gp.mouseHandler.released_tileX >= 0 && gp.mouseHandler.released_tileX <= gp.screenWidth && gp.mouseHandler.released_tileY >= 0 && gp.mouseHandler.released_tileY <= gp.screenHeight) && !gp.tileManager.mapNode[gp.mouseX][gp.mouseY].solid) {
                //poruszanie sie po mapie
                if (gp.mouseHandler.released_tileX == gp.mouseX && gp.mouseHandler.released_tileY == gp.mouseY && !gp.tileManager.tile.get(gp.tileManager.mapTileNum[gp.mouseX][gp.mouseY]).collision && !going) { // jeszcze przypadek can't reach there
                    gp.clickCounter++;
                    if (gp.clickCounter == 1 && !(worldX / gp.tileSize == gp.mouseX && worldY / gp.tileSize == gp.mouseY)) {
                        //wyswietl najkrotsza droge
                        isGoingToObject = goingToObject(gp.mouseHandler.released_tileX, gp.mouseHandler.released_tileY);
                        gp.pathFinding.findBestPath(worldX / gp.tileSize, worldY / gp.tileSize, gp.mouseX, gp.mouseY);

                        gp.oldX = gp.mouseHandler.released_tileX;
                        gp.oldY = gp.mouseHandler.released_tileY;
                    } else if (gp.clickCounter == 2) {
                        if (gp.oldX == gp.mouseHandler.released_tileX && gp.oldY == gp.mouseHandler.released_tileY) {
                            gp.clickCounter = 0;
                            //zrob cos po podwojnym klinieciu - np idz we wskazane miejsce
                            justLeft = false;
                            gp.playerMoved = true;
                            going = true;
                            mapOffsetX = 0;
                            mapOffsetY = 0;
                            if(isGoingToObject) {
                                if (gp.tileManager.finalPathList.size() - remainingMovementPoints <= 0)
                                    gp.tileManager.finalPathList.remove(gp.tileManager.finalPathList.size() - 1);
                            }
                        } else {
                            gp.clickCounter = 1;
                            if (!(worldX / gp.tileSize == gp.mouseX && worldY / gp.tileSize == gp.mouseY)) {
                                isGoingToObject = goingToObject(gp.mouseHandler.released_tileX, gp.mouseHandler.released_tileY);
                                gp.pathFinding.findBestPath(worldX / gp.tileSize, worldY / gp.tileSize, gp.mouseX, gp.mouseY);
                                gp.oldX = gp.mouseHandler.released_tileX;
                                gp.oldY = gp.mouseHandler.released_tileY;
                            }
                        }
                    }
                }
                gp.mouseHandler.leftButtonReleased = false;
            }
            if (gp.mouseHandler.rightButtonPressed) {
                gp.mouseHandler.blockedMouseMovement = true;
            }
            if (gp.mouseHandler.rightButtonReleased) {
                gp.mouseHandler.blockedMouseMovement = false;
                //przestan pokazywac parametry czegos
                gp.tileManager.finalPathList.clear();
                isGoingToObject = false;
                gp.mouseHandler.rightButtonReleased = false;
                gp.clickCounter = 0;
            }
        }
    }
    public void newTurn(){
        remainingMovementPoints = movementPoints;
        remainingManaPoints = Math.min(remainingManaPoints + 2, manaPoints);
        gp.clickCounter = 1;
        gold += goldPerTurn;
        wood += woodPerTurn;
        gp.day++;
        if(gp.day%8 == 0){
            gp.day = 1;
            gp.week++;
            if(gp.week%5 == 0){
                gp.week = 1;
                gp.month++;
            }
        }
        gp.keyHandler.endTurnReleased = false;
    }
    public void isPlayerInTheTown(int playersX, int playersY){
        for(int i = 0; i < gp.cas.size(); i++){
            if(gp.cas.get(i) != null && (gp.cas.get(i).worldX+2)/gp.tileSize == playersX-1 && (gp.cas.get(i).worldY+2)/gp.tileSize == playersY-1){
                inTheTown = i;
                return;
            }
        }
        inTheTown = -1;
    }
    public boolean goingToObject(int destinationX, int destinationY){
        for(int i = 0; i < gp.obj.size(); i++){
            if(gp.obj.get(i) != null && gp.obj.get(i).worldY/gp.tileSize == destinationY){
                if(gp.obj.get(i).worldX/gp.tileSize == destinationX){
                    indexOfObjectToRemove = i;
                    xLocationOfObjectToRemove = destinationX;
                    yLocationOfObjectToRemove = destinationY;
                    return true;
                }
            }
        }
        indexOfObjectToRemove = -1;
        xLocationOfObjectToRemove = -1;
        yLocationOfObjectToRemove = -1;
        return false;
    }
    public void goAnimation(){
        if(gp.tileManager.finalPathList.size() != 0 && remainingMovementPoints != 0){
            if(worldX/gp.tileSize < gp.tileManager.finalPathList.get(0).col)
                direction = "right";
            else
                direction = "left";
            if(counter == 12/speed){
                if(worldX/gp.tileSize == gp.tileManager.finalPathList.get(0).col) {
                    moveY = (worldY / gp.tileSize - gp.tileManager.finalPathList.get(0).row) * gp.tileSize/4;
                    worldY -= moveY;
                }
                else {
                    moveX = (worldX / gp.tileSize - gp.tileManager.finalPathList.get(0).col) * gp.tileSize/4;
                    worldX -= moveX;
                }
            }
            else if(counter == 24/speed){
                if(moveY != 0)
                    worldY -= moveY;
                else
                    worldX -= moveX;
            }
            else if(counter == 36/speed){
                if(moveY != 0)
                    worldY -= moveY;
                else
                    worldX -= moveX;
            }
            else if(counter == 48/speed){
                if(moveY != 0)
                    worldY -= moveY;
                else
                    worldX -= moveX;

                gp.tileManager.finalPathList.remove(0);
                remainingMovementPoints--;
                moveX = 0;
                moveY = 0;
                counter = 0;
            }
        }
        else if(remainingMovementPoints != 0){
            if(isGoingToObject) {
                pickUp(gp.obj.get(indexOfObjectToRemove).name);
                gp.obj.remove(indexOfObjectToRemove);
            }
            going = false;
        }
        else
            going = false;
    }
    public void pickUp(String name){

        int amount = 0;
        switch (name){
            case "Wood":
                amount = gp.random.nextInt(6)+1;
                wood += amount;
                pickedUpObjectsName = "Wood";
                break;

            case "Gold":
                amount = (gp.random.nextInt(9) + 2) * 100;
                gold += amount;
                pickedUpObjectsName = "Gold";
                break;
        }
        pickedUpAnObject = true;
        pickedUpAnObjectCounter = 0;
        showPickedUpObjectX = screenX;
        showPickedUpObjectY = screenY;
        showPickedUpObjectMessage = pickedUpObjectsName + " +" + amount;
    }
    public void draw(Graphics2D g2){
        BufferedImage image = null;

        switch(direction){
            case "left":
                image = leftStanding;
                break;
            case "right":
                image = rightStanding;
                break;
            case "up":
                image = leftStanding;
                break;
            case "down":
                image = rightStanding;
                break;
        }
        g2.drawImage(image, screenX - mapOffsetX, screenY - mapOffsetY, gp.tileSize, gp.tileSize, null);
        if(pickedUpAnObject){
            g2.setColor(Color.white);
            if(showPickedUpObjectMessage != null)
                g2.drawString(showPickedUpObjectMessage, showPickedUpObjectX - mapOffsetX, showPickedUpObjectY - mapOffsetY);
            pickedUpAnObjectCounter++;
            if(pickedUpAnObjectCounter % 60 == 0){
                pickedUpAnObjectCounter = 0;
                pickedUpAnObject = false;
            }
            else if(pickedUpAnObjectCounter %5 == 0) {
                showPickedUpObjectY -= 2;
            }
        }
    }
}
