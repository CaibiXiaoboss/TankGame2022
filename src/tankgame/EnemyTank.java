package tankgame;

import java.util.Vector;

/**
 * 敌方坦克
 */
public class EnemyTank extends Tank implements Runnable{
    //在敌人的坦克类，创建一个Vector集合
    Vector<Shot> shots = new Vector<>();
    //定义一个tank集合用于判断此坦克是否和集合中的坦克重叠
    //再用一个set方法在画板中的集合赋给enemyTanks中
    Vector<EnemyTank> enemyTanks = new Vector<>();
    //敌人坦克的生命属性
    boolean isLive = true;

    public EnemyTank(int x, int y) {
        super(x, y);
    }

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
    }

    public void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        this.enemyTanks = enemyTanks;
    }

    //定义一个判断重合的方法
    public boolean noTouchTank(){
        switch (this.getDirect()){
            //up
            case 0:
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    if (enemyTank != this) {
                        //判断this左上角的点进入enemyTank的体积内，即this对象的x在[enemyTank.x , enemyTank.x + 80]
                        //并且this对象的y在[enemyTank.y,enemyTank.y + 80],这个区间，关系为&&与关系
                        if(this.getY() >= enemyTank.getY() &&
                                this.getY() <= enemyTank.getY() + 80 &&
                                this.getX() >= enemyTank.getX() &&
                                this.getX() <= enemyTank.getX() + 80){
                            return false;
                        }
                        //判断this右上角的点与集合中坦克的面积关系
                        if(this.getX() + 80 >= enemyTank.getX() &&
                                this.getX() + 80 <= enemyTank.getX() + 80 &&
                                this.getY() >= enemyTank.getY() &&
                                this.getY() <= enemyTank.getY() + 80){
                            return false;
                        }
                    }
                }
                break;
            //left
            case 1:
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    if(enemyTank != this){
                        if(this.getY() >= enemyTank.getY() &&
                                this.getY() <= enemyTank.getY() + 80 &&
                                this.getX() >= enemyTank.getX() &&
                                this.getX() <= enemyTank.getX() + 80){
                            return false;
                        }
                        if(this.getX() >= enemyTank.getX() &&
                                this.getX()  <= enemyTank.getX() + 80 &&
                                this.getY() + 80 >= enemyTank.getY() &&
                                this.getY() + 80 <= enemyTank.getY() + 80){
                            return false;
                        }
                    }
                }
                break;
            //right
            case 2:
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    if(enemyTank != this){
                        if(this.getY() >= enemyTank.getY() &&
                                this.getY() <= enemyTank.getY() + 80 &&
                                this.getX() + 80 >= enemyTank.getX() &&
                                this.getX() + 80 <= enemyTank.getX() + 80){
                            return false;
                        }
                        if(this.getX() + 80 >= enemyTank.getX() &&
                                this.getX() + 80 <= enemyTank.getX() + 80 &&
                                this.getY() + 80 >= enemyTank.getY() &&
                                this.getY() + 80 <= enemyTank.getY() + 80){
                            return false;
                        }
                    }
                }
                break;
            //down
            case 3:
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    if(enemyTank != this){
                        if(this.getY() + 80 >= enemyTank.getY() &&
                                this.getY() + 80 <= enemyTank.getY() + 80 &&
                                this.getX() >= enemyTank.getX() &&
                                this.getX() <= enemyTank.getX() + 80){
                            return false;
                        }
                        if(this.getX() + 80 >= enemyTank.getX() &&
                                this.getX() + 80 <= enemyTank.getX() + 80 &&
                                this.getY() + 80 >= enemyTank.getY() &&
                                this.getY() + 80 <= enemyTank.getY() + 80){
                            return false;
                        }
                    }
                }
                break;
        }
        return true;
    }

    //线程的运行方法
    @Override
    public void run() {
        while (true){
            //子弹的进程
            //判断shots集合里是否有子弹，如果没有就new一个对象加进去
            //判断坦克是否存活，子弹集合中的对象是否少于4个
            if(isLive && shots.size() < 4){
                Shot shot = null;
                switch (getDirect()){
                    case 0:
                        shot = new Shot(getX() + 40,getY(),0);
                        break;
                    case 1:
                        shot = new Shot(getX(),getY() + 40,1);
                        break;
                    case 2:
                        shot = new Shot(getX() + 80,getY() + 40,2);
                        break;
                    case 3:
                        shot = new Shot(getX() + 40,getY() + 80,3);
                        break;
                }
                shots.add(shot);
                new Thread(shot).start();
            }
            //移动的进程
            switch (getDirect()){
                //up
                case 0:
                    for (int i = 0; i < 10; i++) {
                        if (this.noTouchTank()) {
                            moveUp();
                        }
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                //left
                case 1:
                    for (int i = 0; i < 10; i++) {
                        if (noTouchTank()) {
                            moveLeft();
                        }
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                //right
                case 2:
                    for (int i = 0; i < 10; i++) {
                        if (noTouchTank()) {
                            moveRight();
                        }
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                //down
                case 3:
                    for (int i = 0; i < 10; i++) {
                        if (noTouchTank()) {
                            moveDown();
                        }
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                }
            setDirect((int)(Math.random() * 4));

            //写并发程序一定要考虑线程什么时候退出
            //当敌方坦克生命周期为false时，就退出while死循环
            if(!(isLive)){
                break;
            }
        }
    }
}

//不适用，下面的是对某个对象进行操作，上面应该要对某个集合里的对象进行操作
//    //向我方坦克射击的方法
//    public void shotHeroTank(){
//        while (true){
//            try {
//                Thread.sleep(300);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            switch (getDirect()){
//                //创建向上的Shot对象
//                case 0:
//                    shot = new Shot(getX() + 40,getY(),0);
//                    break;
//                //创建向左的Shot对象
//                case 1:
//                    shot = new Shot(getX(),getY() + 40,1);
//                    break;
//                //创建向右的Shot对象
//                case 2:
//                    shot = new Shot(getX() + 80,getY() + 40,2);
//                    break;
//                //创建向下的Shot对象
//                case 3:
//                    shot = new Shot(getX() + 40,getY() + 80,3);
//                    break;
//            }
//            new Thread(shot).start();
//        }
        //创建shot对象，要根据当前Hero对象的位置和方向来创建Shot
//        switch (getDirect()){
//            //创建向上的Shot对象
//            case 0:
//                shot = new Shot(getX() + 40,getY(),0);
//                break;
//            //创建向左的Shot对象
//            case 1:
//                shot = new Shot(getX(),getY() + 40,1);
//                break;
//            //创建向右的Shot对象
//            case 2:
//                shot = new Shot(getX() + 80,getY() + 40,2);
//                break;
//            //创建向下的Shot对象
//            case 3:
//                shot = new Shot(getX() + 40,getY() + 80,3);
//                break;
//        }
//        new Thread(shot).start();
//    }

