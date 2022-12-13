package tankgame;

import sun.awt.image.ToolkitImage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

/**
 * 画板绘图类
 * 为什么遍历不用增强for循环直接取出对象?
 * 因为增强for在对集合进行增删的时候会抛异常
 */

//为了让Panel不停地重绘，需要把他设置成一个线程，在线程的run方法里写入循环的repaint方法
//让他并发地刷新画板
public class MyPanel extends JPanel implements KeyListener,Runnable{
    //定义一个自己的坦克对象和敌人的坦克集合(Vector类型，因为会有多线程)
    Hero hero = null;
    //定义一个敌人对象的集合，泛型使用EnemyTank
    Vector<EnemyTank> enemyTanks = new Vector<>();
    //定义一个存放Node节点的Vector集合，用于恢复敌人坦克的坐标和方向
    Vector<Node> nodes = new Vector<>();
    //定义爆炸效果集合
    //当子弹击中坦克时，就加入Bomb对象到集合中
    Vector<Bomb> bombs = new Vector<>();
    //定义敌人坦克个数
    int enemyTankSize = 3;
//    Shot shot = null;

    //定义三张炸弹图片，用于显示爆炸效果
    Image image1 = null;
    Image image2 = null;
    Image image3 = null;

    //构造器默认初始化一个Hero对象
    //构造器创建敌我坦克对象
    public MyPanel(String key){
        //nodes集合存放文件中的node对象
        nodes = Recorder.getNodesInEnemyTankRec();
        //初始化自己的坦克
        hero = new Hero(500,100);
        //把敌方坦克集合set进Recorder类中
        Recorder.setEnemyTanks(enemyTanks);

        switch (key){
            //开新游戏
            case "1":
                for (int i = 0; i < enemyTankSize; i++) {
                    EnemyTank enemyTank = new EnemyTank(100 * (i + 1), 0);
                    enemyTank.setEnemyTanks(enemyTanks);
                    new Thread(enemyTank).start();
                    enemyTank.setDirect(3);
                    enemyTanks.add(enemyTank);
                }
                break;
            //继续游戏
            case "2":
                for(int i = 0;i < nodes.size();i++){
                    Node node = nodes.get(i);
                    EnemyTank enemyTank = new EnemyTank(node.getX(),node.getY());
                    enemyTank.setEnemyTanks(enemyTanks);
                    new Thread(enemyTank).start();
                    enemyTank.setDirect(node.getDirect());
                    enemyTanks.add(enemyTank);
                }
                break;
            default:
                System.out.println("你的输入有误");
        }


        //开始新游戏的写法
        //初始化敌人的坦克，循环添加地方坦克对象进集合中
//        for (int i = 0; i < enemyTankSize; i++) {
            //创建一个敌方坦克对象
//            EnemyTank enemyTank = new EnemyTank(100 * (i + 1), 0);
            //把集合set进去enemyTank类中
//            enemyTank.setEnemyTanks(enemyTanks);
            //启动坦克线程
//            new Thread(enemyTank).start();
            //初始化方向
//            enemyTank.setDirect((int)(Math.random() * 4));
//            enemyTank.setDirect(3);

            //已经在敌人坦克的类里内置
//            //初始化敌人的子弹对象
//            Shot shot = new Shot(enemyTank.getX() + 40,enemyTank.getY() + 80,enemyTank.getDirect());
//            //子弹对象加入集合中
//            enemyTank.shots.add(shot);
//            //把shot对象给Thread代理并启动
//            new Thread(shot).start();

            //把坦克对象加入集合中
//            enemyTanks.add(enemyTank);
//        }


//        for(int i = 0;i < enemyTankSize;i++){
//            EnemyTank enemyTank = enemyTanks.get(i);
//            enemyTank.setEnemyTanks(enemyTanks);
//        }


        //初始化爆炸图片的对象，用于爆炸特效
        image1 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_1.gif"));
        image2 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_2.gif"));
        image3 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_3.gif"));
    }

    //编写一个我方击毁的坦克信息,参数g为画笔一定要传入，凡是绘图类的方法一定要传画笔
    public void showInfo(Graphics g){
        g.setColor(Color.BLACK);
        Font font = new Font("宋体",Font.BOLD,20);
        g.setFont(font);
        //设置好画笔的颜色和字体的格式之后，用画笔写字
        g.drawString("您累计击毁敌方坦克:",1010,30);
        drawTank(1010,50,g,0,0);
        //上一个画笔颜色是坦克的颜色，所以要重置一下
        g.setColor(Color.BLACK);
        g.drawString(": " + Recorder.getAllEnemyTank() + "辆",1100,100);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        //填充背景区域
        g.fillRect(0,0,1000,750);
        showInfo(g);

        //画自己坦克的方法
        if (hero.isLive) {
            drawTank(hero.getX(),hero.getY(),g,hero.getDirect(),1);
        }

//        画我方子弹的方法,判断子弹是否存活
//        if(hero.shot != null && hero.shot.isLive == true){
//            g.fillOval(hero.shot.x - 4 ,hero.shot.y - 4,10,10);
//        }
        for (int i = 0; i < hero.shots.size(); i++) {
            Shot shot = hero.shots.get(i);
            if(shot != null && shot.isLive){
                g.fillOval(shot.x - 4 ,shot.y - 4,10,10);
            }else {//如果shot的不是存活状态，就从集合中拿掉
                hero.shots.remove(i);
            }
        }



        //画敌方坦克，和敌方子弹，还要判断坦克是否存活，不存活就不绘制
        for (int i = 0 ;i < enemyTanks.size();i++) {
            EnemyTank enemyTank = enemyTanks.get(i);
            //判断敌人坦克的是否存活，存活才能进入drawTank方法画出来

            if(enemyTank.isLive){
                drawTank(enemyTank.getX(), enemyTank.getY(), g, enemyTank.getDirect(), 0);
                //遍历shot取出shot对象
                for (int j = 0; j < enemyTank.shots.size(); j++) {
                    Shot shot = enemyTank.shots.get(j);
                    if (shot.isLive) {
                        g.fillOval(shot.x - 4, shot.y - 4, 10, 10);
                    } else {
                        enemyTank.shots.remove(shot);
                    }
                }
            }/*else {
                enemyTanks.remove(enemyTank);
            }*/
        }


        //如果bombs中有对象，就画出
        for (int i = 0; i < bombs.size(); i++) {
            Bomb bomb = bombs.get(i);
            if(bomb.life > 6){
                g.drawImage(image1,bomb.x,bomb.y,80,80,this);
            }else if(bomb.life > 3){
                g.drawImage(image2,bomb.x,bomb.y,80,80,this);
            }else {
                g.drawImage(image3,bomb.x,bomb.y,80,80,this);
            }
            //让爆炸效果的生命周期减少
            bomb.lifeDown();
            //如果bomb的生命周期为0，就从集合中移除对象
            if(bomb.life == 0){
                bombs.remove(bomb);
            }
        }
    }
    //封装画坦克的方法
    //Graphics g 是一个画笔，必不可少

    /**
     *
     * @param x 坦克的横坐标
     * @param y 坦克的纵坐标
     * @param g 画笔
     * @param direction 上下左右方向
     * @param type 坦克类型(区分敌我)
     */
    //绘制坦克的方法
    public void drawTank(int x,int y,Graphics g,int direction,int type) {
        //根据不同类型的坦克，设置不同的颜色
        switch (type){
            case 0://敌方的坦克
                g.setColor(Color.cyan);
                break;
            case 1://我们的坦克
                g.setColor(Color.yellow);
                break;
        }

        //根据方向调整坦克
        //1.绘制不同方向的坦克
        switch (direction){
            //向上,竖向的轮子
            case 0:
                g.fill3DRect(x,y,20,80,false);//坦克左边的轮子
                g.fill3DRect(x + 20,y + 20,40,40,false);//坦克中间的舱
                g.fill3DRect(x + 60,y,20,80,false);//坦克右边的轮子
                g.fillOval(x + 20,y + 20,40,40);//坦克中间的炮台
                g.fill3DRect(x + 40,y,2,40,false);//炮
                break;
            //向左,横向的轮子
            case 1:
                g.fill3DRect(x,y,80,20,false);//坦克左边的轮子
                g.fill3DRect(x + 20,y + 20,40,40,false);//坦克中间的舱
                g.fill3DRect(x ,y + 60,80,20,false);//坦克右边的轮子
                g.fillOval(x + 20,y + 20,40,40);//坦克中间的炮台
                g.fill3DRect(x,y + 40,40,2,false);//炮
                break;
            //向右
            case 2:
                g.fill3DRect(x,y,80,20,false);//坦克左边的轮子
                g.fill3DRect(x + 20,y + 20,40,40,false);//坦克中间的舱
                g.fill3DRect(x ,y + 60,80,20,false);//坦克右边的轮子
                g.fillOval(x + 20,y + 20,40,40);//坦克中间的炮台
                g.fill3DRect(x + 40,y + 40,40,2,false);//炮
                break;
            //向下
            case 3:
                g.fill3DRect(x,y,20,80,false);//坦克左边的轮子
                g.fill3DRect(x + 20,y + 20,40,40,false);//坦克中间的舱
                g.fill3DRect(x + 60,y,20,80,false);//坦克右边的轮子
                g.fillOval(x + 20,y + 20,40,40);//坦克中间的炮台
                g.fill3DRect(x + 40,y + 40,2,40,false);//炮
                break;
//            default:
//                System.out.println("暂时不处理");
        }

    }

    //击中坦克方法
    //编写方法，判断子弹是否击中坦克
    public void hitTank(Shot shot,EnemyTank enemyTank){
        //判断子弹的横纵坐标是否和敌方坦克重合
        if(shot.x > enemyTank.getX() && shot.x < enemyTank.getX() + 80
        && shot.y > enemyTank.getY() && shot.y < enemyTank.getY() + 80){
            shot.isLive = false;
            enemyTank.setLive(false);
            enemyTanks.remove(enemyTank);
            //生命设置false后，new一个Bomb加入到Bombs中
            bombs.add(new Bomb(enemyTank.getX(), enemyTank.getY()));
            Recorder.addAllEnemyTank();
        }
        //判断子弹的横纵坐标是否和我方坦克重合
    }
    //击中己方坦克，方法重写
    public void hitTank(Shot shot,Hero hero){
        if(shot.x > hero.getX() && shot.x < hero.getX() + 80
        && shot.y > hero.getY() && shot.y < hero.getY() + 80){
            shot.isLive = false;
            hero.setLive(false);
//            hero = null;
            bombs.add(new Bomb(hero.getX(),hero.getY()));
        }

    }


    //击中敌方坦克的方法(关于子弹)
    public void hitEnemyTank() {
        //遍历hero类中子弹集合中的子弹对象是否和敌方坦克集合中的敌方坦克对象重合
        for (int i = 0; i < hero.shots.size(); i++) {
            Shot shot = hero.shots.get(i);
            if (shot != null && shot.isLive) {
                for (int j = 0; j < enemyTanks.size(); j++) {
                    EnemyTank enemyTank = enemyTanks.get(j);
                    hitTank(shot, enemyTank);
                }
            }
        }
    }
    //修正：hitTank加一个判断动作，只有在hero的对象是存活状态，和子弹也是存活状态的时候，才会使用hitTank方法
    //如果不加这个判断，那么就会一直执行hitTank方法，从而造成多次爆炸，即使hero的isLive已经为false
    public void hitHero(){
        for (int i = 0; i < enemyTanks.size(); i++) {
            EnemyTank enemyTank = enemyTanks.get(i);
            for (int j = 0; j < enemyTank.shots.size(); j++) {
                Shot shot = enemyTank.shots.get(j);
                if (hero.isLive && shot.isLive) {
                    hitTank(shot,hero);
                }
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    //监听键盘的wasd
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W){
            hero.setDirect(0);
            hero.moveUp();
        }else if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S){
            hero.setDirect(3);
            hero.moveDown();
        }else if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A){
            hero.setDirect(1);
            hero.moveLeft();
        }else if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D){
            hero.setDirect(2);
            hero.moveRight();
        }else {
            System.out.println("你的输入有误，请输入wasd或者↑↓←→");
        }
        //如果用户按下了J，就发射子弹
        if(e.getKeyCode() == KeyEvent.VK_J){
                hero.shotEnemyTank();
        }
        this.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void run() {
        while (true){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //调用判断击中敌方坦克的方法
            hitEnemyTank();
            hitHero();
            this.repaint();
        }
    }
}
