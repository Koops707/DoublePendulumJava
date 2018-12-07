import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;


public class DoublePen {
	JFrame frame;
	DrawingPanel panel;
	int middlex = 400;						//frame.getWidth()/2;
	int middley = 100;						//frame.getHeight()/5;
	
	int m1 = 10;
	int m2 = 10;
	double a1 = Math.PI/3;
	double a2 = Math.PI/8;
	double a1_v = 0;
	double a2_v = 0;
	int r1 = 100;
	int r2 = 100;
	double gravConstant = 9;
	
	public static void main(String[] args) {
		new DoublePen().pubCode();
	}
	public void pubCode() {
		frame = new JFrame("Naisu");
		
		panel = new DrawingPanel();
		
		frame.getContentPane().add(BorderLayout.CENTER, panel);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000, 500);
		frame.setResizable(false);
		move();
	}
	
	class DrawingPanel extends JComponent{
		public void paintComponent(Graphics graphics) {
			Graphics2D g = (Graphics2D) graphics;
			
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g.setColor(Color.DARK_GRAY);
			
			double x1 = middlex + (r1 * Math.sin(a1));
			double y1 = middley + (r1 * Math.cos(a1));
			double x2 = x1 + (r2 * Math.sin(a2));
			double y2 = y1 + (r2 * Math.cos(a2));
			
			Line2D l1 = new Line2D.Double(middlex, middley, x1, y1);
			Line2D l2 = new Line2D.Double(x1, y1, x2, y2);
			g.setColor(Color.BLACK);
			Ellipse2D e1 = new Ellipse2D.Double(x1 - (m1/2), y1 - (m1/2), m1, m1);
			g.fill(e1);
			Ellipse2D e2 = new Ellipse2D.Double(x2 - (m2/2), y2 - (m2/2), m2, m2);
			g.fill(e2);
			
			g.draw(l1);
			g.draw(e1);
			g.draw(l2);
			g.draw(e2);
		}
	}
	public void move() {
//		double a1_num1 = (-(gravConstant) * (2 * m1 + m2) * Math.sin(a1)) - (m2 * gravConstant * Math.sin(a1 - (2*a2)));	
//		double a1_num2 = 2*Math.sin(a1 - a2)*m2*(Math.pow(a2_v, 2)*r2+Math.pow(a1_v, 2)*r1*Math.cos(a1 - a2));
//		double a1_dem = r1 * (2*m1 + m2 - m2*Math.cos(2*a1 - 2*a2));
		int delay = (1000/ 60) + 100; // just the fps for this
		//final Timer timer = new Timer(delay, null);
		
		double a1_num1;
		double a1_num2;
		double a1_num3;
		double a1_num4;
		double a1_dem;
		
		double a1_a;
		
		double a2_num1;
		double a2_num2;
		double a2_num3;
		double a2_num4;
		double a2_dem;
		
		double a2_a;
		
		while(true) {
			
			a1_num1 = -gravConstant * (2 * m1 * m2) * Math.sin(a1);
			a1_num2 = -m2 * gravConstant * Math.sin(a1 - 2 * a2);
			a1_num3 = -2*Math.sin(a1-a2)*m2;
			a1_num4 = a2_v*a2_v * r2 + a1_v*a1_v*r1*Math.cos(a1-a2);
			a1_dem = r1 * (2 * m1 + m2 - m2 * Math.cos(2*a1 - 2*a2));
			
			a1_a = (a1_num1 + a1_num2 + a1_num3 * a1_num4) /a1_dem;
			
			a2_num1 = 2*Math.sin(a1 - a2);
			a2_num2 = a1_v * a1_v * r1 * (m1+m2);
			a2_num3 = gravConstant * (m1+m2) * Math.cos(a1);
			a2_num4 = a2_v * a2_v * r2 * m2 * Math.cos(a1-a2);
			a2_dem = r2*( 2 *m1 + m2 - m2 * Math.cos(2*a1 - 2*a2));
			
			a2_a = (a2_num1*(a2_num2 + a2_num3 + a2_num4))/a2_dem;
			
			
			a1_v += a1_a;
			a2_v += a2_a;
			a1 += a1_v;
			a2 += a2_v;
			
			try {
				//timer.start();
				Thread.sleep(delay);
				frame.repaint();
				
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	

}

/*pseudo code
 * r1 = 100;
 * r2 = 100
 * m1 = 10
 * m2 = 10
 * 
 */


