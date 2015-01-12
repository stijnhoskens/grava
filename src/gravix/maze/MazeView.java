package gravix.maze;

import grava.maze.Direction;
import grava.maze.Maze;
import grava.maze.MazeBuilder;
import grava.maze.MazeListener;
import grava.maze.MazeNode;
import grava.maze.Position;

import java.awt.Color;
import java.awt.Graphics;
import java.util.function.Consumer;
import java.util.stream.IntStream;

import javax.swing.JApplet;

public class MazeView extends JApplet {

	private static final long serialVersionUID = 1L;

	private static final int SQUARE_WIDTH = 20;

	private Maze<MazeNode> maze;
	private Consumer<Graphics> painter;

	@Override
	public void init() {
		setBackground(Color.WHITE);
	}

	@Override
	public void start() {
		maze = MazeBuilder.square(8, MazeNode::new)
				.withListeners(new Listener()).matrix();
		//maze.addWallAt(new Position(0, 0), Direction.UP);
	}

	@Override
	public void paint(Graphics g) {
		painter.accept(g);
	}

	private Position toUpperLeftCorner(Position p) {
		int newY = (maze.height() - p.getY() - 1) * SQUARE_WIDTH;
		int newX = p.getX() * SQUARE_WIDTH;
		return new Position(newX, newY);
	}

	private void drawSquare(Position p, Graphics g) {
		Position q = toUpperLeftCorner(p);
		g.drawRect(q.getX(), q.getY(), SQUARE_WIDTH, SQUARE_WIDTH);
	}

	private void drawWall(Position p, Direction d, Graphics g) {
		g.setColor(Color.BLACK);
		if (d.isHorizontal()) {
			int x = d.increment() ? p.getX() + 1 : p.getX();
			int y = p.getY();
			Position first = toUpperLeftCorner(new Position(x, y));
			Position second = toUpperLeftCorner(new Position(x, y - 1));
			drawLine(first, second, g);
		} else {
			int x = p.getX();
			int y = d.increment() ? p.getY() : p.getY() - 1;
			Position first = toUpperLeftCorner(new Position(x, y));
			Position second = toUpperLeftCorner(new Position(x + 1, y));
			drawLine(first, second, g);
		}
	}

	private void drawLine(Position p, Position q, Graphics g) {
		g.drawLine(p.getX(), p.getY(), q.getX(), q.getY());
	}

	private class Listener implements MazeListener {

		@Override
		public void mazeCreated(int w, int h, boolean flag) {
			painter = g -> {
				IntStream.range(0, w).forEach(x -> {
					IntStream.range(0, h).forEach(y -> {
						Position p = new Position(x, y);
						g.setColor(Color.LIGHT_GRAY);
						drawSquare(p, g);
						if (y == 0)
							drawWall(p, Direction.DOWN, g);
						if (y == h - 1)
							drawWall(p, Direction.UP, g);
						if (x == 0)
							drawWall(p, Direction.LEFT, g);
						if (x == w - 1)
							drawWall(p, Direction.RIGHT, g);
					});
				});
			};
		}

		@Override
		public void wallAdded(Position p, Direction d) {
			painter = g -> {
				drawWall(p, d, g);
				System.out.println("painting a wall");
			};
		}

		@Override
		public void wallRemoved(Position p, Direction d) {
			painter = g -> {

			};
		}
	}

}
