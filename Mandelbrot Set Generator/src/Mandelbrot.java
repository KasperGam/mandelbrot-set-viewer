import javax.swing.JOptionPane;

public class Mandelbrot {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Plane frame = new Plane();
		frame.repaint();
		JOptionPane.showMessageDialog(frame, 
				"Use up and down arrows to zoom in and out.\n" +
				"Use left and right arrow keys or the a and\n" +
				"d keys (or the left and right arrow keys) to\n" +
				"move left and right. Use w and s to move up\n" +
				"and down. Press space to change color scheme.");
	}

}
