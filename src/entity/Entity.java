package entity;


import java.awt.image.BufferedImage;

public class Entity {
    public int worldX, worldY, speed, movementPoints, remainingMovementPoints, manaPoints, remainingManaPoints;
    public boolean going = false;

    public BufferedImage    leftStanding, leftWalk1, leftWalk2, rightStanding, rightWalk1, rightWalk2,
                            upStanding, upWalk1, upWalk2, downStanding, downWalk1, downWalk2;
    public String direction;

}
