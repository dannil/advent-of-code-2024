package day2;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Day2 {

  public static void main(String[] args) throws Exception {
    Path inputPath = Paths.get(Day2.class.getResource("input.txt").toURI());
    List<String> input = Files.readAllLines(inputPath);

    List<int[]> reports = input.stream()
        .map(line -> line.split(" "))
        .map(levels -> Arrays.stream(levels).mapToInt(Integer::parseInt).toArray())
        .toList();

    // 407
    System.out.println(part1(reports));

    // 459
    System.out.println(part2(reports));
  }

  private static long part1(List<int[]> reports) {
    return reports.stream()
        .filter(Day2::isReportSafe)
        .count();
  }

  private static long part2(List<int[]> reports) {
    int safe = 0;
    for (int[] report : reports) {
      boolean isReportSafe = isReportSafe(report);
      if (isReportSafe) {
        safe++;
      } else {
        // The Problem Dampener allows for a single bad level
        // BRUTE FORCE: for every level in the unsafe report, create a dampened
        // report that exludes that level and check if said dampened report is
        // now a safe report; if not, keep repeating until end and if no
        // dampened report is safe, mark the report unsafe
        for (int i = 0; i < report.length; i++) {
          int[] dampenedReport = new int[report.length - 1];

          int index = 0;
          for (int j = 0; j < report.length; j++) {
            if (j != i) {
              dampenedReport[index++] = report[j];
            }
          }

          isReportSafe = isReportSafe(dampenedReport);
          if (isReportSafe) {
            safe++;
            break;
          }
        }
      }
    }
    return safe;
  }

  // Checks if a report is safe based on the two criterias of the levels being
  // 1) all increasing or all decreasing, and 2) adjacent level delta
  // satisfying 1 <= |delta| <= 3
  private static boolean isReportSafe(int[] report) {
    int first = report[0];
    int second = report[1];
    boolean isIncreasing = first < second;

    int current = first;
    for (int i = 0; i < report.length - 1; i++) {
      int next = report[i + 1];
      int difference = Math.abs(current - next);
      if ((difference < 1 || difference > 3)
          || (isIncreasing && current > next)
          || (!isIncreasing && current < next)) {
        return false;
      }
      current = next;
    }
    return true;
  }

}
