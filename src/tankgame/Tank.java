package tankgame;

/**
 * 坦克抽象类
 */
public abstract class Tank {
    private int x;//坦克的横坐标
    private int y;//坦克的纵坐标
    private int direct;
    private int type;
    private int speed = 10;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getDirect() {
        return direct;
    }

    public void setDirect(int direct) {
        this.direct = direct;
    }

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

    public Tank(int x, int y) {
        this.x = x;
        this.y = y;
    }
    //坦克移动的方法
    public void moveUp(){
        if(y > 0 ){
            y -= speed;
        }
    }
    public void moveDown(){
        if(y < 630){
            y += speed;
        }
    }
    public void moveLeft(){
        if(x > 0){
            x -= speed;
        }
    }
    public void moveRight(){
        if(x < 900){
            x += speed;
        }
    }

    //判断是否存在坦克的方法，要求传入一个对象判断传入的坦克和其他坦克的体积是否重合
    //重合返回false
    //判断条件：与关系，当传入的坦克的某一个边界的x或y值满足在横纵坐标的某个区间内，如 坦克x < 传入x <坦克x + 80
    //则该坐标与坦克重合。
    public boolean tankPass(Tank tank){
        switch (tank.direct){
            //up
            case 0:
                if(tank.y > this.y && tank.y < this.y + 80 ||
                        tank.y + 80 > this.y &&  tank.y + 80 < this.y + 80){
                    return false;
                }
                break;
            //left
            case 1:
                if(tank.x > this.x  && tank.x < this.x + 80 ||
                        tank.x + 80 > this.x && tank.x + 80 < this.x + 80){
                    return false;
                }
                break;
            //right
            case 2:
                if(tank.x + 80 > this.x &&  tank.x + 80 < this.x + 80 ||
                        tank.x > this.x  && tank.x < this.x + 80){
                    return false;
                }
                break;
            //down
            case 3:
                if(tank.y + 80 > this.y &&  tank.y + 80 < this.y + 80 ||
                        tank.y > this.y && tank.y < this.y + 80){
                    return false;
                }
        }
        return true;
    }
}
