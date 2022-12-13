package tankgame;

import java.io.*;
import java.util.Vector;

/**
 * 该类用于记录我方击毁的坦克数，计分板的功能
 * 还有和文件交互的功能，用于存档
 *
 */
public class Recorder {
    //定义静态变量
    private static int allEnemyTank = 0;
    private static BufferedWriter bw = null;
    private static String recordFile = "src/myRecord.txt";

    public static Vector<EnemyTank> getEnemyTanks() {
        return enemyTanks;
    }

    public static void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        Recorder.enemyTanks = enemyTanks;
    }

    private static Vector<EnemyTank> enemyTanks = null;

    public static int getAllEnemyTank() {
        return allEnemyTank;
    }

    public static void setAllEnemyTank(int allEnemyTank) {
        Recorder.allEnemyTank = allEnemyTank;
    }

    public static void addAllEnemyTank(){
        allEnemyTank++;
    }

    //保存文件的方法，当给程序退出时调用
    //方法升级，保存敌人坦克的坐标和方向
    public static void keep(){
        try {
            bw = new BufferedWriter(new FileWriter(recordFile));
            bw.write(allEnemyTank + "");
            bw.newLine();
            //遍历类中的集合属性，保存集合中对象的xy和方向值
            for (int i = 0; i < enemyTanks.size(); i++) {
                EnemyTank enemyTank = enemyTanks.get(i);
                if(enemyTank.isLive){
                    String record = enemyTank.getX() + " " + enemyTank.getY() + " " + enemyTank.getDirect();
                    bw.write(record);
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
