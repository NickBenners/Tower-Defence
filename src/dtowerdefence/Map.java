package dtowerdefence;

import java.awt.Graphics;
import java.io.File;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class Map {

    private int noColX, noRowY;
    private Block[][] block;
    private Point[] Path;

    public Map(String mapName) {
        try {
            Scanner sc = new Scanner(new File("Maps/" + mapName + ".txt"));
            String ColRow[] = sc.nextLine().split(",");
            noColX = Integer.parseInt(ColRow[0]);
            noRowY = Integer.parseInt(ColRow[1]);
            block = new Block[noColX][noRowY];
            sc.nextLine();
            for (int i = 0; i < noRowY; i++) {
                String det[] = sc.nextLine().split(",");
                for (int j = 0; j < noColX; j++) {
                    block[j][i] = new Block((1600 / noColX) * j, (1000 / noRowY) * i, 1000 / noRowY, 1600 / noColX, det[j].charAt(0) + "", (det[j].charAt(0) == 'b' ? bridge(j, i) : det[j].charAt(1) + ""));
                }
            }
        } catch (Exception e) {
            try {
                Scanner sc = new Scanner(new File("Maps/Map1.txt"));
                String ColRow[] = sc.nextLine().split(",");
                noColX = Integer.parseInt(ColRow[0]);
                noRowY = Integer.parseInt(ColRow[1]);
                block = new Block[noColX][noRowY];
                sc.nextLine();
                for (int i = 0; i < noRowY; i++) {
                    String det[] = sc.nextLine().split(",");
                    for (int j = 0; j < noColX; j++) {
                        block[j][i] = new Block((1600 / noColX) * j, (1000 / noRowY) * i, 1000 / noRowY, 1600 / noColX, det[j].charAt(0) + "", (det[j].charAt(0) == 'b' ? bridge(j, i) : det[j].charAt(1) + ""));
                    }
                }
            } catch (Exception ex) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Map Constructor time of the month");
            }
        }
    }

    public Point[] getPath() {
        int pathNo = 0;
        Point start = new Point(0, 0, "");
        Point finish = new Point(0, 0, "");
        for (int i = 0; i < noColX; i++) {
            for (int j = 0; j < noRowY; j++) {
                if (block[i][j].getType().equals("p")) {
                    pathNo++;
                    if (block[i][j].getDetail().equals("s")) {
                        start.setColX(i);
                        start.setRowY(j);
                        start.setDetail("r");
                    } else if (block[i][j].getDetail().equals("e")) {
                        finish.setColX(i);
                        finish.setRowY(j);
                        finish.setDetail("e");
                    }
                }
            }
        }
        Path = new Point[pathNo];
        Path[0] = start;
        Path[pathNo - 1] = finish;
        int tColX = start.getColX();
        int tRowY = start.getRowY();
        for (int i = 1; i < pathNo - 1; i++) {
            if (Path[i - 1].getDetail().equals("r") || Path[i - 1].getDetail().equals("s")) {
                tColX++;
            } else if (Path[i - 1].getDetail().equals("l")) {
                tColX--;
            } else if (Path[i - 1].getDetail().equals("u")) {
                tRowY--;
            } else if (Path[i - 1].getDetail().equals("d")) {
                tRowY++;
            }
            Path[i] = new Point(tColX, tRowY, block[tColX][tRowY].getDetail());
        }
        return Path;
    }

    public String bridge(int j, int i) {
        return (block[j - 1][i].getDetail().equals("r") ? "r" : (block[j + 1][i].getDetail().equals("l") ? "l" : (block[j][i - 1].getDetail().equals("d") ? "d" : (block[j][i + 1].getDetail().equals("u") ? "u" : "r"))));
    }

    public void paint(Graphics g) {
        for (int i = 0; i < noRowY; i++) {
            for (int j = 0; j < noColX; j++) {
                block[j][i].paint(g);
            }
        }
    }

    public Block[][] getBlock() {
        return block;
    }

    public void setBlock(Block b, int i, int i1) {
        block[i][i1] = b;
    }

    public int getNoColX() {
        return noColX;
    }

    public int getNoRowY() {
        return noRowY;
    }

}
