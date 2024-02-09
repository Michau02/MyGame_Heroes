package main;

import castles.CAS_People;
import object.OBJ_Gold;
import object.OBJ_GoldInv;
import object.OBJ_Wood;
import object.OBJ_WoodInv;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }
    public void setObject(){
        //OBJECTS
        gp.obj[0] = new OBJ_Wood();
        gp.obj[0].worldX = 5*gp.tileSize;
        gp.obj[0].worldY = 10*gp.tileSize;

        gp.obj[1] = new OBJ_Wood();
        gp.obj[1].worldX = 9*gp.tileSize;
        gp.obj[1].worldY = 10*gp.tileSize;

        gp.obj[2] = new OBJ_Wood();
        gp.obj[2].worldX = 5*gp.tileSize;
        gp.obj[2].worldY = 15*gp.tileSize;

        gp.obj[3] = new OBJ_Gold();
        gp.obj[3].worldX = 6*gp.tileSize;
        gp.obj[3].worldY = 12*gp.tileSize;

        gp.obj[4] = new OBJ_Gold();
        gp.obj[4].worldX = 8*gp.tileSize;
        gp.obj[4].worldY = 13*gp.tileSize;

        //CURRENT RESOURCES
        gp.objInv[0] = new OBJ_WoodInv();
        gp.objInv[0].worldX = gp.tileSize/2;
        gp.objInv[0].worldY = 17*gp.tileSize;

        gp.objInv[1] = new OBJ_GoldInv();
        gp.objInv[1].worldX = 2*gp.tileSize + gp.tileSize/2;
        gp.objInv[1].worldY = 17*gp.tileSize;

        //CASTLES
        gp.cas[0] = new CAS_People();
        gp.cas[0].worldX = 6*gp.tileSize;
        gp.cas[0].worldY = 8*gp.tileSize;
    }
}
