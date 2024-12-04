package day4;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Day4 {

  public static void main(String[] args) throws Exception {
    Path inputPath = Paths.get(Day4.class.getResource("input.txt").toURI());
    List<String> input = Files.readAllLines(inputPath);

    char[][] matrix = input.stream()
        .map(String::toCharArray)
        .toArray(char[][]::new);

    System.out.println(part1(matrix));
    System.out.println(part2(matrix));
  }

  private static int part1(char[][] matrix) {
    int[][] directions = {
        { 0, 1 },
        { 1, 0 },
        { 1, 1 },
        { 1, -1 }
    };

    String toFind = "XMAS";
    int matches = 0;
    for (int y = 0; y < matrix.length; y++) {
      for (int x = 0; x < matrix[0].length; x++) {
        for (int[] direction : directions) {
          if (hasXmas(matrix, toFind, x, y, direction)) {
            matches++;
          }
          String toFindReversed = new StringBuilder(toFind).reverse().toString();
          if (hasXmas(matrix, toFindReversed, x, y, direction)) {
            matches++;
          }
        }
      }
    }
    return matches;
  }

  private static int part2(char[][] matrix) {
    String toFind = "MAS";
    int matches = 0;
    for (int y = 1; y < matrix.length - 1; y++) {
      for (int x = 1; x < matrix[0].length - 1; x++) {
        if (hasMasCross(matrix, toFind, x, y)) {
          matches++;
        }
      }
    }
    return matches;
  }

  private static boolean hasXmas(char[][] matrix, String toFind, int x, int y, int[] direction) {
    int length = toFind.length();

    // Check if adjacent cell to inspect would overflow the array indices
    int limitX = x + (length - 1) * direction[0];
    int limitY = y + (length - 1) * direction[1];
    if (limitX < 0 || limitY < 0 || limitX >= matrix[0].length || limitY >= matrix.length) {
      return false;
    }

    for (int i = 0; i < length; i++) {
      int currentX = x + i * direction[0];
      int currentY = y + i * direction[1];
      if (matrix[currentY][currentX] != toFind.charAt(i)) {
        return false;
      }
    }
    return true;
  }

  private static boolean hasMasCross(char[][] matrix, String toFind, int x, int y) {
    if (matrix[y][x] != toFind.charAt(1)) {
      return false;
    }

    char first = toFind.charAt(0);
    char last = toFind.charAt(2);

    char topRight = matrix[y - 1][x + 1];
    char bottomRight = matrix[y + 1][x + 1];
    char bottomLeft = matrix[y + 1][x - 1];
    char topLeft = matrix[y - 1][x - 1];

    boolean topLeftBottomRight = (topLeft == first && bottomRight == last) || (topLeft == last && bottomRight == first);
    boolean topRightBottomLeft = (topRight == first && bottomLeft == last) || (topRight == last && bottomLeft == first);
    return topLeftBottomRight && topRightBottomLeft;
  }

}
