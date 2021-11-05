package dtowerdefence;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.PrintWriter;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements MouseListener, MouseMotionListener, ActionListener, KeyListener {

    private Map map = new Map("Map1");
    private Map createMap;
    private String mouseC = "Blue", Menu = "Menu", Stat = "Home", newMap = "";
    private boolean paused, Sound, Anim = true;
    private int HP = 10, Money = 25, waveNo = 1;
    private Timer t = new Timer(40, this);
    private GameObject[] arr = new GameObject[1000];
    private int[] rect = new int[3];

    public GamePanel() {
        t.start();
    }

    public void newGame() {
        for (int i = 0; i < 1000; i++) {
            if (arr[i] != null) {
                arr[i].delete();
            }
        }
        HP = 10;
        Money = 25;
        rect[0] = 0;
        rect[1] = 0;
        rect[2] = 0;
        startWave();
        t.restart();
    }

    public void startWave() {
        if (waveNo == 1) {
            addGO(new Enemy(map.getNoRowY(), map.getNoColX(), 1, 5, map.getPath(), this));
        } else {
            addGO(new Enemy(map.getNoRowY(), map.getNoColX(), 2, 4 + waveNo, map.getPath(), this));
        }
    }

    @Override
    public void paint(Graphics g) {
        if (HP <= 0) {
            g.drawImage(Toolkit.getDefaultToolkit().getImage("YOU LOSE.png"), 0, 0, this);
        } else {
            if (Stat.equals("Home")) {
                g.drawImage(Toolkit.getDefaultToolkit().getImage("Home.png"), 0, 0, this);
            } else if (Stat.equals("CreateT")) {
                g.drawImage(Toolkit.getDefaultToolkit().getImage("Create Tut.png"), 0, 0, this);
            } else if (Stat.equals("Create")) {
                createMap.paint(g);
            } else if (Stat.equals("Help")) {
                g.drawImage(Toolkit.getDefaultToolkit().getImage("Help.png"), 0, 0, this);
            } else {
                g.setColor(Color.WHITE);
                g.fillRect(0, 0, getWidth(), getHeight());
                map.paint(g);
                for (int i = 0; i < 1000; i++) {
                    if (arr[i] != null) {
                        arr[i].paint(g);
                    }
                }
                g.setColor(new Color(134, 176, 240, 50));
                g.fillRect(rect[0], rect[1], rect[2], rect[2]);
                g.setColor(new Color(134, 176, 240));
                g.drawRect(rect[0], rect[1], rect[2], rect[2]);
                g.drawImage(Toolkit.getDefaultToolkit().getImage("HUD.png"), 0, 0, 1600, 1000, this);
                g.setColor(Color.BLACK);
                g.setFont(new Font("Georgia", Font.BOLD, 24));
                g.drawString(HP + "", 500, 845);
                g.drawString(Money + "", 500, 895);
                if (paused) {
                    g.drawImage(Toolkit.getDefaultToolkit().getImage("Menu/" + Menu + ".png"), 500, 125, this);
                    if (Menu.equals("Options")) {
                        g.drawImage(Toolkit.getDefaultToolkit().getImage("Menu/Sound" + Sound + ".png"), 525, 375, 100, 100, this);
                        g.drawImage(Toolkit.getDefaultToolkit().getImage("Menu/Anim" + Anim + ".png"), 725, 375, 100, 100, this);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        GamePanel Game = new GamePanel();
        JFrame jf = new JFrame("Tower Defence");
        jf.setSize(1606, 1035);
        jf.add(Game);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setResizable(false);
        Game.addMouseListener(Game);
        Game.addMouseMotionListener(Game);
        jf.addKeyListener(Game);
    }

    public void saveMap() {
        try {
            if (newMap.equals("")) {
                if (JOptionPane.showInputDialog("Load Map?").equals("Yes")) {
                    newMap = JOptionPane.showInputDialog("Map Name?");
                    createMap = new Map(newMap);
                } else {
                    newMap = JOptionPane.showInputDialog("Map Output?");
                }
            }
            PrintWriter pw = new PrintWriter(new File("Maps/" + newMap + ".txt"));
            pw.print("16,10");
            pw.println("");
            for (int i = 0; i < 10; i++) {
                pw.println("");
                for (int j = 0; j < 16; j++) {
                    if (j != 15) {
                        if (createMap.getBlock()[j][i].getType().equals("b")) {
                            pw.print(createMap.getBlock()[j][i].getType() + "b,");
                        } else {
                            pw.print(createMap.getBlock()[j][i].getType() + createMap.getBlock()[j][i].getDetail() + ",");
                        }
                    } else {
                        if (createMap.getBlock()[j][i].getType().equals("b")) {
                            pw.print(createMap.getBlock()[j][i].getType());
                        } else {
                            pw.print(createMap.getBlock()[j][i].getType() + createMap.getBlock()[j][i].getDetail());
                        }
                    }
                }
            }
            pw.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Create Map struggling..");
            e.printStackTrace();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (Stat.equals("Home")) {
            if (e.getX() > 300 && e.getX() < 492 && e.getY() > 663 && e.getY() < 781) {
                Stat = "Game";
                String mapname = JOptionPane.showInputDialog("Map Name?");
                map = new Map(mapname.equals("") ? "Map1" : mapname);
                newGame();
            } else if (e.getX() > 835 && e.getX() < 1095 && e.getY() > 657 && e.getY() < 780) {
                Stat = "CreateT";
            } else if (e.getX() > 1410 && e.getX() < 1564 && e.getY() > 30 && e.getY() < 110) {
                Stat = "Help";
            }
        } else if (Stat.equals("CreateT")) {
            Stat = "Create";
            mouseC = "r";
            createMap = new Map("Creator");
            saveMap();
        } else if (Stat.equals("Help")) {
            if (e.getX() > 1337 && e.getX() < 1470 && e.getY() > 32 && e.getY() < 106) {
                Stat = "Home";
            }
        } else if (Stat.equals("Create")) {
            saveMap();
            int colXno = (e.getX() / 100);
            int rowYno = (e.getY() / 100);
            System.out.println(colXno + " : " + rowYno);
            createMap.setBlock(new Block(colXno * 100, rowYno * 100, 100, 100, ((mouseC.equals("r") || mouseC.equals("l") || mouseC.equals("u") || mouseC.equals("d")) ? "p" : (mouseC.equals("b") ? "b" : mouseC)), (mouseC.equals("w") ? "a" : (mouseC.equals("g") ? (int) (Math.random() * 3) + 1 + "" : (mouseC.equals("t") ? "1" : mouseC)))), colXno, rowYno);
        } else {
//            Centre Tower
            if (!paused) {
                for (int i = 0; i < 1000; i++) {
                    if (arr[i] instanceof Tower) {
                        Tower To = (Tower) (arr[i]);
                        int colXno = e.getX() / (1600 / map.getNoColX()) * (1600 / map.getNoColX());
                        int rowYno = e.getY() / (1000 / map.getNoRowY()) * (1000 / map.getNoRowY());
                        if (map.getBlock()[colXno / (1600 / map.getNoColX())][rowYno / (1000 / map.getNoRowY())].getType().equals("t")) {
                            if (!(map.getBlock()[colXno / (1600 / map.getNoColX())][rowYno / (1000 / map.getNoRowY())].hasTower()) || (To.getPosX() == colXno && To.getPosY() == rowYno && !To.is())) {
                                if (Money >= (mouseC.equals("Blue") ? 10 : (mouseC.equals("Red") ? 15 : 25))) {
                                    Tower T = new Tower(colXno, rowYno, (mouseC.equals("Blue") ? 1 : (mouseC.equals("Red") ? 2 : 3)), 0, 2, false, false, this);
                                    Money -= (mouseC.equals("Blue") ? 10 : (mouseC.equals("Red") ? 15 : 25));
                                    addGO(T);
                                    map.getBlock()[colXno / (1600 / map.getNoColX())][rowYno / (1000 / map.getNoRowY())].setTower(true);
                                    rect[0] = 0;
                                    rect[1] = 0;
                                    rect[2] = 0;
                                    break;
                                }
                            }
                        }
                        if (arr[i] != null && (To.getPop() && (To.getType() != 11 || To.getType() != 21 || To.getType() != 31))) {
                            if (e.getY() < To.getPosY() + 147 && e.getY() > To.getPosY() + 102) {
                                if (e.getX() < To.getPosX() - 48 && e.getX() > To.getPosX() - 95) {
                                    //Red
                                    To.setUpgraded(true);
                                    break;
                                } else if (e.getX() < To.getPosX() - 3 && e.getX() > To.getPosX() - 47) {
                                    //Yellow
                                    if (Money >= 10) {
                                        To.setUpgraded(true);
                                        To.setRange(To.getRange() + 1);
                                        Money -= 10;
                                    }
                                    break;
                                }
                            } else if (e.getY() > To.getPosY() + 147 && e.getY() < To.getPosY() + 193) {
                                if (e.getX() < To.getPosX() - 48 && e.getX() > To.getPosX() - 95) {
                                    //Blue
                                    if (Money >= 5) {
                                        To.setUpgraded(true);
                                        To.setDamage(To.getDamage() + 1);
                                        Money -= 5;
                                    }
                                    break;
                                } else if (e.getX() < To.getPosX() - 3 && e.getX() > To.getPosX() - 47) {
                                    //Green
                                    To.setUpgraded(true);
                                    break;
                                }
                            } else if (e.getY() > To.getPosY() + 74 && e.getY() < To.getPosY() + 101 && e.getX() > To.getPosX() - 63 && e.getX() < To.getPosX() - 33) {
                                To.delete();
                                map.getBlock()[colXno / (1600 / map.getNoColX())][rowYno / (1000 / map.getNoRowY())].setTower(false);
                                break;
                            }
                        }
                    }
                }
            }
            //Paused?
            if (((e.getX() > 2 && e.getX() < 19) || (e.getX() > 28 && e.getX() < 45)) && e.getY() < 52 && e.getY() > 2) {
                paused = !paused;
            }
            //Menu Clicking
            if (paused && e.getX() > 511 & e.getX() < 1085) {
                switch (Menu) {
                    case "Menu":
                        if (e.getY() > 385 && e.getY() < 526) {
                            Menu = "Options";
                        } else if (e.getY() > 532 && e.getY() < 663) {
                            Menu = "New";
                        } else if (e.getY() > 665 && e.getY() < 855) {
                            Menu = "Exit";
                        }
                        break;
                    case "Options":
                        if (e.getY() > 377 && e.getY() < 472) {
                            if (e.getX() > 527 && e.getX() < 621) {
                                Sound = !Sound;
                            } else if (e.getX() > 727 && e.getX() < 821) {
                                Anim = !Anim;
                            }
                        } else if (e.getY() > 530 && e.getY() < 662) {
                            Menu = "New";
                        } else if (e.getY() > 667 && e.getY() < 855) {
                            Menu = "Exit";
                        }
                        break;
                    case "New":
                        if (e.getY() > 511 && e.getY() < 599) {
                            if (e.getX() > 616 && e.getX() < 761) {
                                //New Game
                                newGame();
                            }
                        } else if (e.getY() > 253 && e.getY() < 357) {
                            Menu = "Options";
                        } else if (e.getY() > 663 && e.getY() < 854) {
                            Menu = "Exit";
                        }
                        break;
                    case "Exit":
                        if (e.getY() > 694 && e.getY() < 773) {
                            if (e.getX() > 598 && e.getX() < 751) {
                                //Quit
                                Stat = "Home";
                            }
                        } else if (e.getY() > 254 && e.getY() < 362) {
                            Menu = "Options";
                        } else if (e.getY() > 363 && e.getY() < 479) {
                            Menu = "New";
                        }
                        break;
                }
            }
        }
        repaint();
    }

    public void addGO(GameObject GO) {
        for (int i = 0; i < 1000; i++) {
            if (arr[i] == null) {
                GO.setCount(i);
                GO.setIs(true);
                arr[i] = GO;
                break;
            } else if (!arr[i].is()) {
                GO.setIs(true);
                GO.setCount(i);
                arr[i] = GO;
                break;
            }
        }
    }

    public void sortGO() {
        for (int i = 0; i < 1000 - 1; i++) {
            for (int j = i; j < 1000 - 1 - i; j++) {
                if (arr[j + 1] == null && arr[j] != null) {
                    GameObject Temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = Temp;
                    arr[j + 1].setCount(j + 1);
                } else if ((arr[j + 1] instanceof Tower || arr[j + 1] instanceof Enemy) && arr[j] instanceof Bullet) {
                    GameObject Temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = Temp;
                    arr[j].setCount(j);
                    arr[j + 1].setCount(j + 1);
                }
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (Stat.equals("Create")) {
            if (e.getExtendedKeyCode() == 37) {
                mouseC = "l";
            } else if (e.getExtendedKeyCode() == 38) {
                mouseC = "u";
            } else if (e.getExtendedKeyCode() == 39) {
                mouseC = "r";
            } else if (e.getExtendedKeyCode() == 40) {
                mouseC = "d";
            } else if (e.getExtendedKeyCode() == 66) {
                mouseC = "b";
            } else if (e.getExtendedKeyCode() == 87) {
                mouseC = "w";
            } else if (e.getExtendedKeyCode() == 71) {
                mouseC = "g";
            } else if (e.getExtendedKeyCode() == 84) {
                mouseC = "t";
            }
        } else {
            if (e.getExtendedKeyCode() == 49) {
                mouseC = "Blue";
            } else if (e.getExtendedKeyCode() == 50) {
                mouseC = "Red";
            } else if (e.getExtendedKeyCode() == 51) {
                mouseC = "Green";
            }
            repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        Image imageM = Toolkit.getDefaultToolkit().getImage("MouseBlue.png");
        Cursor aM = Toolkit.getDefaultToolkit().createCustomCursor(imageM, new Point(this.getX(), this.getY()), "");
        this.setCursor(aM);
        if (!Stat.equals("Create")) {
            Image image = Toolkit.getDefaultToolkit().getImage("Mouse" + mouseC + ".png");
            Cursor a = Toolkit.getDefaultToolkit().createCustomCursor(image, new Point(this.getX(), this.getY()), "");
            this.setCursor(a);
            if (e.getX() > 312 && e.getX() < 343 && e.getY() > 765 && e.getY() < 799) {
                mouseC = "Blue";
            } else if (e.getX() > 173 && e.getX() < 205 && e.getY() > 811 && e.getY() < 828) {
                mouseC = "Red";
            } else if (e.getX() > 107 && e.getX() < 158 && e.getY() > 915 && e.getY() < 950) {
                mouseC = "Green";
            }
            //Tower
            for (int i = 0; i < 1000; i++) {
                if (arr[i] instanceof Tower) {
                    Tower To = (Tower) (arr[i]);
                    if (To.getType() == 11 || To.getType() == 21 || To.getType() == 31) {
                        To.delete();
                    }
                    if (!To.getPop()) {
                        To.setAnim(0);
                    } else if (!(e.getX() > To.getPosX() - 100 && e.getX() < To.getPosX() + 200 && e.getY() > To.getPosY() - 100 && e.getY() < To.getPosY() + 200)) {
                        To.setPop(false);
                    }
                }
            }
            rect[0] = 0;
            rect[1] = 0;
            rect[2] = 0;
            if (!paused) {
                for (int i = 0; i < 1000; i++) {
                    if (arr[i] instanceof Tower) {
                        Tower To = (Tower) (arr[i]);
                        int colXno = e.getX() / (1600 / map.getNoColX()) * (1600 / map.getNoColX());
                        int rowYno = e.getY() / (1000 / map.getNoRowY()) * (1000 / map.getNoRowY());
                        if (map.getBlock()[colXno / (1600 / map.getNoColX())][rowYno / (1000 / map.getNoRowY())].getType().equals("t")) {
                            if (To.getPosX() == colXno && To.getPosY() == rowYno && !To.is()) {
                                Tower T = new Tower(colXno, rowYno, (mouseC.equals("Blue") ? 11 : (mouseC.equals("Red") ? 21 : 31)), 0, 2, false, false, this);
                                addGO(T);
                                rect[0] = colXno - (T.getType() == 21 ? 100 : 200);
                                rect[1] = rowYno - (T.getType() == 21 ? 100 : 200);
                                rect[2] = (T.getType() == 21 ? 300 : 500);
                                break;
                            } else if (To.getType() != 11 && To.getType() != 21 && To.getType() != 31 && To.getPosX() == colXno && To.getPosY() == rowYno) {
                                To.setPop(true);
                                break;
                            }
                        }
                    } else {
                        int colXno = e.getX() / (1600 / map.getNoColX()) * (1600 / map.getNoColX());
                        int rowYno = e.getY() / (1000 / map.getNoRowY()) * (1000 / map.getNoRowY());
                        if (map.getBlock()[colXno / (1600 / map.getNoColX())][rowYno / (1000 / map.getNoRowY())].getType().equals("t")) {
                            if (!(map.getBlock()[colXno / (1600 / map.getNoColX())][rowYno / (1000 / map.getNoRowY())].hasTower())) {
                                Tower T = new Tower(colXno, rowYno, (mouseC.equals("Blue") ? 11 : (mouseC.equals("Red") ? 21 : 31)), 0, 2, false, false, this);
                                addGO(T);
                                rect[0] = colXno - (T.getType() == 21 ? 100 : 200);
                                rect[1] = rowYno - (T.getType() == 21 ? 100 : 200);
                                rect[2] = (T.getType() == 21 ? 300 : 500);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    public void deleteObject(int Index) {
        arr[Index].setIs(false);
    }

    public GameObject[] getGO() {
        return arr;
    }

    public Map getMap() {
        return map;
    }

    public boolean paused() {
        return paused;
    }

    public boolean getSound() {
        return Sound;
    }

    public int getWaveNo() {
        return waveNo;
    }

    public void setWaveNo(int waveNo) {
        this.waveNo = waveNo;
    }

    public boolean getAnim() {
        return Anim;
    }

    public int getMoney() {
        return Money;
    }

    public int getHP() {
        return HP;
    }

    public void setMoney(int Money) {
        this.Money = Money;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        sortGO();
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
