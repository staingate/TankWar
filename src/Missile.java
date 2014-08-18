import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.*;

/**
 * �ӵ��࣬��װ�ӵ������Ժͷ���
 * @author ��ѧ��
 * @version 1.0 2014-7-20
 */
public class Missile {

	int x, y;
	Tank.Direction dir;
	private boolean live = true;
	private TankClient tc;
	private boolean good;

	public static final int XSPEED = 10;
	public static final int YSPEED = 10;
	public static final int WIDTH = 10;
	public static final int HEIGHT = 10;

	/**
	 * �ӵ���Ĺ��췽��
	 * @param x �ӵ���x����
	 * @param y �ӵ���y����
	 * @param dir �ڹܵķ���
	 */
	public Missile(int x, int y, Tank.Direction dir) {
		this.x = x;
		this.y = y;
		this.dir = dir;
	}

	/**
	 * �ӵ���Ĺ��췽��
	 * @param x �ӵ���x����
	 * @param y �ӵ���y����
	 * @param good �ӵ��ĺû����������ķ���������ӵ�
	 * @param dir �ڹܵķ���
	 * @param tc TankClient����
	 */
	public Missile(int x, int y, boolean good, Tank.Direction dir, TankClient tc) {
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.tc = tc;
		this.good = good;
	}

	/**
	 * �ӵ���draw����
	 * @param g
	 */
	public void draw(Graphics g) {
		if (!live) {
			tc.missiles.remove(this);
			return;
		}
			
		Color c = g.getColor();
		if(!good) {
			g.setColor(Color.GREEN);
		} else {
			g.setColor(Color.BLACK);
		}
		
		g.fillOval(x, y, WIDTH, HEIGHT);
		g.setColor(c);

		move();
	}

	/**
	 * �ӵ����ƶ�����
	 */
	private void move() {
		// TODO Auto-generated method stub
		switch (dir) {
		case L:
			x -= XSPEED;
			break;

		case LU:
			x -= XSPEED;
			y -= YSPEED;
			break;

		case U:
			y -= YSPEED;
			break;

		case RU:
			x += XSPEED;
			y -= YSPEED;
			break;

		case R:
			x += XSPEED;
			break;

		case RD:
			x += XSPEED;
			y += YSPEED;
			break;
		case D:
			y += YSPEED;
			break;

		case LD:
			x -= XSPEED;
			y += YSPEED;
			break;

		default:
			break;
		}

		//���ӵ��߳��߽�����������ӵ�����
		if (x < 0 || y < 0 || x > TankClient.SCREEN_WIDTH
				|| y > TankClient.SCREEN_LENGTH) {
			live = false;
			
		}

	}

	/**
	 * �ж��ӵ��Ƿ���
	 * @return ����boolean���͵�live����
	 */
	public boolean isLive() {
		return live;
	}

	/**
	 * ��ײ��ⷽ��
	 * @return
	 */
	public Rectangle getRect() {
		return new Rectangle(x, y, WIDTH, HEIGHT);
	}

	/**
	 * �ӵ��Ļ��з���
	 * @param t ̹�˶���
	 * @return ���з���true�����򷵻�false
	 */
	public boolean hitTank(Tank t) {
		if (this.live && this.getRect().intersects(t.getRect()) && t.isLive() && this.good != t.isGood()) {
			if(t.isGood()){
				t.setLife(t.getLife() - 20);
				if(t.getLife() <= 0) {
					t.setLive(false);
				}
			}else {
				t.setLive(false);
			}
			
			this.live = false;
			Explode e = new Explode(x, y, tc);
			tc.explodes.add(e);
			return true;
		} else {
			return false;
		}

	}
	
	/**
	 * �ӵ��Ļ��з���
	 * @param t ̹�˶���
	 * @return ���з���true�����򷵻�false
	 */
	public boolean hitTanks(List<Tank> tanks) {
		for (int i = 0; i < tanks.size(); i++) {
			if (hitTank(tanks.get(i))) {
				return true;
			} 
		}
		return false;
	}

	/**
	 * �ж��ӵ��Ƿ���ǽ��ײ
	 * @param w ǽ
	 * @return ײ�Ϸ���true�����򷵻�false
	 */
	public boolean hitWall(Wall w) {
		if(this.live && this.getRect().intersects(w.getRect())) {
			this.live = false;
			return true;
		}
		return false;
	}
}
