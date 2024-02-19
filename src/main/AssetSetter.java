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
        gp.obj.add(new OBJ_Wood());
        gp.obj.get(0).worldX = 5*gp.tileSize;
        gp.obj.get(0).worldY = 10*gp.tileSize;

        gp.obj.add(new OBJ_Wood());
        gp.obj.get(1).worldX = 9*gp.tileSize;
        gp.obj.get(1).worldY = 10*gp.tileSize;

        gp.obj.add(new OBJ_Wood());
        gp.obj.get(2).worldX = 5*gp.tileSize;
        gp.obj.get(2).worldY = 15*gp.tileSize;

        gp.obj.add(new OBJ_Gold());
        gp.obj.get(3).worldX = 6*gp.tileSize;
        gp.obj.get(3).worldY = 12*gp.tileSize;

        gp.obj.add(new OBJ_Gold());
        gp.obj.get(4).worldX = 8*gp.tileSize;
        gp.obj.get(4).worldY = 13*gp.tileSize;

        //CURRENT RESOURCES
        gp.objInv.add(new OBJ_WoodInv());
        gp.objInv.get(0).worldX = gp.tileSize/2;
        gp.objInv.get(0).worldY = (gp.maxWorldRow-1)*gp.tileSize;

        gp.objInv.add(new OBJ_GoldInv());
        gp.objInv.get(1).worldX = 2*gp.tileSize + gp.tileSize/2;
        gp.objInv.get(1).worldY = (gp.maxWorldRow-1)*gp.tileSize;

        //CASTLES
        gp.cas[0] = new CAS_People();
        gp.cas[0].worldX = 6*gp.tileSize;
        gp.cas[0].worldY = 8*gp.tileSize;
    }
}
