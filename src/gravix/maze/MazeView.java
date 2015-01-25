package gravix.maze;

import grava.maze.Direction;
import grava.maze.MazeBuilder;
import grava.maze.MazeListener;
import grava.maze.MazeNode;
import grava.maze.Position;
import grava.maze.generator.RecursiveBacktracker;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.stream.IntStream;

import javax.swing.JApplet;
import javax.swing.event.MouseInputAdapter;

public class MazeView extends JApplet {

	private static final long serialVersionUID = 1L;

	private static final int SQUARE_WIDTH = 20;
	private Position offset = new Position(10, 10);

	private Set<Wall> walls = new HashSet<>();
	private List<Consumer<Graphics>> permPaintJobs = new ArrayList<>();
	private List<Consumer<Graphics>> tempPaintJobs = new ArrayList<>();

	private int mazeHeight;
	private Listener listener = new Listener();
	private ExecutorService executor = Executors.newCachedThreadPool();

	@Override
	public void init() {
		setSize(500, 500);
	}

	@Override
	public void start() {
		executor.execute(() -> MazeBuilder.square(50, MazeNode::new)
				.withListeners(listener)
				.build(/* new RecursiveBacktracker<>() */));
		addMouseListener(listener);
		addMouseMotionListener(listener);
	}

	private void clean() {
		addTempPaintJob(g -> {
			Dimension d = getSize();
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, d.width, d.height);
		});
	}

	@Override
	public synchronized void paint(Graphics g) {
		permPaintJobs.forEach(c -> c.accept(g));
		tempPaintJobs.forEach(c -> c.accept(g));
		tempPaintJobs.clear();
		walls.forEach(w -> w.draw(g));
	}

	private synchronized void addPermPaintJob(Consumer<Graphics> c) {
		permPaintJobs.add(c);
	}

	private synchronized void addTempPaintJob(Consumer<Graphics> c) {
		tempPaintJobs.add(c);
	}

	private synchronized void addWall(Position p, Direction d) {
		walls.add(toWall(p, d));
	}

	private synchronized void removeWall(Position p, Direction d) {
		Wall wall = toWall(p, d);
		walls.remove(wall);
		addTempPaintJob(g -> wall.remove(g));
	}

	private Position toUpperLeftCorner(Position p) {
		int newY = (mazeHeight - p.getY() - 1) * SQUARE_WIDTH + offset.getY();
		int newX = p.getX() * SQUARE_WIDTH + offset.getX();
		return new Position(newX, newY);
	}

	private Wall toWall(Position p, Direction d) {
		if (d.equals(Direction.RIGHT))
			p = new Position(p.getX() + 1, p.getY());
		else if (d.equals(Direction.DOWN))
			p = new Position(p.getX(), p.getY() - 1);
		Orientation o = d.isHorizontal() ? Orientation.DOWN : Orientation.RIGHT;
		return new Wall(p, o);
	}

	private void drawLine(Position p, Position q, Graphics g) {
		g.drawLine(p.getX(), p.getY(), q.getX(), q.getY());
	}

	private void drawDot(Position p, Graphics g) {
		drawLine(p, p, g);
	}

	private class Listener extends MouseInputAdapter implements MazeListener {

		@Override
		public void mazeCreated(int w, int h, boolean flag) {
			mazeHeight = h;
			drawOuterWalls(w, h);
			drawDots(w, h);
			if (!flag)
				drawAllWalls(w, h);
			repaint();
		}

		private void drawAllWalls(int w, int h) {
			IntStream.range(0, w).forEach(
					x -> IntStream.range(0, h).forEach(y -> {
						Position p = new Position(x, y);
						addWall(p, Direction.DOWN);
						addWall(p, Direction.RIGHT);
					}));
		}

		private void drawDots(int w, int h) {
			addPermPaintJob(g -> {
				LineStyle.WALL.set(g);
				IntStream.range(1, w).forEach(
						x -> IntStream.range(0, h - 1).forEach(
								y -> drawDot(toUpperLeftCorner(new Position(x,
										y)), g)));
			});
		}

		private void drawOuterWalls(int w, int h) {
			IntStream.range(0, w).forEach(x -> {
				addWall(new Position(x, 0), Direction.DOWN);
				addWall(new Position(x, h - 1), Direction.UP);
			});
			IntStream.range(0, h).forEach(y -> {
				addWall(new Position(0, y), Direction.LEFT);
				addWall(new Position(w - 1, y), Direction.RIGHT);
			});
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

		private Point previous;

		@Override
		public void mouseDragged(MouseEvent e) {
			if (previous != null) {
				Point p = e.getPoint();
				Position move = new Position(p.x - previous.x, p.y - previous.y);
				offset = offset.translate(move);
				previous = p;
			} else
				previous = e.getPoint();
			clean();
			repaint();
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			previous = null;
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

		private final Position position;
		private final Orientation orientation;

		Wall(Position p, Orientation o) {
			position = p;
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
			Position upperLeft = toUpperLeftCorner(position);
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
					+ ((position == null) ? 0 : position.hashCode());
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
			if (position == null) {
				if (other.position != null)
					return false;
			} else if (!position.equals(other.position))
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
