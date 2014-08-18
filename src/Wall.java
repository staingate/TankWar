
import java.awt.*;

/**
 * ǽ�࣬��װǽ�����Ժͷ���
 * @author ��ѧ��
 * @version 1.0 2014-7-20
 */
public class Wall {
	int x, y, w, h;
	TankClient tc;
	
	/**
	 * ǽ�Ĺ��췽��
	 * @param x ǽ��x����
	 * @param y ǽ��y����
	 * @param w ǽ�Ŀ��
	 * @param h ǽ�ĸ߶�
	 * @param tc TankClient����
	 */
	public Wall(int x, int y, int w, int h, TankClient tc) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.tc = tc;
	}
	
	/**
	 * ǽ��draw����
	 * @param g
	 */
	public void draw(Graphics g) {
		g.fillRect(x, y, w, h);
	}
	
	/**
	 * ��ײ��ⷽ��
	 * @return
	 */
	public Rectangle getRect() {
		return new Rectangle(x, y, w, h);
	}
}
