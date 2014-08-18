
import java.awt.*;

/**
 * 爆炸类
 * @author 罗学林
 * @version 1.0 2014-7-20
 */
public class Explode {
	int x, y;
	private boolean live = true;
	private TankClient tc;
	int[] diameters = {4, 7, 12, 18, 26, 32, 20, 8, 4};
	int step = 0;
	
	/**
	 * 爆炸的构造方法
	 * @param x 爆炸的x坐标
	 * @param y 爆炸的y坐标
	 * @param tc TankClient对象
	 */
	public Explode(int x, int y, TankClient tc) {
		this.x =x;
		this.y = y;
		this.tc = tc;
	}
	
	/**
	 * 爆炸的draw方法
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
