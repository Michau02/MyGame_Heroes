package main;

import tile.Node;
import tile.TileManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class PathFinding {

    GamePanel gp;
    TileManager tm;
    public Node startNode, goalNode, currentNode;
    int cost, travelTime, m = 0;
    BufferedImage   greenLeft, greenUpLeft, greenUpRight, greenRight, greenDownRight, greenDownLeft, greenDown, greenUp, greenCross,
                    redLeft, redUpLeft, redUpRight, redRight, redDownRight, redDownLeft, redDown, redUp, redCross;
    String dest;

    public PathFinding(GamePanel gp, TileManager tm){
        this.gp = gp;
        this.tm = tm;
        loadImages();
    }

    public void loadImages(){
        try{
            //green
            greenLeft = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/path/greenLeft.png")));
            greenUpLeft = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/path/greenUpLeft.png")));
            greenUpRight = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/path/greenUpRight.png")));
            greenRight = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/path/greenRight.png")));
            greenDownRight = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/path/greenDownRight.png")));
            greenDownLeft = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/path/greenDownLeft.png")));
            greenDown = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/path/greenDown.png")));
            greenUp = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/path/greenUp.png")));
            greenCross = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/path/greenCross.png")));

            //red
            redLeft = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/path/redLeft.png")));
            redUpLeft = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/path/redUpLeft.png")));
            redUpRight = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/path/redUpRight.png")));
            redRight = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/path/redRight.png")));
            redDownRight = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/path/redDownRight.png")));
            redDownLeft = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/path/redDownLeft.png")));
            redDown = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/path/redDown.png")));
            redUp = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/path/redUp.png")));
            redCross = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/path/redCross.png")));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void findBestPath(int startX, int startY, int goalX, int goalY){
            tm.finalPathList.clear();
            tm.openList.clear();
            tm.goalReached = false;

            for (int i = 0; i < tm.mapNode.length; i++) {
                for (int j = 0; j < tm.mapNode[0].length; j++) {
                    tm.mapNode[i][j].open = false;
                }
            }

            //set start and goal tiles
            setStartNode(startX, startY);
            setGoalNode(goalX, goalY);

            setCostsOnNodes();
            search();
    }

    private void setStartNode(int x, int y){
        tm.mapNode[x][y].setAsStart();
        startNode = tm.mapNode[x][y];
        currentNode = startNode;
    }
    private void setGoalNode(int x, int y){
        tm.mapNode[x][y].setAsGoal();
        goalNode = tm.mapNode[x][y];
    }
    public void getCost(Node node){
        //gCost
        int xDistance = Math.abs(node.col - startNode.col);
        int yDistance = Math.abs(node.row - startNode.row);
        node.gCost = xDistance + yDistance;

        //hCost
        xDistance = Math.abs(node.col - goalNode.col);
        yDistance = Math.abs(node.row - goalNode.row);
        node.hCost = xDistance + yDistance;

        //fCost
        node.fCost = node.hCost + node.gCost;
    }
    public void setCostsOnNodes(){
        int col = 0, row = 0;
        while(col < tm.mapSizeX && row < tm.mapSizeY){
            while(col < tm.mapSizeX){
               getCost(tm.mapNode[col][row]);
               col++;
            }
            if(col == tm.mapSizeX){
                col = 0;
                row ++;
            }
        }
    }
    public void search(){
        while(!tm.goalReached){
            int col = currentNode.col;
            int row = currentNode.row;

            currentNode.setAsChecked();
            tm.openList.remove(currentNode);

            if(row - 1 >= 0)
                openNode(tm.mapNode[col][row-1]);

            if(col -1 >= 0)
                openNode(tm.mapNode[col-1][row]);

            if(row + 1 < tm.mapSizeY)
                openNode(tm.mapNode[col][row+1]);

            if(col + 1 < tm.mapSizeX)
                openNode(tm.mapNode[col+1][row]);

            int bestNodeIndex = 0;
            int bestNodefCost = 999;


            for(int i = 0; i < tm.openList.size(); i++){
                if(tm.openList.get(i).fCost < bestNodefCost){
                    bestNodeIndex = i;
                    bestNodefCost = tm.openList.get(i).fCost;
                }
                else if(tm.openList.get(i).fCost == bestNodefCost){
                    if(tm.openList.get(i).gCost < tm.openList.get(bestNodeIndex).gCost)
                        bestNodeIndex = i;
                }
            }
            currentNode = tm.openList.get(bestNodeIndex);

            if(currentNode == goalNode){
                tm.goalReached = true;
                trackThePath();
            }
        }
        tm.finalPathList.add(tm.finalPathList.size(), goalNode);
    }
    public void openNode(Node node){
        if(!node.open && !node.solid && ((!objectThere(node)) || (node.col == gp.player.xLocationOfObjectToRemove && node.row == gp.player.yLocationOfObjectToRemove))){
            node.setAsOpen();
            node.parent = currentNode;
            tm.openList.add(node);
        }
    }

    public boolean objectThere(Node node){
        for(int i = 0; i < gp.obj.size(); i++){
            if(gp.obj.get(i) != null) {
                if (gp.obj.get(i).worldX / gp.tileSize == node.col && gp.obj.get(i).worldY / gp.tileSize == node.row)
                    return true;
            }
        }
        return false;
    }
    public void trackThePath(){
        currentNode = goalNode;

        while(currentNode != startNode){
            currentNode = currentNode.parent;
            if(currentNode != startNode){
                tm.finalPathList.add(0, currentNode);
            }
        }
    }
    public void draw(Graphics2D g2){
        int x,y;
        BufferedImage image = null;
        cost = 1;
        for(int i = 0; i < tm.finalPathList.size(); i++){
            if(i != 0){
                cost += Math.abs(tm.finalPathList.get(i).col - tm.finalPathList.get(i-1).col) + Math.abs(tm.finalPathList.get(i).row - tm.finalPathList.get(i-1).row);
            }
            x = tm.finalPathList.get(i).col;
            y = tm.finalPathList.get(i).row;

            if(i != tm.finalPathList.size()-1){ // strzalki
                // ustalenie kierunku strzalki
                if(tm.finalPathList.get(i).col < tm.finalPathList.get(i+1).col) {
                    if(tm.finalPathList.get(i).row < tm.finalPathList.get(i+1).row) dest = "dr"; // down right
                    else if (tm.finalPathList.get(i).row > tm.finalPathList.get(i+1).row) dest = "ur"; // up right
                    else dest = "r"; // right
                }
                else if(tm.finalPathList.get(i).col > tm.finalPathList.get(i+1).col){
                    if(tm.finalPathList.get(i).row < tm.finalPathList.get(i+1).row) dest = "dl"; // down left
                    else if (tm.finalPathList.get(i).row > tm.finalPathList.get(i+1).row) dest = "ul"; // up left
                    else dest = "l"; // left
                }
                else if(tm.finalPathList.get(i).row < tm.finalPathList.get(i+1).row) dest = "d"; // down
                else dest = "u"; // up

                //ustawienie odpowiedniego image
                if(cost > gp.player.remainingMovementPoints){
                    //czerwone strzalki i X -> na razie sa zielone ale jak dorysuje to sie to zmieni
                    switch(dest){
                        case "u":
                            image = redUp;
                            break;
                        case "r":
                            image = redRight;
                            break;
                        case "d":
                            image = redDown;
                            break;
                        case "l":
                            image = redLeft;
                            break;
                        case "ul":
                            image = redUpLeft;
                            break;
                        case "dl":
                            image = redDownLeft;
                            break;
                        case "ur":
                            image = redUpRight;
                            break;
                        case "dr":
                            image = redDownRight;
                            break;
                    }
                }
                else{
                    switch(dest){
                        case "u":
                            image = greenUp;
                            break;
                        case "r":
                            image = greenRight;
                            break;
                        case "d":
                            image = greenDown;
                            break;
                        case "l":
                            image = greenLeft;
                            break;
                        case "ul":
                            image = greenUpLeft;
                            break;
                        case "dl":
                            image = greenDownLeft;
                            break;
                        case "ur":
                            image = greenUpRight;
                            break;
                        case "dr":
                            image = greenDownRight;
                            break;
                    }
                }
            }
           else{ //krzyzyk
                if(cost > gp.player.remainingMovementPoints){
                    //czerwony
                    image = redCross;
                }
                else{
                    //zielony
                    image = greenCross;
                }
                if(cost > gp.player.remainingMovementPoints)
                    cost -= gp.player.remainingMovementPoints;
                else cost = 0;
                travelTime = (int)Math.ceil((double)cost/gp.player.movementPoints) + 1;
                if(travelTime != 1){
                    g2.setColor(Color.white);
                    g2.drawString(String.valueOf(travelTime), x*gp.tileSize + gp.player.screenX - gp.player.worldX - gp.player.mapOffsetX + gp.tileSize*4/5, (y*gp.tileSize + gp.player.screenY - gp.player.worldY - gp.player.mapOffsetY) + gp.tileSize/6);
                }
            }
            g2.drawImage(image, (x*gp.tileSize + gp.player.screenX - gp.player.worldX - gp.player.mapOffsetX) + gp.tileSize/4, (y*gp.tileSize + gp.player.screenY - gp.player.worldY - gp.player.mapOffsetY) + gp.tileSize/4, gp.tileSize/2, gp.tileSize/2, null);
        }
    }

}
