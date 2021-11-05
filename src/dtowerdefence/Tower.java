package dtowerdefence;

import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class Tower extends GameObject implements ActionListener {

    private int type, bulletCD, anim, damage, range;
    private Timer t = new Timer(40, this);
    private boolean upgraded, pop;

    public Tower(int posX, int posY, int type, int anim, int bulletCD, boolean upgraded, boolean pop, GamePanel GP) {
        super(GP);
        super.setPosX(posX);
        super.setPosY(posY);
        this.type = type;
        this.upgraded = upgraded;
        this.pop = pop;
        this.bulletCD = bulletCD;        if (!upgraded) {
            range = ((type == 2 || type == 12) ? 1 : 2);
            damage = (type == 1 ? 1 : 2);
        }
        t.start();
        //Colourful
        if (!(type == 11 || type == 21 || type == 31)) {
            int arrX = super.getPosX() / (1600 / super.getGP().getMap().getNoColX());
            int arrY = super.getPosY() / (1000 / super.getGP().getMap().getNoRowY());
            if (super.getGP().getMap().getNoColX() - 1 != arrX) {
                super.getGP().getMap().getBlock()[super.getPosX() / (1600 / super.getGP().getMap().getNoColX()) + 1][super.getPosY() / (1000 / super.getGP().getMap().getNoRowY())].setColour(true);
            }
            if (arrX != 0) {
                super.getGP().getMap().getBlock()[super.getPosX() / (1600 / super.getGP().getMap().getNoColX()) - 1][super.getPosY() / (1000 / super.getGP().getMap().getNoRowY())].setColour(true);
            }
            if (super.getGP().getMap().getNoRowY() - 1 != arrY) {
                super.getGP().getMap().getBlock()[super.getPosX() / (1600 / super.getGP().getMap().getNoColX())][super.getPosY() / (1000 / super.getGP().getMap().getNoRowY()) + 1].setColour(true);
            }
            if (arrY != 0) {
                super.getGP().getMap().getBlock()[super.getPosX() / (1600 / super.getGP().getMap().getNoColX())][super.getPosY() / (1000 / super.getGP().getMap().getNoRowY()) - 1].setColour(true);
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        if (super.is()) {
            g.drawImage(Toolkit.getDefaultToolkit().getImage("Towers/" + type + upgraded + ".png"), super.getPosX(), super.getPosY(), 100, 100, null);
            if (pop) {
                g.drawImage(Toolkit.getDefaultToolkit().getImage("Upgrades/" + anim + ".png"), super.getPosX() - 100, super.getPosY() - 100, 300, 300, null);
            }
        }
    }

    public void AIUpdate() {
        boolean shooting = false;
        if (super.is()) {
            if (bulletCD == 1) {
                for (int i = 0; i < 1000; i++) {
                    if ((super.getGP().getGO()[i] instanceof Enemy) && !shooting) {
                        Enemy E = (Enemy) (super.getGP().getGO()[i]);
                        if (E.is()) {
                            //In Range?
                            if (E.getPosX() > super.getPosX() - 100 * range && E.getPosX() < super.getPosX() + 100 + (100 * range) && E.getPosY() > super.getPosY() - 100 * range && E.getPosY() < super.getPosY() + 100 + (100 * range)) {
                                spawnBullet(E.getPosX(), E.getPosY());
                                shooting = true;
                            }
                        }
                    }
                }
            }
        }
    }

    public void spawnBullet(int ePosX, int ePosY) {
        Bullet b = new Bullet(super.getGP(), ePosX, ePosY, super.getPosX(), super.getPosY(), type, damage);
        super.getGP().addGO(b);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!super.isPaused()) {
            bulletCD++;
            switch (type) {
                case 1:
                    if (bulletCD > 16) {
                        bulletCD = 1;
                    }
                    break;
                case 2:
                    if (bulletCD > 25) {
                        bulletCD = 1;
                    }
                    break;
                case 3:
                    if (bulletCD > 25) {
                        bulletCD = 1;
                    }
                    break;
            }
            if (pop && anim != 11) {
                anim++;
            }
            AIUpdate();
        }
    }

    @Override
    public void delete() {
        super.getGP().deleteObject(super.getCount());
        super.setIs(false);
        if (!(type == 11 || type == 21 || type == 31)) {
            int arrX = super.getPosX() / (1600 / super.getGP().getMap().getNoColX());
            int arrY = super.getPosY() / (1000 / super.getGP().getMap().getNoRowY());
            if (super.getGP().getMap().getNoColX() - 1 != arrX) {
                super.getGP().getMap().getBlock()[super.getPosX() / (1600 / super.getGP().getMap().getNoColX()) + 1][super.getPosY() / (1000 / super.getGP().getMap().getNoRowY())].setColour(false);
            }
            if (arrX != 0) {
                super.getGP().getMap().getBlock()[super.getPosX() / (1600 / super.getGP().getMap().getNoColX()) - 1][super.getPosY() / (1000 / super.getGP().getMap().getNoRowY())].setColour(false);
            }
            if (super.getGP().getMap().getNoRowY() - 1 != arrY) {
                super.getGP().getMap().getBlock()[super.getPosX() / (1600 / super.getGP().getMap().getNoColX())][super.getPosY() / (1000 / super.getGP().getMap().getNoRowY()) + 1].setColour(false);
            }
            if (arrY != 0) {
                super.getGP().getMap().getBlock()[super.getPosX() / (1600 / super.getGP().getMap().getNoColX())][super.getPosY() / (1000 / super.getGP().getMap().getNoRowY()) - 1].setColour(false);
            }
            super.getGP().setMoney(super.getGP().getMoney() + 10);
        }
    }

    public int getType() {
        return type;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getBulletCD() {
        return bulletCD;
    }

    public boolean isUpgraded() {
        return upgraded;
    }

    public boolean getPop() {
        return pop;
    }

    public void setPop(boolean pop) {
        this.pop = pop;
    }

    public void setUpgraded(boolean upgraded) {
        this.upgraded = upgraded;
    }

    public void setAnim(int anim) {
        this.anim = anim;
    }
}
