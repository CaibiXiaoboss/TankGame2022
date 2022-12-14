package tankgame;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Scanner;

/**
 * 运行主程序
 */

public class MyTankGame01 extends JFrame {
    MyPanel mp = null;
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        new MyTankGame01();
    }
    public MyTankGame01(){

        System.out.println("请输入选择 1：新游戏 2：继续上局游戏");
        String key = scanner.next();

        mp = new MyPanel(key);
        this.add(mp);//add一个画板区域
        new Thread(mp).start();//启动mp的线程，不断重绘画板，让子弹动起来
        this.setSize(1300,750);//面板大小
        this.addKeyListener(mp);//让JFrame监听mp的键盘事件
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//关闭按钮退出程序
        this.setVisible(true);//打开界面

        //监听关闭窗口的指令
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Recorder.keep();
                System.exit(0);
            }
        });
    }
}

