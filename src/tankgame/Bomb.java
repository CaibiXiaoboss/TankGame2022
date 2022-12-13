package tankgame;

/**
 * 爆炸效果
 */
public class Bomb {
    //坐标属性
    int x,y;
    //生命周期属性
    int  life = 9;
    //是否还存活
    boolean isLive = true;

    public Bomb(int x, int y) {
        this.x = x;
        this.y = y;
    }

    //生命递减方法
    public void lifeDown(){
        if (life > 0){
            life--;
        }else {
            isLive = false;
        }
    }
}
