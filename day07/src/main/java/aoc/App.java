package aoc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class App {
  public Integer getSolutionPart1(List<Integer> numbers) {
    int min = numbers.get(0), max = numbers.get(numbers.size()-1);
    List<Integer> result = new ArrayList<>();
    for (int i = min; i < max; i++) {
      List<Integer> sum = new ArrayList<>();
      for (int n: numbers) {
        sum.add(Math.abs(n-i));
      }
      result.add(sum.stream().mapToInt(Integer::intValue).sum());
    }
    return result.stream().sorted().findFirst().get();
  }

  public Integer getSolutionPart2(List<Integer> numbers) {
    int min = numbers.get(0), max = numbers.get(numbers.size()-1);
    List<Integer> result = new ArrayList<>();
    for (int i = min; i < max; i++) {
      List<Integer> sum = new ArrayList<>();
      for (int n: numbers) {
        sum.add(IntStream.rangeClosed(0, Math.abs(n-i)).sum());
      }
      result.add(sum.stream().mapToInt(Integer::intValue).sum());
    }
    return result.stream().sorted().findFirst().get();
  }

  public static void main(String[] args) throws IOException {
    System.out.println("java");
    List<Integer> lines = Files.lines(Path.of("input.txt")).flatMap(s -> Arrays.stream(s.split(","))).map(Integer::parseInt).sorted()
        .collect(Collectors.toList());
    String part = System.getenv("part") == null ? "part2" : System.getenv("part");
    System.out.println(part.equals("part2") ? new App().getSolutionPart2(lines) : new App().getSolutionPart1(lines));
  }
}

