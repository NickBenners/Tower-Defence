package dtowerdefence;

import java.awt.Graphics;
import java.awt.Toolkit;

public class Block {

    private int xPos, yPos, Height, Width, animCount;
    private String type, detail;
    private boolean colour = false, hasTower = false;

    public Block(int xPos, int yPos, int Height, int Width, String type, String detail) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.Height = Height;
        this.Width = Width;
        this.type = type;
        this.detail = detail;
    }

    public int getxPos() {
        return xPos;
    }

    public boolean hasTower() {
        return hasTower;
    }

    public String getType() {
        return type;
    }

    public String getDetail() {
        return detail;
    }

    public int getyPos() {
        return yPos;
    }

    public int getHeight() {
        return Height;
    }

    public int getWidth() {
        return Width;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    public void setHeight(int Height) {
        this.Height = Height;
    }

    public void setWidth(int Width) {
        this.Width = Width;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setColour(boolean colour) {
        this.colour = colour;
    }

    public void setTower(boolean hasTower) {
        this.hasTower = hasTower;
    }

    public void paint(Graphics g) {
        if (detail.equals("a")) {
            animCount %= 3;
            animCount++;
            g.drawImage(Toolkit.getDefaultToolkit().getImage("Blocks/" + type + "/" + detail + "/" + animCount + "/" + colour + ".png"), xPos, yPos, Width, Height, null);
        } else {
            g.drawImage(Toolkit.getDefaultToolkit().getImage("Blocks/" + type + "/" + detail + "/" + colour + ".png"), xPos, yPos, Width, Height, null);
        }
    }

}
