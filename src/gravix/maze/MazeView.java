package gravix.maze;

import javax.swing.JApplet;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class MazeView extends JApplet {

	private static final long serialVersionUID = 1L;

	@Override
	public void init() {
		try {
			SwingUtilities.invokeAndWait(() -> add(new JLabel("Hello World")));
		} catch (Exception e) {
			System.err.println("createGUI didn't complete successfully");
		}
	}
}
