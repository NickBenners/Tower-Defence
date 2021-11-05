package dtowerdefence;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class Bullet extends GameObject implements ActionListener {

    private int ePosX, ePosY, type, anim = 1, movX, movY, damage;
    private Timer t = new Timer(40, this);
    private double th;

    public Bullet(GamePanel GP, int ePosX, int ePosY, int posX, int posY, int type, int damage) {
        super(GP);
        this.ePosX = ePosX;
        this.ePosY = ePosY;
        super.setPosX(posX);
        super.setPosY(posY);
        this.type = type;
        this.damage = damage;
        th = (Math.atan((double) (ePosY - super.getPosY()) / (double) (ePosX - super.getPosX())));
        movX = (int) ((super.getPosX() > ePosX ? -1 : 1) * (type == 1 ? 1 : 0.5) * Math.toDegrees(Math.cos(th)));
        movY = (int) ((super.getPosX() > ePosX ? -1 : 1) * (type == 1 ? 1 : 0.5) * Math.toDegrees(Math.sin(th)));
        t.start();
    }

    public void AIUpdate() {
        if (type != 3) {
            super.setPosX(super.getPosX() + movX);
            super.setPosY(super.getPosY() + movY);
        }
        if (super.getPosX() > 1600 || super.getPosY() > 1000 || super.getPosX() < 0 || super.getPosY() < 0) {
            delete();
        }
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.LIGHT_GRAY);
        if (super.is() || type == 3) {
            if (type == 1) {
                g.setColor(Color.BLUE);
                g.fillOval(super.getPosX() + 25, super.getPosY() + 25, 25, 25);
                g.setColor(Color.BLACK);
                g.drawOval(super.getPosX() + 25, super.getPosY() + 25, 25, 25);
            } else if (type == 2) {
                g.setColor(Color.BLUE);
                g.drawOval(super.getPosX() + 50, super.getPosY() + 50, 50, 50);
                g.setColor(Color.BLACK);
                g.drawOval(super.getPosX() + 50, super.getPosY() + 50, 50, 50);
            } else if (type == 3) {
                g.drawImage(Toolkit.getDefaultToolkit().getImage("Bullets/3/" + anim + ".png"), super.getPosX() - 200, super.getPosY() - 200, 500, 500, null);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!super.isPaused() && (super.is() || type == 3)) {
            anim++;
            //Sprey
            if (type == 3 && anim > 6) {
                super.delete();
            }
            AIUpdate();
        }
    }

    public int getType() {
        return type;
    }

    public int getDamage() {
        return damage;
    }

}
