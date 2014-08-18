import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * ���࣬����Э����������й�����������������
 * @author ��ѧ��
 * @version 1.0 2014-7-20
 */
public class TankClient extends Frame {

	public static final int SCREEN_WIDTH = 800;//������Ϸ����Ŀ��
	public static final int SCREEN_LENGTH = 600;//������Ϸ����ĸ߶�

	Tank myTank = new Tank(50, 50, true, this);//����һ��Tank�࣬�½�һ���ҷ�̹�˲�ָ��λ��
	Wall w1 = new Wall(100, 200, 20, 150, this);//�½�һ��Wall�࣬�½�һ��ǽ���ƶ�ǽ���ֵ�λ��
	
	List<Explode> explodes = new ArrayList<Explode>();//����һ��List���ϣ��������ÿ����ը����
	List<Missile> missiles = new ArrayList<Missile>();//����һ��List���ϣ��������ÿ���ӵ�����
	List<Tank> tanks = new ArrayList<Tank>();//����һ��Lsit���ϣ��������ÿ��̹�˶���

	Image offScreenImage = null;//����һ������ͼƬ

	/**
	 * ����ĸ��·���
	 * @param g ���ʣ����յ��ô˷�����Graphics����
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
	 * ����������������Ҫ�ڽ�������ʾ�ĸ�������
	 * @param g
	 */
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		g.drawString("Missiles conut:" + missiles.size(), 10, 50);
		g.drawString("Explode conut:" + explodes.size(), 10, 70);
		g.drawString("Tank conut:" + tanks.size(), 10, 90);
		g.drawString("Tank life:" + myTank.getLife(), 10, 110);
		
		//���з�̹��ȫ��������ͨ��ѭ����tanks���������10������
		if(tanks.size() <= 0) {
			for(int i = 0; i < 10; i ++) {
				tanks.add(new Tank(50 + 40 * (i + 1), 50, false, this));
			}
		}
		
		//ͨ��forѭ��ȡ��missiles�����ڵ�ÿ���ӵ�
		for(int i = 0; i < missiles.size(); i ++) {
			Missile m = missiles.get(i);
			m.hitTanks(tanks);//���û��з������ж��Ƿ����tanks�����ڵ��κ�һ��̹��
			m.hitTank(myTank);//���õ������з���
			m.hitWall(w1);//����ײǽ�������ж��Ƿ�ײǽ
			m.draw(g);//����draw�������ػ���������
		}
		
		//ͨ��forѭ��ȡ��ÿ����ը����չʾ
		for(int i = 0; i < explodes.size(); i ++) {
			Explode e = explodes.get(i);
			e.draw(g);
		}
		
		//ͨ��forѭ��ȡ��ÿ��̹�˱��ж��Ƿ�ײǽ��������̹����ײ
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
	 * ������������TankClient�����ô˶������lanch��������
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TankClient tc = new TankClient();
		tc.lanch();

	}

	/**
	 * ������������ʾ��������Ϸ����
	 */
	public void lanch() {
		//�������������10���ط�̹��
		for(int i = 0; i < 10; i ++) {
			tanks.add(new Tank(50 + 40 * (i + 1), 50, false, this));
		}
		
		
		this.setSize(SCREEN_WIDTH, SCREEN_LENGTH);
		this.setResizable(false);
		this.setTitle("̹�˴�ս");
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

		new Thread(new tankThread()).start();//�����̴߳���TankThread����
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
	 * ˽���ڲ��࣬�����Լ��̼�����������Ӧ
	 * @author ��ѧ��
	 * @param 1.0 2014-7-20
	 */
	private class keyBoardMonitor extends KeyAdapter {

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			myTank.keyReleased(e);//����Tank���е�keyReleased��������Ӧ
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			myTank.keyPressed(e);//����Tank���е�keyPressed��������Ӧ
		}

	}

}
