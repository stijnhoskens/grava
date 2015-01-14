package gravix.maze;

import grava.maze.Direction;
import grava.maze.Maze;
import grava.maze.MazeBuilder;
import grava.maze.MazeListener;
import grava.maze.MazeNode;
import grava.maze.Position;
import grava.maze.generator.RecursiveBacktracker;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.stream.IntStream;

import javax.swing.JApplet;

public class MazeView extends JApplet {

	private static final long serialVersionUID = 1L;

	private static final int SQUARE_WIDTH = 20;
	private static final Position OFFSET = new Position(10, 10);

	private Consumer<Graphics> painter = defaultPainter();

	private Maze<MazeNode> maze;
	private Listener listener = new Listener();
	private ExecutorService executor = Executors.newCachedThreadPool();

	@Override
	public void init() {
		setBackground(Color.WHITE);
		setSize(500, 500);
	}

	@Override
	public void start() {
		executor.execute(() -> {
			maze = MazeBuilder.square(20, MazeNode::new)
					.withListeners(listener)
					.build(new RecursiveBacktracker<>());
		});
	}

	@Override
	public synchronized void paint(Graphics g) {
		painter.accept(g);
	}

	private synchronized void addPaintJob(Consumer<Graphics> c) {
		painter = painter.andThen(c);
	}

	public Consumer<Graphics> defaultPainter() {
		return g -> {
			// DO NOTHING
		};
	}

	private Position toUpperLeftCorner(Position p) {
		int newY = (maze.height() - p.getY() - 1) * SQUARE_WIDTH
				+ OFFSET.getY();
		int newX = p.getX() * SQUARE_WIDTH + OFFSET.getX();
		return new Position(newX, newY);
	}

	private void drawWall(Position p, Direction d, Graphics g) {
		drawLine(p, d, g, Style.WALL);
	}

	private void removeWall(Position p, Direction d, Graphics g) {
		drawLine(p, d, g, Style.NO_WALL);
	}

	private void drawLine(Position p, Direction d, Graphics g, Style s) {
		s.set(g);
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
			addPaintJob(g -> {
				IntStream.range(0, w).forEach(x -> {
					IntStream.range(0, h).forEach(y -> {
						Position p = new Position(x, y);
						if (y == 0 || !flag)
							drawWall(p, Direction.DOWN, g);
						if (y == h - 1 || !flag)
							drawWall(p, Direction.UP, g);
						if (x == 0 || !flag)
							drawWall(p, Direction.LEFT, g);
						if (x == w - 1 || !flag)
							drawWall(p, Direction.RIGHT, g);
					});
				});
			});
			repaint();
		}

		@Override
		public void wallAdded(Position p, Direction d) {
			addPaintJob(g -> {
				drawWall(p, d, g);
			});
			repaint();
		}

		@Override
		public void wallRemoved(Position p, Direction d) {
			addPaintJob(g -> {
				removeWall(p, d, g);
			});
			repaint();
		}
	}

	private enum Style {

		WALL {
			private final Color color = Color.BLACK;

			@Override
			void set(Graphics g) {
				g.setColor(color);
				((Graphics2D) g).setStroke(STROKE);
			}
		},
		NO_WALL {
			private final Color color = Color.WHITE;

			@Override
			void set(Graphics g) {
				g.setColor(color);
				((Graphics2D) g).setStroke(STROKE);
			}
		};

		private final static Stroke STROKE = new BasicStroke(1,
				BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND);

		abstract void set(Graphics g);

	}

}
