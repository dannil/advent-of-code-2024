package day5;

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

public class Day5 {

  public static void main(String[] args) throws Exception {
    Path inputPath = Paths.get(Day5.class.getResource("input.txt").toURI());
    String input = Files.readString(inputPath);

    String[] components = input.replace("\r\n", "\n").split("\\n\\s*\\n");
    String[] orderingRulesRaw = components[0].split("\\n");
    String[] updatesRaw = components[1].split("\\n");

    Map<Integer, List<Integer>> orderingRules = new HashMap<>();
    for (String row : orderingRulesRaw) {
      String[] parts = row.split("\\|");
      int x = Integer.parseInt(parts[0]);
      int y = Integer.parseInt(parts[1]);
      orderingRules.computeIfAbsent(x, value -> new ArrayList<>()).add(y);
    }

    List<int[]> updates = new ArrayList<>();
    for (String row : updatesRaw) {
      String[] parts = row.split(",");
      int[] parsed = new int[parts.length];
      for (int i = 0; i < parts.length; i++) {
        parsed[i] = Integer.parseInt(parts[i]);
      }
      updates.add(parsed);
    }

    System.out.println(part1(updates, orderingRules));
    System.out.println(part2(updates, orderingRules));
  }

  private static int part1(List<int[]> updates, Map<Integer, List<Integer>> orderingRules) {
    return updates.stream()
        .filter(update -> isCorrectOrder(update, orderingRules))
        .mapToInt(update -> update[update.length / 2])
        .sum();
  }

  private static int part2(List<int[]> updates, Map<Integer, List<Integer>> orderingRules) {
    return updates.stream()
        .filter(update -> !isCorrectOrder(update, orderingRules))
        .map(update -> sortByOrderingRules(update, orderingRules))
        .mapToInt(update -> update[update.length / 2])
        .sum();
  }

  private static boolean isCorrectOrder(int[] update, Map<Integer, List<Integer>> orderingRules) {
    Set<Integer> toCheck = Arrays.stream(update).boxed().collect(Collectors.toSet());
    for (int i = 0; i < update.length - 1; i++) {
      int currentUpdate = update[i];
      toCheck.remove(currentUpdate);
      List<Integer> orderingRule = orderingRules.getOrDefault(currentUpdate, List.of());
      for (Integer j : toCheck) {
        boolean hasRule = orderingRule.contains(j);
        if (!hasRule) {
          return false;
        }
      }
    }
    return true;
  }

  private static int[] sortByOrderingRules(int[] update, Map<Integer, List<Integer>> orderingRules) {
    return Arrays.stream(update)
        .boxed()
        .sorted((a, b) -> orderingRules.getOrDefault(a, List.of()).contains(b) ? -1 : 1)
        .mapToInt(Integer::intValue)
        .toArray();
  }

}
