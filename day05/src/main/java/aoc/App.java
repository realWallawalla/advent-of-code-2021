package aoc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class App {
  public Integer getSolutionPart1(List<Line> lines) {
    return paintGrid(lines);
  }

  public Integer getSolutionPart2(List<Line> lines) {
    return paintGrid(lines);
  }

  private Integer paintGrid(List<Line> lines) {
    int[][] grid = new int[1000][1000];
    AtomicInteger count = new AtomicInteger();
    lines.forEach(line -> line.points.forEach(point -> {
      grid[point.x][point.y] += 1;
      if (grid[point.x][point.y] == 2) {
        count.incrementAndGet();
      }
    }));
    return count.get();
  }

  public static void main(String[] args) throws IOException {
    System.out.println("java");
    List<Line> lines = Files.lines(Path.of("input.txt"))
        .map(s -> s.replaceAll("[^\\d,]", " "))
        .map(Line::new)
        .collect(Collectors.toList());
    String part = System.getenv("part") == null ? "part2" : System.getenv("part");
    System.out.println(
        part.equals("part2") ? new App().getSolutionPart2(lines)
            : new App().getSolutionPart1(lines));
  }

  private static class  Line {
    private List<Point> points = new ArrayList<>();

    public Line(String startAndEndCoordinates) {
      List<Integer> coordinates = Arrays.stream(startAndEndCoordinates.split("[\\s,]+")).map(String::trim)
          .map(Integer::parseInt).collect(Collectors.toList());
      int x1 = coordinates.get(0), y1 = coordinates.get(1), x2 = coordinates.get(2), y2 = coordinates.get(3);
      int xMin = Math.min(x1, x2), xMax = Math.max(x1, x2), yMax = Math.max(y1, y2), yMin = Math.min(y1, y2);
      if (y1 == y2) {
        points = IntStream.rangeClosed(xMin, xMax).boxed()
            .map(x -> new Point(x, y1)).collect(Collectors.toList());
      } else if(x1 == x2) {
        points = IntStream.rangeClosed(yMin, yMax).boxed()
            .map(y -> new Point(x1, y)).collect(Collectors.toList());
      } else if(x1 < x2 && y1 < y2 || x1 > x2 && y1 > y2) {
        List<Integer> yRange = IntStream.rangeClosed(yMin, yMax).boxed()
            .collect(Collectors.toList());
        List<Integer> xRange = IntStream.rangeClosed(xMin, xMax).boxed()
            .collect(Collectors.toList());
        for (int i = 0; i < xRange.size(); i++) {
          points.add(new Point(xRange.get(i), yRange.get(i)));
        }
      } else {
        List<Integer> xRange = IntStream.rangeClosed(xMin, xMax).boxed()
            .collect(Collectors.toList());
        List<Integer> yRange = IntStream.rangeClosed(yMin, yMax).map(i -> yMax + (yMin - i)).boxed()
            .collect(Collectors.toList());
        for (int i = 0; i < xRange.size(); i++) {
          points.add(new Point(xRange.get(i), yRange.get(i)));
        }
      }
    }

    static class Point {
      private final int x;
      private final int y;

      public Point(int x, int y) {
        this.x = x;
        this.y = y;
      }
    }
  }
}
