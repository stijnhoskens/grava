package grava.maze;

public class MazeNode implements Positioned {

	private Position pos;

	public MazeNode(Position position) {
		pos = position;
	}

	@Override
	public Position getPosition() {
		return pos;
	}

}
