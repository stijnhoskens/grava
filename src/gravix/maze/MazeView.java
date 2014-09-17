package gravix.maze;

import java.lang.reflect.InvocationTargetException;

import javax.swing.JApplet;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class MazeView extends JApplet {

	private static final long serialVersionUID = 1L;

	@Override
	public void init() {
		try {
			SwingUtilities.invokeAndWait(() -> add(new JLabel("Hello World")));
		} catch (InvocationTargetException | InterruptedException e) {
			e.printStackTrace();
		}
	}
}
