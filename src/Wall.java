
import java.awt.*;

/**
 * 墙类，包装墙的属性和方法
 * @author 罗学林
 * @version 1.0 2014-7-20
 */
public class Wall {
	int x, y, w, h;
	TankClient tc;
	
	/**
	 * 墙的构造方法
	 * @param x 墙的x坐标
	 * @param y 墙的y坐标
	 * @param w 墙的宽度
	 * @param h 墙的高度
	 * @param tc TankClient对象
	 */
	public Wall(int x, int y, int w, int h, TankClient tc) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.tc = tc;
	}
	
	/**
	 * 墙的draw方法
	 * @param g
	 */
	public void draw(Graphics g) {
		g.fillRect(x, y, w, h);
	}
	
	/**
	 * 碰撞检测方法
	 * @return
	 */
	public Rectangle getRect() {
		return new Rectangle(x, y, w, h);
	}
}
