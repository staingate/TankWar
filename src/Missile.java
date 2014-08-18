import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.*;

/**
 * 子弹类，包装子弹的属性和方法
 * @author 罗学林
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
	 * 子弹类的构造方法
	 * @param x 子弹的x坐标
	 * @param y 子弹的y坐标
	 * @param dir 炮管的方向
	 */
	public Missile(int x, int y, Tank.Direction dir) {
		this.x = x;
		this.y = y;
		this.dir = dir;
	}

	/**
	 * 子弹类的构造方法
	 * @param x 子弹的x坐标
	 * @param y 子弹的y坐标
	 * @param good 子弹的好坏，即是由哪方发射出的子弹
	 * @param dir 炮管的方向
	 * @param tc TankClient对象
	 */
	public Missile(int x, int y, boolean good, Tank.Direction dir, TankClient tc) {
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.tc = tc;
		this.good = good;
	}

	/**
	 * 子弹的draw方法
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
	 * 子弹的移动方法
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

		//当子弹走出边界后则回收这个子弹对象
		if (x < 0 || y < 0 || x > TankClient.SCREEN_WIDTH
				|| y > TankClient.SCREEN_LENGTH) {
			live = false;
			
		}

	}

	/**
	 * 判断子弹是否存活
	 * @return 返回boolean类型的live变量
	 */
	public boolean isLive() {
		return live;
	}

	/**
	 * 碰撞检测方法
	 * @return
	 */
	public Rectangle getRect() {
		return new Rectangle(x, y, WIDTH, HEIGHT);
	}

	/**
	 * 子弹的击中方法
	 * @param t 坦克对象
	 * @return 击中返回true，否则返回false
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
	 * 子弹的击中方法
	 * @param t 坦克对象
	 * @return 击中返回true，否则返回false
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
	 * 判断子弹是否与墙相撞
	 * @param w 墙
	 * @return 撞上返回true，否则返回false
	 */
	public boolean hitWall(Wall w) {
		if(this.live && this.getRect().intersects(w.getRect())) {
			this.live = false;
			return true;
		}
		return false;
	}
}
