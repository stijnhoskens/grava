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
		setBackground(Color.LIGHT_GRAY);
	}

	@Override
	public void start() {
		maze = MazeBuilder.square(5, MazeNode::new)
				.withListeners(new Listener()).matrix();
		repaint();
	}

	@Override
	public void paint(Graphics g) {
		painter.accept(g);
	}

	private Position toUpperLeftCorner(Position p) {
		int newX = (maze.height() - p.getX() - 1) * SQUARE_WIDTH;
		int newY = p.getY() * SQUARE_WIDTH;
		return new Position(newX, newY);
	}

	private void drawSquare(Position p, Graphics g) {
		Position q = toUpperLeftCorner(p);
		g.drawRect(q.getX(), q.getY(), SQUARE_WIDTH, SQUARE_WIDTH);
	}

	private void drawWall(Position p, Direction d, Graphics g) {
		
	}

	private class Listener implements MazeListener {

		@Override
		public void mazeCreated(int w, int h, boolean flag) {
			painter = g -> {
				g.setColor(Color.LIGHT_GRAY);
				IntStream.range(0, w).forEach(x -> {
					IntStream.range(0, h).forEach(y -> {
						drawSquare(new Position(x, y), g);
					});
				});
			};
			repaint();
		}

		@Override
		public void wallAdded(Position p, Direction d) {
			painter = g -> {

			};
			repaint();
		}

		@Override
		public void wallRemoved(Position p, Direction d) {
			painter = g -> {

			};
			repaint();
		}
	}

}
