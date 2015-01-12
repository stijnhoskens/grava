package grava.maze;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public abstract class AbstractMaze<V extends Positioned> implements Maze<V> {

	private final Set<MazeListener> listeners = new HashSet<>();

	@Override
	public void addListener(MazeListener l) {
		listeners.add(l);
	}

	@Override
	public void removeListener(MazeListener l) {
		listeners.remove(l);
	}

	protected void informMazeListeners(Consumer<MazeListener> action) {
		listeners.forEach(action);
	}

	protected boolean exceedsDimensions(Position p) {
		return exceedsWidth(p) || exceedsHeight(p);
	}

	protected boolean exceedsWidth(Position p) {
		return p.getX() < 0 || p.getX() >= width();
	}

	protected boolean exceedsHeight(Position p) {
		return p.getY() < 0 || p.getY() >= height();
	}

}
