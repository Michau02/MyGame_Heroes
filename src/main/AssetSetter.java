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
        set(0,5,10); // type - 0 -> wood
                                // type = 1 -> gold

        gp.obj.add(new OBJ_Wood());
        gp.obj.get(1).worldX = 16*gp.tileSize;
        gp.obj.get(1).worldY = 12*gp.tileSize;

        gp.obj.add(new OBJ_Wood());
        gp.obj.get(2).worldX = 9*gp.tileSize;
        gp.obj.get(2).worldY = 17*gp.tileSize;

        gp.obj.add(new OBJ_Wood());
        gp.obj.get(3).worldX = 39*gp.tileSize;
        gp.obj.get(3).worldY = 11*gp.tileSize;

        gp.obj.add(new OBJ_Wood());
        gp.obj.get(4).worldX = 26*gp.tileSize;
        gp.obj.get(4).worldY = 21*gp.tileSize;

        gp.obj.add(new OBJ_Wood());
        gp.obj.get(5).worldX = 44*gp.tileSize;
        gp.obj.get(5).worldY = 10*gp.tileSize;

        gp.obj.add(new OBJ_Gold());
        gp.obj.get(6).worldX = 2*gp.tileSize;
        gp.obj.get(6).worldY = 4*gp.tileSize;

        gp.obj.add(new OBJ_Gold());
        gp.obj.get(7).worldX = 6*gp.tileSize;
        gp.obj.get(7).worldY = 19*gp.tileSize;

        gp.obj.add(new OBJ_Gold());
        gp.obj.get(7).worldX = 6*gp.tileSize;
        gp.obj.get(7).worldY = 19*gp.tileSize;

        gp.obj.add(new OBJ_Gold());
        gp.obj.get(7).worldX = 6*gp.tileSize;
        gp.obj.get(7).worldY = 19*gp.tileSize;

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
        gp.player.playersCastles.add(gp.cas[0]);
    }
    public void set(int type, int x, int y) {
        if(type >= 0 && type < 2){
            switch (type) {
                case 0:
                    gp.obj.add(new OBJ_Wood());
                    break;
                case 1:
                    gp.obj.add(new OBJ_Gold());
                    break;
            }
            gp.obj.get(gp.obj.size()-1).worldX = x*gp.tileSize;
            gp.obj.get(gp.obj.size()-1).worldY = y*gp.tileSize;
        }
    }
}
