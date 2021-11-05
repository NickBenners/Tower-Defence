package dtowerdefence;

public class Point {

    private int colX, rowY;
    private String detail;

    public Point(int colX, int rowY, String detail) {
        this.colX = colX;
        this.rowY = rowY;
        this.detail = detail;
    }

    public int getColX() {
        return colX;
    }

    public String getDetail() {
        return detail;
    }

    public void setColX(int colX) {
        this.colX = colX;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getRowY() {
        return rowY;
    }

    public void setRowY(int rowY) {
        this.rowY = rowY;
    }

}
