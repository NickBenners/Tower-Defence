package dtowerdefence;

import java.awt.Color;
import java.awt.Graphics;

public class GameObject {

    private int posX, posY, Count;
    private boolean is = true;
    private GamePanel GP;

    public GameObject(GamePanel GP) {
        this.GP = GP;
    }

    public void delete() {
        GP.deleteObject(Count);
        is = false;
    }

    public boolean isPaused() {
        return GP.paused();
    }

    public int getPosX() {
        return posX;
    }

    public boolean is() {
        return is;
    }
    
    public void setIs(boolean is) {
        this.is = is;
    }

    public int getCount() {
        return Count;
    }

    public GamePanel getGP() {
        return GP;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setCount(int Count) {
        this.Count = Count;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public void paint(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect(posX, posY, 100, 100);
    }

}
