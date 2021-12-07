/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package aoc;

import static java.util.stream.Collectors.groupingBy;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class App {
  private final List<String> input;
  public App(List<String> input) {
    this.input = input;
  }

  public Integer getSolutionPart1(List<Integer> randomNumbers, List<Board> boards) {
    randomNumbers.stream().takeWhile(ignore -> boards.stream().noneMatch(board -> board.winner)).forEach(n -> boards.forEach(board -> board.markNumber(n)));
    return boards.stream().filter(board -> board.winner).findFirst().map(board -> board.score).orElse(-1);
  }

  public Integer getSolutionPart2(List<Integer> randomNumbers, List<Board> boards) {
    Set<Board> winnerBoards = new LinkedHashSet<>();
    randomNumbers.forEach(n -> boards.forEach(board -> {
      board.markNumber(n);
      if (board.winner)
        winnerBoards.add(board);
    }));
    return winnerBoards.stream().skip(winnerBoards.size()-1).findFirst().orElseThrow().score;
  }

  public static void main(String[] args) throws IOException {
    System.out.println("java");
    final List<String> input = Files.lines(Path.of("input.txt")).collect(Collectors.toList());
    final List<Integer> randomNumbers =
        Arrays.stream(input.get(0).split(",")).map(Integer::parseInt).collect(Collectors.toList());
    final AtomicInteger counter = new AtomicInteger();
    final List<Board> boards =
        input.stream().skip(2).filter(line -> !line.isBlank()).flatMap(line -> Arrays.stream(line.split("\\s+"))).filter(number -> !number.isBlank())
            .map(Integer::parseInt).collect(groupingBy(ignore -> counter.getAndIncrement() / 25)).values().stream().map(Board::new).collect(Collectors.toList());
    String part = System.getenv("part") == null ? "part2" : System.getenv("part");
    System.out.println(
        part.equals("part2") ? new App(input).getSolutionPart2(randomNumbers, boards) : new App(input).getSolutionPart1(randomNumbers, boards));
  }

  private static class Board {
    public final static List<List<Integer>> COL_ROW_WINNERS =
        List.of(
            List.of(0, 1, 2, 3, 4),
            List.of(5, 6, 7, 8, 9),
            List.of(10, 11, 12, 13, 14),
            List.of(15, 16, 17, 18, 19),
            List.of(20, 21, 22, 23, 24),

            List.of(0, 5, 10, 15, 20),
            List.of(1, 6, 11, 16, 21),
            List.of(2, 7, 12, 17, 22),
            List.of(3, 8, 13, 18, 23),
            List.of(4, 9, 14, 19, 24));
    private final HashSet<Integer> positions;
    private final List<Integer> numbers;
    private boolean winner;
    private int score;

    public Board(List<Integer> numbers) {
      this.numbers = numbers;
      winner = false;
      score = numbers.stream().mapToInt(Integer::intValue).sum();
      positions = new HashSet<>();
    }

    public void markNumber(int n) {
      if (winner)
        return;
      // win if index in a row e.g 1,2,3.. or column 1,6,11...
      final int positionOnBoard = numbers.indexOf(n);
      if (positionOnBoard != -1) {
        score -= n;
        positions.add(positionOnBoard);
      } else
          return;
      if (COL_ROW_WINNERS.stream().anyMatch(positions::containsAll)) {
        winner = true;
        score = score * n;
      }
    }
  }
}