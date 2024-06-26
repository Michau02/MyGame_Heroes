package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;

public class TileManager {

    GamePanel gp;
    public ArrayList<Tile> tile;
    public int[][] mapTileNum;

    //pathfinding
    public Node node;
    public Node[][] mapNode;
    public ArrayList<Node> openList = new ArrayList<>();
    public ArrayList<Node> finalPathList = new ArrayList<>();
    public boolean goalReached;

    public int mapSizeX, mapSizeY;
    String fileName;

    public TileManager(GamePanel gp){
        this.gp = gp;
        tile = new ArrayList<>();
        fileName = "SimpleMap2_48x27";
        String[] name = fileName.split("_");
        String[] sizeOfMap = name[1].split("\\.");
        String[] size = sizeOfMap[0].split("x");
        mapSizeX = Integer.parseInt(size[0]);
        mapSizeY = Integer.parseInt(size[1]);

        getTileImage();
        loadMap(fileName);
    }
    public void loadMap(String fileName){
        try{
            InputStream inputStream = getClass().getResourceAsStream("/res/maps/" + fileName +  ".txt");
            assert inputStream != null;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            int row = 0, col = 0;
            mapTileNum = new int[mapSizeX][mapSizeY];
            mapNode = new Node[mapSizeX][mapSizeY];

            String line;
            String[] numbers;
            while(col < mapSizeX && row < mapSizeY){
                line = bufferedReader.readLine();
                numbers = line.split(" ");
                while(col < mapSizeX){
                    mapTileNum[col][row] = Integer.parseInt(numbers[col]);
                    node = new Node(col, row, tile.get(mapTileNum[col][row]).collision);
                    mapNode[col][row] = node;
                    col++;
                }
                if(col == mapSizeX){
                    col = 0;
                    row++;
                }
            }
        }catch (Exception e){
            System.out.println("Blad we wczytaniu mapy");
        }

    }
    public void getTileImage(){
        try{
            //GRASS
            tile.add(new Tile());
            tile.get(tile.size()-1).name = "grass";
            tile.get(tile.size()-1).image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/grass/BasicGrass.png")));

            tile.add(new Tile());
            tile.get(tile.size()-1).name = "grass";
            tile.get(tile.size()-1).image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/grass/BasicGrass_2.png")));

            tile.add(new Tile());
            tile.get(tile.size()-1).name = "grass";
            tile.get(tile.size()-1).image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/grass/GrassWater_Bottom.png")));

            tile.add(new Tile());
            tile.get(tile.size()-1).name = "grass";
            tile.get(tile.size()-1).image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/grass/GrassWater_Left.png")));

            tile.add(new Tile());
            tile.get(tile.size()-1).name = "grass";
            tile.get(tile.size()-1).image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/grass/GrassWater_Right.png")));

            tile.add(new Tile());
            tile.get(tile.size()-1).name = "grass";
            tile.get(tile.size()-1).image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/grass/GrassWater_Top.png")));

            tile.add(new Tile());
            tile.get(tile.size()-1).name = "grass";
            tile.get(tile.size()-1).image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/grass/GrassWater_Left_Bottom.png")));

            tile.add(new Tile());
            tile.get(tile.size()-1).name = "grass";
            tile.get(tile.size()-1).image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/grass/GrassWater_Left_Top.png")));

            tile.add(new Tile());
            tile.get(tile.size()-1).name = "grass";
            tile.get(tile.size()-1).image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/grass/GrassWater_Right_Bottom.png")));

            tile.add(new Tile());
            tile.get(tile.size()-1).name = "grass";
            tile.get(tile.size()-1).image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/grass/GrassWater_Right_Top.png")));

            //WATER
            tile.add(new Tile());
            tile.get(tile.size()-1).name = "water";
            tile.get(tile.size()-1).image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/water/BasicWater.png")));
            tile.get(tile.size()-1).collision = true;

            tile.add(new Tile());
            tile.get(tile.size()-1).name = "water";
            tile.get(tile.size()-1).image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/water/BasicWater_2.png")));
            tile.get(tile.size()-1).collision = true;

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2){

        int worldCol = 0, worldRow = 0;
        while(worldCol < mapSizeX && worldRow < mapSizeY){ // gp.maxWorld... to ilość kolumn i rzędów ekranu
            int tileNum = mapTileNum[worldCol][worldRow]; // cyfra będąca indeksem do tile[] czyli tablicy płytek do narysowania na ekranie

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX - gp.player.mapOffsetX; // player.worldX to pozycja gracza na mapie świata a player.screenX to pozycja gracza na ekranie (środek)
            int screenY = worldY - gp.player.worldY + gp.player.screenY - gp.player.mapOffsetY;

            if( worldX + gp.tileSize > gp.player.worldX - gp.player.screenX - gp.player.mapOffsetX &&
                worldX - 2*gp.tileSize < gp.player.worldX + gp.player.screenX + gp.player.mapOffsetX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY - gp.player.mapOffsetY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY + gp.player.mapOffsetY)
            {
                g2.drawImage(tile.get(tileNum).image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            }
            worldCol++;
            if(worldCol == mapSizeX){
                worldCol = 0;
                worldRow++;
            }
        }

    }

}
