package day6;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day6 {

  public static void main(String[] args) throws Exception {
    Path inputPath = Paths.get(Day6.class.getResource("input2.txt").toURI());
    List<String> input = Files.readAllLines(inputPath);

    char[][] matrix = input.stream()
        .map(String::toCharArray)
        .toArray(char[][]::new);

    List<Character> startingDirections = List.of('^', 'v', '<', '>');
    int[] guardPosition = null;
    int[] direction = null;

    for (int y = 0; y < matrix.length; y++) {
      for (int x = 0; x < matrix[y].length; x++) {
        if (startingDirections.contains(matrix[y][x])) {
          guardPosition = new int[] { y, x };
          direction = getGuardDirection(matrix[y][x]);
          break;
        }
      }
    }

    System.out.println(part1(matrix, guardPosition, direction)); // 4515
    System.out.println(part2(matrix, guardPosition, direction));
  }

  private static long part1(char[][] matrix, int[] initialGuardPosition, int[] initialDirection) {
    boolean[][] visited = new boolean[matrix.length][matrix[0].length];

    int[] guardPosition = initialGuardPosition;
    int[] direction = initialDirection;
    while (true) {
      visited[guardPosition[0]][guardPosition[1]] = true;
      int nextY = guardPosition[0] + direction[0];
      int nextX = guardPosition[1] + direction[1];

      // Check if adjacent cell to inspect would overflow the array indices
      if (nextY < 0 || nextX < 0 || nextY >= matrix.length || nextX >= matrix[0].length) {
        break;
      }

      char nextCell = matrix[nextY][nextX];
      if (nextCell == '#') {
        // Turn right 90 degrees
        direction = turnRight(direction);
      }

      guardPosition = new int[] { guardPosition[0] + direction[0], guardPosition[1] + direction[1] };
    }

    return IntStream.range(0, visited.length)
        .mapToLong(y -> IntStream.range(0, visited[y].length)
            .filter(x -> visited[y][x])
            .count())
        .sum();
  }

  private static long part2(char[][] matrix, int[] initialGuardPosition, int[] initialDirection) {
    int obstructions = 0;

    TraversedCell[][] visited = new TraversedCell[matrix.length][matrix[0].length];

    int[] guardPosition = initialGuardPosition;
    int[] direction = initialDirection;
    while (true) {
      if (visited[guardPosition[0]][guardPosition[1]] == null) {
        visited[guardPosition[0]][guardPosition[1]] = new TraversedCell();
      }
      visited[guardPosition[0]][guardPosition[1]].isVisited = true;
      visited[guardPosition[0]][guardPosition[1]].direction = direction;
      int nextY = guardPosition[0] + direction[0];
      int nextX = guardPosition[1] + direction[1];

      // Check if adjacent cell to inspect would overflow the array indices
      if (nextY < 0 || nextX < 0 || nextY >= matrix.length || nextX >= matrix[0].length) {
        break;
      }

      char nextCell = matrix[nextY][nextX];
      if (nextCell == '#') {
        // Turn right 90 degrees
        direction = turnRight(direction);
      }

      // If a turn into already traversed cells where the direction of travel is
      // the same as when first traversing that cell, it would result in a cycle

      guardPosition = new int[] { guardPosition[0] + direction[0], guardPosition[1] + direction[1] };
    }

    return obstructions;
  }

  private static int[] turnRight(int[] direction) {
    return new int[] { direction[1], -direction[0] };
  }

  private static int[] getGuardDirection(char guardSymbol) {
    int[][] directions = {
        { -1, 0 }, // Up
        { 1, 0 }, // Down
        { 0, -1 }, // Left
        { 0, 1 } // Right
    };

    switch (guardSymbol) {
      case '^':
        return directions[0];
      case 'v':
        return directions[1];
      case '<':
        return directions[2];
      case '>':
        return directions[3];
      default:
        throw new IllegalArgumentException();
    }
  }

  private static class TraversedCell {
    public boolean isVisited;
    public int[] direction;
  }

}
