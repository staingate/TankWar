import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.*;

/**
 * 坦克类，包装坦克的属性和方法
 * @author 罗学林
 * @version 1.0 2014-7-20
 */
public class Tank {
	//定义四个常量，代表坦克的横向速度、纵向速度、宽度和高度
	public static final int XSPEED = 5;
	public static final int YSPEED = 5;
	public static final int WIDTH = 30;
	public static final int HEIGHT = 30;
	
	TankClient tc = null;
	private boolean good;
	private boolean isLive = true;
	private static Random r = new Random();
	private int step = r.nextInt(12) + 3;
	
	private BloodBar bb = new BloodBar();//新建一个血条对象

	private int tankX, tankY;//定义坦克的位置
	private int oldX, oldY;//定义坦克的上一个位置，在坦克相撞方法中使用
	private int life = 100;//定义坦克的生命值
	
	/**
	 * 坦克生命值的get方法
	 * @return life int类型的生命值
	 */
	public int getLife() {
		return life;
	}

	/**
	 * 坦克生命值的set方法
	 * @param life int类型的坦克生命值
	 */
	public void setLife(int life) {
		this.life = life;
	}

	/**
	 * 判断坦克是否是我方坦克的方法
	 * @return 返回good的值
	 */
	public boolean isGood() {
		return good;
	}

	/**
	 * 判断坦克是否存活的方法
	 * @return 返回isLive的值
	 */
	public boolean isLive() {
		return isLive;
	}

	/**
	 * isLive的set方法
	 * @param isLive boolean类型的值
	 */
	public void setLive(boolean isLive) {
		this.isLive = isLive;
	}

	/**
	 * 定义一个枚举类型Direction，用来指明坦克的朝向
	 * @author 罗学林
	 *
	 */
	enum Direction {
		L, LU, U, RU, R, RD, D, LD, STOP
	};

	private Direction dir = Direction.STOP;//创建一个Direction类型变量dir表示坦克的朝向
	private Direction gunDir = Tank.Direction.D;//创建一个Direction类型变量gunDir表示坦克炮管的朝向
	private boolean bL = false, bU = false, bD = false, bR = false;

	/**
	 * Tank类的构造方法
	 * @param tankX 坦克的x坐标
	 * @param tankY 坦克的y坐标
	 * @param good 坦克的好坏，即坦克属于哪一方
	 */
	public Tank(int tankX, int tankY, boolean good) {
		this.tankX = tankX;
		this.tankY = tankY;
		this.oldX = tankX;
		this.oldY = tankY;
		this.good = good;
	}
	
	/**
	 * Tank类的构造方法
	 * @param tankX 坦克的x坐标
	 * @param tankY 坦克的y坐标
	 * @param good 坦克的好坏，即坦克属于哪一方
	 * @param tc TankClient对象
	 */
	public Tank(int tankX, int tankY, boolean good, TankClient tc) {
		this(tankX, tankY, good);
		this.tc = tc;
	}

	/**
	 * 坦克的draw方法，用来画坦克
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
		
		//判断炮管的朝向并画出炮管
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

		move();//调用坦克的移动方法
	}

	/**
	 * 坦克的移动方法，用来控制坦克的移动
	 */
	public void move() {
		//将当前坦克坐标存为坦克的上一个坐标
		this.oldX = tankX;
		this.oldY = tankY;
		
		//判断坦克的朝向并移动
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
		
		//如果坦克不是静止的，则炮管方向等于坦克朝向
		if(dir != Direction.STOP) {
			this.gunDir = this.dir;
		}
		
		//通过判断使得坦克不能越过画面边界
		if(tankX < 0) tankX = 0;
		if(tankY < 30) tankY = 30;
		if(tankX + Tank.WIDTH > TankClient.SCREEN_WIDTH) tankX = TankClient.SCREEN_WIDTH - Tank.WIDTH;
		if(tankY + Tank.HEIGHT > TankClient.SCREEN_WIDTH) tankY = TankClient.SCREEN_LENGTH - Tank.HEIGHT;
		
		//敌方坦克的随机移动方法
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
	 * 键盘监听器的响应方法
	 * @param e 监听事件
	 */
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_F2 ://按F2使我方坦克重生
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
	 * 键盘监听器的响应方法
	 * @param e 监听事件
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
		
		case KeyEvent.VK_CONTROL://按Ctrl键发射子弹
			fire();
			break;

		default:
			break;
		}
		locateDirection();
		
	}

	/**
	 * 判断坦克朝向的方法
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
	 * 坦克的开火方法，调用此方法发射一颗子弹
	 * @return 返回一个子弹对象
	 */
	public Missile fire() {
		if(!isLive()) {
			return null;
		}
		int x = tankX + Tank.WIDTH/2 - Missile.WIDTH/2;
		int y = tankY + Tank.HEIGHT/2 - Missile.HEIGHT/2;
		Missile m = new Missile(x, y, good, gunDir, this.tc);//创建一个子弹对象
		tc.missiles.add(m);//将子弹对象添加到missiles集合内
		return m;
	}
	
	/**
	 * 碰撞检测方法
	 * @return
	 */
	public Rectangle getRect() {
		return new Rectangle(tankX, tankY, WIDTH, HEIGHT);
	}
	
	/**
	 * 判断是否撞墙的方法
	 * @param w 墙
	 * @return 撞上墙返回true，否则返回false
	 */
	public boolean collideWithWall(Wall w) {
		if(this.isLive && this.getRect().intersects(w.getRect())) {
			this.stay();
			return true;
		}
		return false;
	}
	
	/**
	 * 当坦克发生碰撞是的响应方法，即让坦克返回上一个坐标
	 */
	private void stay() {
		tankX = oldX;
		tankY = oldY;
	}
	
	/**
	 * 判断坦克是否互相碰撞的方法
	 * @param tanks 坦克集合
	 * @return 坦克相撞返回true，否则返回false
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
	 * 私有内部类BloodBar血条类
	 * @author 罗学林
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
