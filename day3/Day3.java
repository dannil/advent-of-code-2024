package day3;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3 {

  public static void main(String[] args) throws Exception {
    Path inputPath = Paths.get(Day3.class.getResource("input.txt").toURI());
    String input = Files.readString(inputPath);

    System.out.println(part1(input));
    System.out.println(part2(input));
  }

  private static long part1(String input) {
    Pattern pattern = Pattern.compile("mul\\((\\d+),(\\d+)\\)");
    Matcher matcher = pattern.matcher(input);

    return matcher.results()
        .mapToLong(result -> {
          int left = Integer.parseInt(result.group(1));
          int right = Integer.parseInt(result.group(2));
          return left * right;
        })
        .sum();
  }

  private static long part2(String input) {
    Pattern pattern = Pattern.compile("(mul\\((\\d+),(\\d+)\\))|(do\\(\\))|(don't\\(\\))");
    Matcher matcher = pattern.matcher(input);

    long sum = 0;
    boolean enabled = true;
    while (matcher.find()) {
      String leftGroup = matcher.group(2);
      String rightGroup = matcher.group(3);
      String doGroup = matcher.group(4);
      String dontGroup = matcher.group(5);
      if (enabled && leftGroup != null && rightGroup != null) {
        int left = Integer.parseInt(leftGroup);
        int right = Integer.parseInt(rightGroup);
        sum += left * right;
      } else if (doGroup != null) {
        enabled = true;
      } else if (dontGroup != null) {
        enabled = false;
      }
    }
    return sum;
  }

}
