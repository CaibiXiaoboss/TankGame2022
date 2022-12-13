package tankgame;

/**
 * 一个Node表示一个敌人坦克的信息，用于存储存档文件中读取出来的数据
 */

public class Node {
    private int x;
    private int y;
    private int direct;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDirect() {
        return direct;
    }

    public void setDirect(int direct) {
        this.direct = direct;
    }

    public Node(int x, int y, int direct) {
        this.x = x;
        this.y = y;
        this.direct = direct;
    }
}
