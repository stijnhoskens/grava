package grava.maze;

import grava.edge.Edge;
import grava.util.CollectionUtils;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class LinkedMaze<V extends Positioned> implements Maze<V> {

	private final Map<Position, LinkedWrapper<V>> map = new HashMap<>();

	public LinkedMaze(int width, int height, Iterable<V> vertices) {
		
	}

	private static class Passage<V> {
		private boolean isOpen;
		private final V destination;

		Passage(V destination) {
			this(destination, true);
		}

		Passage(V destination, boolean isOpen) {
			this.destination = destination;
			this.isOpen = isOpen;
		}

		boolean isOpen() {
			return isOpen;
		}

		V destination() {
			return destination;
		}
	}

	private static class LinkedWrapper<V> {
		EnumMap<Direction, Passage<V>> neighbours = new EnumMap<>(
				Direction.class);

		Set<V> neighbours() {
			return CollectionUtils.setOf(neighbours.values().stream()
					.filter(Passage::isOpen).map(Passage::destination));
		}

		boolean getPassage(Direction d) {
			return neighbours.get(d).isOpen;
		}

		void setPassage(Direction d, boolean value) {
			neighbours.get(d).isOpen = value;
		}
	}

	@Override
	public Set<Edge<V>> edgesOf(V v) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<V> getVertices() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<V> neighboursOf(V v) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addWallAt(V v, Direction direction) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addWallBetween(V u, V v) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean hasWallAt(V v, Direction direction) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasWallBetween(V u, V v) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeWallAt(V v, Direction direction) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeWallBetween(V u, V v) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public V vertexAt(Position p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int width() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int height() {
		// TODO Auto-generated method stub
		return 0;
	}

}
