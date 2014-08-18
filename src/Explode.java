
import java.awt.*;

/**
 * ��ը��
 * @author ��ѧ��
 * @version 1.0 2014-7-20
 */
public class Explode {
	int x, y;
	private boolean live = true;
	private TankClient tc;
	int[] diameters = {4, 7, 12, 18, 26, 32, 20, 8, 4};
	int step = 0;
	
	/**
	 * ��ը�Ĺ��췽��
	 * @param x ��ը��x����
	 * @param y ��ը��y����
	 * @param tc TankClient����
	 */
	public Explode(int x, int y, TankClient tc) {
		this.x =x;
		this.y = y;
		this.tc = tc;
	}
	
	/**
	 * ��ը��draw����
	 * @param g
	 */
	public void draw(Graphics g) {
		if(!live) {
			tc.explodes.remove(this);
			return;
		}
		
		if(step == diameters.length) {
			live = false;
			step = 0;
			return;
		}
		
		Color c = g.getColor();
		g.setColor(Color.ORANGE);
		g.fillOval(x, y, diameters[step], diameters[step]);
		g.setColor(c);
		
		step ++;
		
	}

}
