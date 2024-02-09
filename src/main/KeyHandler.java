package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    GamePanel gp;
    public boolean  upPressed= false, leftPressed= false, rightPressed= false, downPressed= false,
                    endTurnPressed = false, endTurnReleased = false, movePressed = false, yKeyPressed = false;

    public KeyHandler(GamePanel gp){
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        if(e.getKeyCode() == KeyEvent.VK_Y){
            yKeyPressed = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_E){
            endTurnReleased = false;
            endTurnPressed = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_M){
            movePressed = true;
        }

       /* if(e.getKeyCode() == KeyEvent.VK_W){
            upPressed = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_S){
            downPressed = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_A){
            leftPressed= true;
        }
        if(e.getKeyCode() == KeyEvent.VK_D){
            rightPressed = true;
        }*/
    }

    @Override
    public void keyReleased(KeyEvent e) {

        if(e.getKeyCode() == KeyEvent.VK_Y){
            yKeyPressed = false;
        }
        if(e.getKeyCode() == KeyEvent.VK_E){
            endTurnPressed = false;
            endTurnReleased = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_M){
            movePressed = false;
        }
       /* if(e.getKeyCode() == KeyEvent.VK_W){
            upPressed = false;
        }
        if(e.getKeyCode() == KeyEvent.VK_S){
            downPressed = false;
        }
        if(e.getKeyCode() == KeyEvent.VK_A){
            leftPressed= false;
        }
        if(e.getKeyCode() == KeyEvent.VK_D){
            rightPressed = false;
        }*/
    }
}
