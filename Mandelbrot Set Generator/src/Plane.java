import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.awt.geom.Point2D;

import javax.swing.JFrame;


public class Plane extends JFrame {
	
	private int startingSize = 300;
	private double zoom = 100.0;
	private long xOffset = 0;
	private long yOffset = 0;
	private int layer = 0;
	
	/**
	 * This is the current color scheme. 0 means blue, 1 red, 2 green.
	 */
	private int currColor = 0;
	
	/**
	 * Constructs a new Mandelbrot plane (300*300 pixels, from -1.5 to 1.5 initially).
	 */
	public Plane(){
		super();
		int xPos = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width/2;
		int yPos = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height/2;
		setSize(startingSize, startingSize);
		setLocation(xPos-(getWidth()/2),yPos-(getHeight()/2));
		setVisible(true);
		setBackground(Color.WHITE);
		addKeyListener(new zoomListener());
		repaint();
		
	}
	
	public class zoomListener implements KeyListener{

		@Override
		public void keyPressed(KeyEvent arg0) {
			int code = arg0.getKeyCode();
			int change = (int)(zoom/10.0);
			if (zoom/10>40){
				change = 40;
			}
			if (code==KeyEvent.VK_UP){
				zoom+=zoom*.1;
				xOffset+=xOffset*.1;
				yOffset+=yOffset*.1;
				layer++;
				//System.out.println("magnification: "+zoom);
			} else if (code==KeyEvent.VK_DOWN && zoom>10){
				zoom-=zoom*.1;
				xOffset-=xOffset*.1;
				yOffset-=yOffset*.1;
				layer--;
				///System.out.println("magnification: "+zoom/144);
			} else if (code==KeyEvent.VK_A || code==KeyEvent.VK_LEFT){
				xOffset-=change;
			} else if (code==KeyEvent.VK_D || code==KeyEvent.VK_RIGHT){
				xOffset+=change;
			} else if (code==KeyEvent.VK_W){
				yOffset-=change;
			} else if (code==KeyEvent.VK_S){
				yOffset+=change;
			}
			repaint();
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			int code = arg0.getKeyCode();
			if (code==KeyEvent.VK_SPACE){
				currColor++;
				if (currColor==3){
					currColor = 0;
				}
				repaint();	
			}
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			
		}
		
	}
	
	/**
	 * Paints the Mandelbrot set on the canvas.
	 */
	public void paint(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		
		int width = getWidth();
		int height = getHeight();
		int centerX = width/2;
		int centerY = height/2;
		
		for (int px = 0; px<width; px++){
			double x = (px-centerX+xOffset)/zoom;
			for (int py = 0; py<height; py++){
				double y = (centerY-py-yOffset)/zoom;
				Color clr = getIterationColor(getIterations(x, y));
				g2.setColor(clr);
				g2.drawLine(px, py, px, py);
			}
		}
	}
	
	/**
	 * Gets the color for the iteration when that coordinate escaped.
	 * @param iteration The iteration when the coordinate escaped.
	 * @return The proper color to paint that pixel. 
	 */
	private Color getIterationColor(int iteration){
		if (iteration==-1){
			return Color.black;
		}
		iteration = iteration%10;
		
		switch(currColor){
		case 0: return new Color(22*iteration, 22*iteration, 255);
		case 1: return new Color(3*iteration+220, 22*iteration, 22*iteration);
		case 2: return new Color(22*iteration, 5*iteration+200, 22*iteration);
		default: return Color.black;
		}
	}
	
	private int getIterations(double x, double y){
		//Point2D point = new Point2D.Double(x,y);
		
		double zx = 0.0;
		double zy = 0.0;
		double czx = 0.0;
		double czy = 0.0;
		int max = 100;
		if (layer>70){
			max = layer*5 + 100;
		}

		for (int i=1; i<max; i++){
			czx = x+zx;
			czy = y+zy;
			if ((czx*czx + czy*czy)>=4){
				return i;
			} else {
				zx = czx*czx - czy*czy;
				zy = 2*czx*czy;
			}
			
		}
		return -1;
	}
}
