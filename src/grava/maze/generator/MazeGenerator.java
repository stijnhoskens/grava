package grava.maze.generator;

import java.util.function.Function;

import grava.maze.Maze;
import grava.maze.MazeBuilder;
import grava.maze.Positioned;

public interface MazeGenerator<V extends Positioned> extends
		Function<MazeBuilder<V>, Maze<V>> {

}
