package main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MouseMotionHandler implements MouseMotionListener {

    GamePanel gp;
    public boolean moveLeft = false, moveUp = false, moveDown = false, moveRight = false;

    public MouseMotionHandler(GamePanel gp){
        this.gp = gp;
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

        moveLeft = e.getX() < 3;
        moveRight = e.getX() > gp.screenWidth - 3;
        moveUp = e.getY() < 3;
        moveDown = e.getY() > gp.screenHeight -3;
    }
}
