package tankgame;

/**
 * 子弹线程类
 * 1.当发射一枚子弹后，就相当于启动一个进程
 * 2.Hero创建子弹的对象，当按下j键后，我们就启动一个射击的线程，让子弹不停地移动，形成射击效果
 * 3.需要让MyPanel面板不停重绘子弹，才能有动态效果
 * 4.当子弹移动到边界时，就销毁
 */

public class Shot implements Runnable{
    int x;//子弹的横坐标
    int y;//子弹的纵坐标
    int direct = 0;//子弹的方向
    int speed = 10;//子弹的速度
    boolean isLive = true;//子弹是否存活

    public Shot(int x, int y, int direct) {
        this.x = x;
        this.y = y;
        this.direct = direct;
    }


    @Override
    public void run() {
        while (isLive){
            //让子弹休眠一会，产生移动的停顿
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            switch (direct){
                //up
                case 0:
                    y -= speed;
                    break;
                //left
                case 1:
                    x -= speed;
                    break;
                //right
                case 2:
                    x += speed;
                    break;
                //down
                case 3:
                    y += speed;
                    break;
            }
            System.out.println("x = " + x + "y =" + y);
            //当子弹移动到边界时，就销毁
            //并给子弹生命周期的属性赋值为false
            if (!(x > 0 && x <= 1000 && y > 0 && y <= 750 && isLive)){
                isLive = false;
                System.out.println("子弹线程退出");
                break;
            }
        }
    }
}
