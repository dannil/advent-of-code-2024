package day1;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.IntStream;

public class Day1 {

  public static void main(String[] args) throws Exception {
    Path inputPath = Paths.get(Day1.class.getResource("input.txt").toURI());
    List<String> input = Files.readAllLines(inputPath);

    // Split the two columns into each line pair
    List<int[]> unsortedPairs = input.stream()
        .map(e -> e.split("\\s+"))
        .map(parts -> new int[] { Integer.parseInt(parts[0]), Integer.parseInt(parts[1]) })
        .toList();

    List<Integer> left = unsortedPairs.stream()
        .map(pair -> pair[0])
        .sorted()
        .toList();

    List<Integer> right = unsortedPairs.stream()
        .map(pair -> pair[1])
        .sorted()
        .toList();

    // 765748
    System.out.println(part1(left, right));

    // 27732508
    System.out.println(part2(left, right));
  }

  public static int part1(List<Integer> left, List<Integer> right) {
    return IntStream.range(0, left.size())
        .map(i -> Math.abs(left.get(i) - right.get(i)))
        .sum();
  }

  public static int part2(List<Integer> left, List<Integer> right) {
    return left.stream()
        .mapToInt(currentLeft -> currentLeft *
            (int) right.stream().filter(currentRight -> currentRight.equals(currentLeft)).count())
        .sum();
  }

}