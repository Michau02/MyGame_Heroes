package entity;

import main.GamePanel;
import main.MouseHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity{
    GamePanel gp;
    MouseHandler mh;
    int counter = 0, moveX, moveY;
    public int indexOfObjectToRemove, xLocationOfObjectToRemove, yLocationOfObjectToRemove;
    public boolean isGoingToObject = false;
    boolean pickedUpAnObject;
    int pickedUpAnObjectCounter = 0;
    String pickedUpObjectsName, showPickedUpObjectMessage;
    int showPickedUpObjectX, showPickedUpObjectY;
    int moveMapSpeed = 2; //scale given by player to calculate speed of moving map -> speed * moveMapSpeed;
    public int mapOffsetY = 0, mapOffsetX = 0;

    public int wood = 0, gold = 0;

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
    public void setDefaultValues(){
        worldX = 6*gp.tileSize;
        worldY = 6*gp.tileSize;
        speed = 2; // 1, 2, 3
        direction = "left";
        movementPoints = 8;
        remainingMovementPoints = movementPoints;
    }
    public void getPlayerImage(){
        try{

            leftStanding = ImageIO.read(getClass().getResourceAsStream("/res/player/leftStanding.png"));
            leftWalk1 = ImageIO.read(getClass().getResourceAsStream("/res/player/leftWalk1.png"));
            leftWalk2 = ImageIO.read(getClass().getResourceAsStream("/res/player/leftStanding.png"));

            rightStanding = ImageIO.read(getClass().getResourceAsStream("/res/player/rightStanding.png"));
            rightWalk1 = ImageIO.read(getClass().getResourceAsStream("/res/player/leftWalk1.png"));
            rightWalk2 = ImageIO.read(getClass().getResourceAsStream("/res/player/leftStanding.png"));

            upStanding = ImageIO.read(getClass().getResourceAsStream("/res/player/leftStanding.png"));
            upWalk1 = ImageIO.read(getClass().getResourceAsStream("/res/player/leftWalk1.png"));
            upWalk2 = ImageIO.read(getClass().getResourceAsStream("/res/player/leftStanding.png"));

            downStanding = ImageIO.read(getClass().getResourceAsStream("/res/player/rightStanding.png"));
            downWalk1 = ImageIO.read(getClass().getResourceAsStream("/res/player/leftWalk1.png"));
            downWalk2 = ImageIO.read(getClass().getResourceAsStream("/res/player/leftStanding.png"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void update() {

        if(gp.mouseMotionHandler.moveUp)
            mapOffsetY -= speed*moveMapSpeed;
        if(gp.mouseMotionHandler.moveDown)
            mapOffsetY += speed*moveMapSpeed;
        if(gp.mouseMotionHandler.moveLeft)
            mapOffsetX -= speed*moveMapSpeed;
        if(gp.mouseMotionHandler.moveRight)
            mapOffsetX += speed*moveMapSpeed;
        if (going) {
            counter++;
            goAnimation();
        } else {
            if (gp.keyHandler.endTurnReleased) {
                remainingMovementPoints = movementPoints;
                gp.clickCounter = 1;
                gold += 500;
                wood += 2;
                gp.keyHandler.endTurnReleased = false;
            }
            if (gp.keyHandler.movePressed) {
                if(isGoingToObject) {
                    if (gp.tileManager.finalPathList.size() - remainingMovementPoints <= 0)
                        gp.tileManager.finalPathList.remove(gp.tileManager.finalPathList.size() - 1);
                }
                gp.keyHandler.movePressed = false;
                going = true;
            }
            if (gp.mouseHandler.leftButtonReleased) {
                //poruszanie sie po mapie
                if (gp.mouseHandler.released_tileX == gp.mouseX && gp.mouseHandler.released_tileY == gp.mouseY && !gp.tileManager.tile[gp.tileManager.mapTileNum[gp.mouseX][gp.mouseY]].collision && !going) { // jeszcze przypadek can't reach there
                    gp.clickCounter++;
                    if (gp.clickCounter == 1 && !(worldX / gp.tileSize == gp.mouseX && worldY / gp.tileSize == gp.mouseY)) {
                        //wyswietl najkrotsza droge
                        isGoingToObject = goingToObject(gp.mouseHandler.released_tileX, gp.mouseHandler.released_tileY);
                        gp.pathFinding.findBestPath(worldX / gp.tileSize - mapOffsetX/gp.tileSize, worldY / gp.tileSize - mapOffsetY/gp.tileSize, gp.mouseX, gp.mouseY);

                        gp.oldX = gp.mouseHandler.released_tileX;
                        gp.oldY = gp.mouseHandler.released_tileY;
                    } else if (gp.clickCounter == 2) {
                        if (gp.oldX == gp.mouseHandler.released_tileX && gp.oldY == gp.mouseHandler.released_tileY) {
                            gp.clickCounter = 0;
                            //zrob cos po podwojnym klinieciu - np idz we wskazane miejsce
                            going = true;
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
                //pokaz podglad czegos (np przedmiot, armia itd)
            }
            if (gp.mouseHandler.rightButtonReleased) {
                //przestan pokazywac parametry czegos
                gp.tileManager.finalPathList.clear();
                isGoingToObject = false;
                gp.mouseHandler.rightButtonReleased = false;
                gp.clickCounter = 0;
            }
        }
    }
    public boolean goingToObject(int destinationX, int destinationY){
        for(int i = 0; i < gp.obj.length; i++){
            if(gp.obj[i] != null && gp.obj[i].worldY/gp.tileSize == destinationY){
                if(gp.obj[i].worldX/gp.tileSize == destinationX){
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
                pickUp(gp.obj[indexOfObjectToRemove].name);
                gp.obj[indexOfObjectToRemove] = null;
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
                g2.drawString(showPickedUpObjectMessage, showPickedUpObjectX, showPickedUpObjectY);
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
