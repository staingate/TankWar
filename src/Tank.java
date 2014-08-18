import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.*;

/**
 * ̹���࣬��װ̹�˵����Ժͷ���
 * @author ��ѧ��
 * @version 1.0 2014-7-20
 */
public class Tank {
	//�����ĸ�����������̹�˵ĺ����ٶȡ������ٶȡ���Ⱥ͸߶�
	public static final int XSPEED = 5;
	public static final int YSPEED = 5;
	public static final int WIDTH = 30;
	public static final int HEIGHT = 30;
	
	TankClient tc = null;
	private boolean good;
	private boolean isLive = true;
	private static Random r = new Random();
	private int step = r.nextInt(12) + 3;
	
	private BloodBar bb = new BloodBar();//�½�һ��Ѫ������

	private int tankX, tankY;//����̹�˵�λ��
	private int oldX, oldY;//����̹�˵���һ��λ�ã���̹����ײ������ʹ��
	private int life = 100;//����̹�˵�����ֵ
	
	/**
	 * ̹������ֵ��get����
	 * @return life int���͵�����ֵ
	 */
	public int getLife() {
		return life;
	}

	/**
	 * ̹������ֵ��set����
	 * @param life int���͵�̹������ֵ
	 */
	public void setLife(int life) {
		this.life = life;
	}

	/**
	 * �ж�̹���Ƿ����ҷ�̹�˵ķ���
	 * @return ����good��ֵ
	 */
	public boolean isGood() {
		return good;
	}

	/**
	 * �ж�̹���Ƿ���ķ���
	 * @return ����isLive��ֵ
	 */
	public boolean isLive() {
		return isLive;
	}

	/**
	 * isLive��set����
	 * @param isLive boolean���͵�ֵ
	 */
	public void setLive(boolean isLive) {
		this.isLive = isLive;
	}

	/**
	 * ����һ��ö������Direction������ָ��̹�˵ĳ���
	 * @author ��ѧ��
	 *
	 */
	enum Direction {
		L, LU, U, RU, R, RD, D, LD, STOP
	};

	private Direction dir = Direction.STOP;//����һ��Direction���ͱ���dir��ʾ̹�˵ĳ���
	private Direction gunDir = Tank.Direction.D;//����һ��Direction���ͱ���gunDir��ʾ̹���ڹܵĳ���
	private boolean bL = false, bU = false, bD = false, bR = false;

	/**
	 * Tank��Ĺ��췽��
	 * @param tankX ̹�˵�x����
	 * @param tankY ̹�˵�y����
	 * @param good ̹�˵ĺû�����̹��������һ��
	 */
	public Tank(int tankX, int tankY, boolean good) {
		this.tankX = tankX;
		this.tankY = tankY;
		this.oldX = tankX;
		this.oldY = tankY;
		this.good = good;
	}
	
	/**
	 * Tank��Ĺ��췽��
	 * @param tankX ̹�˵�x����
	 * @param tankY ̹�˵�y����
	 * @param good ̹�˵ĺû�����̹��������һ��
	 * @param tc TankClient����
	 */
	public Tank(int tankX, int tankY, boolean good, TankClient tc) {
		this(tankX, tankY, good);
		this.tc = tc;
	}

	/**
	 * ̹�˵�draw������������̹��
	 * @param g
	 */
	public void draw(Graphics g) {
		if(!isLive) {
			if(!good) {
				tc.tanks.remove(this);
			}
			return;
		}
		Color c = g.getColor();
		if(good) {
			g.setColor(Color.RED);
		}else {
			g.setColor(Color.BLUE);
		}
		g.fillOval(tankX, tankY, WIDTH, HEIGHT);
		g.setColor(c);
		
		if(good) {
			bb.draw(g);
		}
		
		//�ж��ڹܵĳ��򲢻����ڹ�
		switch (gunDir) {
		case L:
			g.drawLine(tankX + Tank.WIDTH/2, tankY + Tank.HEIGHT/2, tankX, tankY + Tank.HEIGHT/2);
			break;

		case LU:
			g.drawLine(tankX + Tank.WIDTH/2, tankY + Tank.HEIGHT/2, tankX, tankY);
			break;

		case U:
			g.drawLine(tankX + Tank.WIDTH/2, tankY + Tank.HEIGHT/2, tankX + Tank.WIDTH/2, tankY);
			break;

		case RU:
			g.drawLine(tankX + Tank.WIDTH/2, tankY + Tank.HEIGHT/2, tankX + Tank.WIDTH, tankY);
			break;

		case R:
			g.drawLine(tankX + Tank.WIDTH/2, tankY + Tank.HEIGHT/2, tankX + Tank.WIDTH, tankY + Tank.HEIGHT/2);
			break;

		case RD:
			g.drawLine(tankX + Tank.WIDTH/2, tankY + Tank.HEIGHT/2, tankX + Tank.WIDTH, tankY + Tank.HEIGHT);
			break;
		
		case D:
			g.drawLine(tankX + Tank.WIDTH/2, tankY + Tank.HEIGHT/2, tankX + Tank.WIDTH/2, tankY + Tank.HEIGHT);
			break;

		case LD:
			g.drawLine(tankX + Tank.WIDTH/2, tankY + Tank.HEIGHT/2, tankX, tankY + Tank.HEIGHT);
			break;

		default:
			break;
		}

		move();//����̹�˵��ƶ�����
	}

	/**
	 * ̹�˵��ƶ���������������̹�˵��ƶ�
	 */
	public void move() {
		//����ǰ̹�������Ϊ̹�˵���һ������
		this.oldX = tankX;
		this.oldY = tankY;
		
		//�ж�̹�˵ĳ����ƶ�
		switch (dir) {
		case L:
			tankX -= XSPEED;
			break;

		case LU:
			tankX -= XSPEED;
			tankY -= YSPEED;
			break;

		case U:
			tankY -= YSPEED;
			break;

		case RU:
			tankX += XSPEED;
			tankY -= YSPEED;
			break;

		case R:
			tankX += XSPEED;
			break;

		case RD:
			tankX += XSPEED;
			tankY += YSPEED;
			break;
		case D:
			tankY += YSPEED;
			break;

		case LD:
			tankX -= XSPEED;
			tankY += YSPEED;
			break;

		case STOP:

			break;

		default:
			break;
		}
		
		//���̹�˲��Ǿ�ֹ�ģ����ڹܷ������̹�˳���
		if(dir != Direction.STOP) {
			this.gunDir = this.dir;
		}
		
		//ͨ���ж�ʹ��̹�˲���Խ������߽�
		if(tankX < 0) tankX = 0;
		if(tankY < 30) tankY = 30;
		if(tankX + Tank.WIDTH > TankClient.SCREEN_WIDTH) tankX = TankClient.SCREEN_WIDTH - Tank.WIDTH;
		if(tankY + Tank.HEIGHT > TankClient.SCREEN_WIDTH) tankY = TankClient.SCREEN_LENGTH - Tank.HEIGHT;
		
		//�з�̹�˵�����ƶ�����
		if(!good) {
			Direction[] dirs = Direction.values();
			
			if(step == 0) {
				step = r.nextInt(12) + 3;
				int randomNumber = r.nextInt(dirs.length);
				dir = dirs[randomNumber];
			}
			step --;
			
			if(r.nextInt(40) > 37) {
				this.fire();
			}
			
			
			
		}
	}

	/**
	 * ���̼���������Ӧ����
	 * @param e �����¼�
	 */
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_F2 ://��F2ʹ�ҷ�̹������
			if(!this.isLive) {
				this.isLive = true;
				this.setLife(100);
			}
		case KeyEvent.VK_LEFT:
			bL = true;
			break;

		case KeyEvent.VK_UP:
			bU = true;
			break;

		case KeyEvent.VK_RIGHT:
			bR = true;
			break;

		case KeyEvent.VK_DOWN:
			bD = true;
			break;
		
		default:
			break;
		}
		locateDirection();
	}
	
	/**
	 * ���̼���������Ӧ����
	 * @param e �����¼�
	 */
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_LEFT:
			bL = false;
			break;

		case KeyEvent.VK_UP:
			bU = false;
			break;

		case KeyEvent.VK_RIGHT:
			bR = false;
			break;

		case KeyEvent.VK_DOWN:
			bD = false;
			break;
		
		case KeyEvent.VK_CONTROL://��Ctrl�������ӵ�
			fire();
			break;

		default:
			break;
		}
		locateDirection();
		
	}

	/**
	 * �ж�̹�˳���ķ���
	 */
	public void locateDirection() {
		if (bL && !bR && !bU && !bD)
			dir = Direction.L;
		else if (bL && !bR && bU && !bD)
			dir = Direction.LU;
		else if (!bL && !bR && bU && !bD)
			dir = Direction.U;
		else if (!bL && bR && bU && !bD)
			dir = Direction.RU;
		else if (!bL && bR && !bU && !bD)
			dir = Direction.R;
		else if (!bL && bR && !bU && bD)
			dir = Direction.RD;
		else if (bL && !bR && !bU && bD)
			dir = Direction.LD;
		else if (!bL && !bR && !bU && bD)
			dir = Direction.D;
		else if (!bL && !bR && !bU && !bD)
			dir = Direction.STOP;
	}

	/**
	 * ̹�˵Ŀ��𷽷������ô˷�������һ���ӵ�
	 * @return ����һ���ӵ�����
	 */
	public Missile fire() {
		if(!isLive()) {
			return null;
		}
		int x = tankX + Tank.WIDTH/2 - Missile.WIDTH/2;
		int y = tankY + Tank.HEIGHT/2 - Missile.HEIGHT/2;
		Missile m = new Missile(x, y, good, gunDir, this.tc);//����һ���ӵ�����
		tc.missiles.add(m);//���ӵ�������ӵ�missiles������
		return m;
	}
	
	/**
	 * ��ײ��ⷽ��
	 * @return
	 */
	public Rectangle getRect() {
		return new Rectangle(tankX, tankY, WIDTH, HEIGHT);
	}
	
	/**
	 * �ж��Ƿ�ײǽ�ķ���
	 * @param w ǽ
	 * @return ײ��ǽ����true�����򷵻�false
	 */
	public boolean collideWithWall(Wall w) {
		if(this.isLive && this.getRect().intersects(w.getRect())) {
			this.stay();
			return true;
		}
		return false;
	}
	
	/**
	 * ��̹�˷�����ײ�ǵ���Ӧ����������̹�˷�����һ������
	 */
	private void stay() {
		tankX = oldX;
		tankY = oldY;
	}
	
	/**
	 * �ж�̹���Ƿ�����ײ�ķ���
	 * @param tanks ̹�˼���
	 * @return ̹����ײ����true�����򷵻�false
	 */
	public boolean collidesWithTanks(List<Tank> tanks) {
		for(int i = 0; i < tanks.size(); i ++) {
			Tank t = tanks.get(i);
			if(this != t) {
				if(this.isLive && t.isLive && this.getRect().intersects(t.getRect())) {
					this.stay();
					t.stay();
					return true;
				}
			}
		}
		return false;
		
	}
	
	/**
	 * ˽���ڲ���BloodBarѪ����
	 * @author ��ѧ��
	 * @version 1.1 2014-7-20
	 */
	private class BloodBar {
		public void draw(Graphics g) {
			Color c = g.getColor();
			g.setColor(Color.RED);
			g.drawRect(tankX, tankY - 10, WIDTH, 10);
			int w = WIDTH * life/100;
			g.fillRect(tankX, tankY - 10, w, 10);
			g.setColor(c);
			
		}
	}
	
}
