package grava.maze;

public interface MazeListener {

	void mazeCreated(int width, int height, boolean withoutWalls);

	void wallAdded(Position position, Direction direction);

	void wallRemoved(Position position, Direction direction);

}
