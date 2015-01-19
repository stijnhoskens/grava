package gravix.maze;

import grava.maze.Direction;
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
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.stream.IntStream;

import javax.swing.JApplet;

public class MazeView extends JApplet {

	private static final long serialVersionUID = 1L;

	private static final int SQUARE_WIDTH = 20;
	private static final Position OFFSET = new Position(10, 10);

	private Set<Wall> walls = new HashSet<>();
	private Consumer<Graphics> paintJobs = doNothing();

	private int mazeHeight;
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
			MazeBuilder.square(20, MazeNode::new).withListeners(listener)
					.build(new RecursiveBacktracker<>());
		});
	}

	@Override
	public synchronized void paint(Graphics g) {
		paintJobs.accept(g);
		paintJobs = doNothing();
		walls.forEach(w -> w.draw(g));
	}

	private synchronized void addPaintJob(Consumer<Graphics> c) {
		paintJobs.andThen(c);
	}

	private static Consumer<Graphics> doNothing() {
		return g -> {
			// DO NOTHING
		};
	}

	private Position toUpperLeftCorner(Position p) {
		int newY = (mazeHeight - p.getY() - 1) * SQUARE_WIDTH + OFFSET.getY();
		int newX = p.getX() * SQUARE_WIDTH + OFFSET.getX();
		return new Position(newX, newY);
	}

	private void addWall(Position p, Direction d) {
		walls.add(toWall(p, d));
	}

	private Wall toWall(Position p, Direction d) {
		if (d.equals(Direction.RIGHT))
			p = new Position(p.getX() - 1, p.getY());
		else if (d.equals(Direction.DOWN))
			p = new Position(p.getX(), p.getY() - 1);
		Orientation o = d.isHorizontal() ? Orientation.RIGHT : Orientation.DOWN;
		return new Wall(toUpperLeftCorner(p), o);
	}

	private void removeWall(Position p, Direction d) {
		Wall wall = toWall(p, d);
		walls.remove(wall);
		addPaintJob(g -> wall.remove(g));
	}

	private void drawLine(Position p, Position q, Graphics g) {
		g.drawLine(p.getX(), p.getY(), q.getX(), q.getY());
	}

	private void drawDot(Position p, Graphics g) {
		drawLine(p, p, g);
	}

	private class Listener implements MazeListener {

		@Override
		public void mazeCreated(int w, int h, boolean flag) {
			mazeHeight = h;
			addPaintJob(g -> {
				IntStream.range(0, w).forEach(x -> {
					IntStream.range(0, h).forEach(y -> {
						Position p = new Position(x, y);
						Position q = toUpperLeftCorner(p);
						drawDot(q, g);
						if (y == 0 || !flag) {
							addWall(p, Direction.DOWN);
							Position s = new Position(x + 1, y);
							q = toUpperLeftCorner(s);
							drawDot(q, g);
						}
						if (y == h - 1 || !flag)
							addWall(p, Direction.UP);
						if (x == 0 || !flag)
							addWall(p, Direction.LEFT);
						if (x == w - 1 || !flag) {
							addWall(p, Direction.RIGHT);
							Position s = new Position(x, y - 1);
							q = toUpperLeftCorner(s);
							drawDot(q, g);
							if (y == 0) {
								s = new Position(x + 1, y - 1);
								q = toUpperLeftCorner(s);
								drawDot(q, g);
							}
						}
					});
				});
			});
			repaint();
		}

		@Override
		public void wallAdded(Position p, Direction d) {
			addWall(p, d);
			repaint();
		}

		@Override
		public void wallRemoved(Position p, Direction d) {
			removeWall(p, d);
			repaint();
		}
	}

	enum Orientation {
		RIGHT {
			@Override
			Position other(Position p) {
				return new Position(p.getX() + SQUARE_WIDTH, p.getY());
			}
		},
		DOWN {
			@Override
			Position other(Position p) {
				return new Position(p.getX(), p.getY() + SQUARE_WIDTH);
			}
		};
		abstract Position other(Position p);
	};

	private class Wall {

		private final Position upperLeft;
		private final Orientation orientation;

		Wall(Position p, Orientation o) {
			upperLeft = p;
			orientation = o;
		}

		void draw(Graphics g) {
			draw(g, LineStyle.WALL);
		}

		void remove(Graphics g) {
			draw(g, LineStyle.NO_WALL);
		}

		private void draw(Graphics g, LineStyle s) {
			s.set(g);
			Position other = orientation.other(upperLeft);
			g.drawLine(upperLeft.getX(), upperLeft.getY(), other.getX(),
					other.getY());
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result
					+ ((orientation == null) ? 0 : orientation.hashCode());
			result = prime * result
					+ ((upperLeft == null) ? 0 : upperLeft.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Wall other = (Wall) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (orientation != other.orientation)
				return false;
			if (upperLeft == null) {
				if (other.upperLeft != null)
					return false;
			} else if (!upperLeft.equals(other.upperLeft))
				return false;
			return true;
		}

		private MazeView getOuterType() {
			return MazeView.this;
		}

	}

	private enum LineStyle {

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

		final static int STROKE_WIDTH = 5;

		final static Stroke STROKE = new BasicStroke(STROKE_WIDTH,
				BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER);

		abstract void set(Graphics g);

	}

}
