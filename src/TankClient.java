import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * 主类，用来协调其他类进行工作，并画出主界面
 * @author 罗学林
 * @version 1.0 2014-7-20
 */
public class TankClient extends Frame {

	public static final int SCREEN_WIDTH = 800;//定义游戏画面的宽度
	public static final int SCREEN_LENGTH = 600;//定义游戏画面的高度

	Tank myTank = new Tank(50, 50, true, this);//创建一个Tank类，新建一辆我方坦克并指定位置
	Wall w1 = new Wall(100, 200, 20, 150, this);//新建一个Wall类，新建一堵墙并制定墙出现的位置
	
	List<Explode> explodes = new ArrayList<Explode>();//创建一个List集合，用来存放每个爆炸对象
	List<Missile> missiles = new ArrayList<Missile>();//创建一个List集合，用来存放每个子弹对象
	List<Tank> tanks = new ArrayList<Tank>();//创建一个Lsit集合，用来存放每个坦克对象

	Image offScreenImage = null;//创建一个缓冲图片

	/**
	 * 画面的更新方法
	 * @param g 画笔，接收调用此方法的Graphics对象
	 */
	@Override
	public void update(Graphics g) {
		// TODO Auto-generated method stub
		if (offScreenImage == null) {
			offScreenImage = this.createImage(SCREEN_WIDTH, SCREEN_LENGTH);

		}
		Graphics gOffScreen = offScreenImage.getGraphics();
		Color c = gOffScreen.getColor();
		gOffScreen.setColor(Color.WHITE);
		gOffScreen.fillRect(0, 0, SCREEN_WIDTH, SCREEN_LENGTH);
		gOffScreen.setColor(c);
		paint(gOffScreen);
		g.drawImage(offScreenImage, 0, 0, null);
	}

	/**
	 * 本方法用来画出需要在界面上显示的各种物体
	 * @param g
	 */
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		g.drawString("Missiles conut:" + missiles.size(), 10, 50);
		g.drawString("Explode conut:" + explodes.size(), 10, 70);
		g.drawString("Tank conut:" + tanks.size(), 10, 90);
		g.drawString("Tank life:" + myTank.getLife(), 10, 110);
		
		//当敌方坦克全部死亡后，通过循环向tanks集合内添加10辆塔克
		if(tanks.size() <= 0) {
			for(int i = 0; i < 10; i ++) {
				tanks.add(new Tank(50 + 40 * (i + 1), 50, false, this));
			}
		}
		
		//通过for循环取出missiles集合内的每个子弹
		for(int i = 0; i < missiles.size(); i ++) {
			Missile m = missiles.get(i);
			m.hitTanks(tanks);//调用击中方法，判断是否击中tanks集合内的任何一辆坦克
			m.hitTank(myTank);//调用单个击中方法
			m.hitWall(w1);//调用撞墙方法，判断是否撞墙
			m.draw(g);//调用draw方法，重画整个画面
		}
		
		//通过for循环取出每个爆炸对象并展示
		for(int i = 0; i < explodes.size(); i ++) {
			Explode e = explodes.get(i);
			e.draw(g);
		}
		
		//通过for循环取出每辆坦克兵判断是否撞墙或与其他坦克相撞
		for (int i = 0; i < tanks.size(); i++) {
			Tank t = tanks.get(i);
			t.collideWithWall(w1);
			t.collidesWithTanks(tanks);
			t.draw(g);
		}
		
		
		myTank.draw(g);
		w1.draw(g);

	}

	/**
	 * 主方法，创建TankClient对象并用此对象调用lanch启动方法
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TankClient tc = new TankClient();
		tc.lanch();

	}

	/**
	 * 本方法用来显示整个个游戏画面
	 */
	public void lanch() {
		//启动是首先添加10辆地方坦克
		for(int i = 0; i < 10; i ++) {
			tanks.add(new Tank(50 + 40 * (i + 1), 50, false, this));
		}
		
		
		this.setSize(SCREEN_WIDTH, SCREEN_LENGTH);
		this.setResizable(false);
		this.setTitle("坦克大战");
		this.setBackground(Color.GREEN);

		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}

		});
		this.setLocationRelativeTo(null);
		this.addKeyListener(new keyBoardMonitor());
		this.setVisible(true);

		new Thread(new tankThread()).start();//启用线程创建TankThread对象
	}

	private class tankThread implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (true) {
				repaint();
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

	}

	/**
	 * 私有内部类，用来对键盘监听器进行响应
	 * @author 罗学林
	 * @param 1.0 2014-7-20
	 */
	private class keyBoardMonitor extends KeyAdapter {

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			myTank.keyReleased(e);//调用Tank类中的keyReleased方法来响应
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			myTank.keyPressed(e);//调用Tank类中的keyPressed方法来响应
		}

	}

}
