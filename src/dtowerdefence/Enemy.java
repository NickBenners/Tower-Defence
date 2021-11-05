package dtowerdefence;

import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class Enemy extends GameObject implements ActionListener {

    private int health = 3, tier, animCount = 1, movSpeed, pathCount, noColx, noRowy;
    private Timer t = new Timer(40, this);
    private Point[] path;

    public Enemy(int noRowy, int noColx, int tier, int waveNo, Point[] path, GamePanel GP) {
        super(GP);
        this.path = path;
        this.tier = tier;
        this.noColx = noColx;
        this.noRowy = noRowy;
        movSpeed = (tier == 3 ? 1 : 4);
        super.setPosX(path[0].getColX() * (1600 / noColx) - 100);
        super.setPosY(path[0].getRowY() * (1000 / noRowy));
        pathCount = 0;
        t.start();
    }

    public void AIUpdate() {
        //Path Following
        if (super.is()) {
            if (pathCount < path.length) {
                if (path[pathCount].getDetail().equals("r") || path[pathCount].getDetail().equals("s")) {
                    if (!(super.getPosX() > path[pathCount + 1].getColX() * 1600 / noColx)) {
                        super.setPosX(super.getPosX() + movSpeed);
                    } else {
                        pathCount++;
                    }
                } else if (path[pathCount].getDetail().equals("u")) {
                    if (!(super.getPosY() < path[pathCount + 1].getRowY() * 1000 / noRowy)) {
                        super.setPosY(super.getPosY() - movSpeed);
                    } else {
                        pathCount++;
                    }
                } else if (path[pathCount].getDetail().equals("l")) {
                    if (!(super.getPosX() < path[pathCount + 1].getColX() * 1600 / noColx)) {
                        super.setPosX(super.getPosX() - movSpeed);
                    } else {
                        pathCount++;
                    }
                } else if (path[pathCount].getDetail().equals("d")) {
                    if (!(super.getPosY() > path[pathCount + 1].getRowY() * 1000 / noRowy)) {
                        super.setPosY(super.getPosY() + movSpeed);
                    } else {
                        pathCount++;
                    }
                } //Finished?
            }
            if (path.length - 1 == pathCount) {
                if (!(super.getPosX() > 1600)) {
                    super.setPosX(super.getPosX() + movSpeed);
                } else {
                    delete();
                    super.getGP().setHP(super.getGP().getHP() - 1);
                }
            }
            //Getting smacked
            for (int i = 0; i < 1000; i++) {
                if (super.getGP().getGO()[i] instanceof Bullet) {
                    Bullet b = (Bullet) super.getGP().getGO()[i];
                    if (b.is()) {
                        //Bullet 1 or 2
                        if (b.getType() != 3) {
                            if (((super.getPosX() + 10 > b.getPosX() && super.getPosX() + 10 < b.getPosX() + (b.getType() == 1 ? 25 : 50)) || (super.getPosX() + 90 > b.getPosX() && super.getPosX() + 90 < b.getPosX() + (b.getType() == 1 ? 25 : 50)))
                                    && ((super.getPosY() > b.getPosY() && super.getPosY() < b.getPosY() + (b.getType() == 1 ? 25 : 50)) || (super.getPosY() + 100 > b.getPosY() && super.getPosY() + 100 < b.getPosY() + (b.getType() == 1 ? 25 : 50)))) {
                                super.getGP().deleteObject(i);
                                health -= (b.getType() == 1 ? 1 : 2);
                                if (health <= 0) {
                                    delete();
                                    super.getGP().setMoney(super.getGP().getMoney() + (tier == 1 ? 5 : 15));
                                }
                            }
                        } //Bullet 3
                        else {
                            if (super.getPosX() > b.getPosX() - 200 && super.getPosX() < b.getPosX() + 300 && super.getPosX() > b.getPosY() - 200 && super.getPosY() < b.getPosY() + 300) {
                                super.getGP().deleteObject(i);
                                health -= 2;
                                if (health <= 0) {
                                    delete();
                                    super.getGP().setMoney(super.getGP().getMoney() + (tier == 1 ? 5 : 15));
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        if (super.is()) {
            g.drawImage(Toolkit.getDefaultToolkit().getImage("Enemies/" + tier + "/" + animCount + ".png"), super.getPosX(), super.getPosY(), 100, 100, null);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!super.isPaused()) {
            if (super.getGP().getAnim()) {
                if (tier == 1) {
                    animCount %= 5;
                } else if (tier == 2) {
                    animCount %= 4;
                }
                animCount++;
            }
            AIUpdate();
        }
    }
    
    @Override
    public void delete() {
        super.getGP().deleteObject(super.getCount());
        super.setIs(false);
        super.getGP().setWaveNo(super.getGP().getWaveNo() + 1);
        super.getGP().startWave();
    }

    public int getHealth() {
        return health;
    }

    public int getTier() {
        return tier;
    }

    public int getNoColx() {
        return noColx;
    }

    public int getNoRowy() {
        return noRowy;
    }

}
