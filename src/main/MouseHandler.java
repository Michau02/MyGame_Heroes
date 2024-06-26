package main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseHandler implements MouseListener {
    GamePanel gp;
    public int released_tileX;
    public int released_tileY;
    int x;
    int y;
    public boolean leftButtonPressed = false;
    public boolean rightButtonPressed = false;
    public boolean leftButtonReleased;
    public boolean rightButtonReleased;

    public boolean blockedMouseMovement = false;

    public MouseHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getButton() == 1){
            leftButtonPressed = true;
            x = e.getX();
            y = e.getY();

            gp.mouseX = (x + gp.player.worldX - gp.player.screenX + gp.player.mapOffsetX) / gp.tileSize;
            gp.mouseY = (y + gp.player.worldY - gp.player.screenY + gp.player.mapOffsetY) / gp.tileSize;
        }
        else{
            x = e.getX();
            y = e.getY();

            if(!blockedMouseMovement){
                gp.mouseX = (x + gp.player.worldX - gp.player.screenX + gp.player.mapOffsetX) / gp.tileSize;
                gp.mouseY = (y + gp.player.worldY - gp.player.screenY + gp.player.mapOffsetY) / gp.tileSize;
            }
            rightButtonPressed = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(e.getButton() == 1) {
            leftButtonPressed = false;
            leftButtonReleased = true;
            x = e.getX();
            y = e.getY();

            released_tileX = (x + gp.player.worldX - gp.player.screenX + gp.player.mapOffsetX) / gp.tileSize;
            released_tileY = (y + gp.player.worldY - gp.player.screenY + gp.player.mapOffsetY) / gp.tileSize;
        }
        else{
            rightButtonPressed = false;
            rightButtonReleased = true;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // Jeśli masz jakąś logikę do obsługi wejścia myszy, możesz dodać ją tutaj
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // Jeśli masz jakąś logikę do obsługi wyjścia myszy, możesz dodać ją tutaj
    }
}
