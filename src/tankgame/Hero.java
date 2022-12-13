package tankgame;

import java.util.Vector;

/**
 * 己方坦克类
 */
public class Hero extends Tank{
    //创建射击线程,定义一个Shot对象
    Shot shot = null;
    Vector<Shot> shots = new Vector<>();
    boolean isLive = true;
    public Hero(int x, int y) {
        super(x, y);
    }

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
    }

    //向敌人坦克射击的方法
    public void shotEnemyTank(){

        if(shots.size() >= 5){
            return;
        }
        //判断子弹个数
        //if (shots.size() == 0 || shots.size() <= 5) {

            //创建shot对象，要根据当前Hero对象的位置和方向来创建Shot
            //判断条件：如果shot为空或者shot对象的不存活，就进入switch语句
//        if (shot == null || (!shot.isLive)) {
            switch (getDirect()){
                //创建向上的Shot对象
                case 0:
                    shot = new Shot(getX() + 40,getY(),0);
//                    shots.add(new Shot(getX() + 40,getY(),0));
                    break;
                //创建向左的Shot对象
                case 1:
                    shot = new Shot(getX(),getY() + 40,1);
//                    shots.add(new Shot(getX(),getY() + 40,1));
                    break;
                //创建向右的Shot对象
                case 2:
                    shot = new Shot(getX() + 80,getY() + 40,2);
//                    shots.add(new Shot(getX() + 80,getY() + 40,2));
                    break;
                //创建向下的Shot对象
                case 3:
                    shot = new Shot(getX() + 40,getY() + 80,3);
//                    shots.add(new Shot(getX() + 40,getY() + 80,3));
                    break;
            }
            shots.add(shot);
            new Thread(shot).start();
        }
    }
//}
