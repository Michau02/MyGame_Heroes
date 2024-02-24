package castles;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class CAS_People extends SuperCastle{

    public CAS_People(){
        name = "peopleTown";
        try{
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/castles/people/Zamek_Ludzie_Tier1_1.png")));
        }
        catch (
                IOException e){
            e.printStackTrace();
        }

        loadBuildings();
    }
}
