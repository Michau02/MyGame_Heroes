package castles;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class Building {

    public Building(String name){
        this.name = name;
        try{
            switch(name){
                case "strzelnica":
                    image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/buildings/strzelnica.png")));
                    buildingCost.put("gold", 1000);
                    buildingCost.put("wood", 5);
                    break;
                case "stajnia":
                    image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/buildings/stajnia.png")));
                    buildingCost.put("gold", 4000);
                    buildingCost.put("wood", 20);
            }
        }catch (IOException e){
            e.printStackTrace();
        }

    }
    public BufferedImage image;
    public String name;
    public boolean buildAlready = false;
    public HashMap<String, Integer> buildingCost = new HashMap<>();
}
