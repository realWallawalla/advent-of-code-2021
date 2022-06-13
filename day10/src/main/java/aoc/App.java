package aoc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class App {
    public static void main(String[] args) throws IOException {
        List<Integer> lines = Files.lines(Path.of("input.txt")).flatMap(s -> Arrays.stream(s.split(","))).map(Integer::parseInt).sorted()
                .collect(Collectors.toList());

        String part = System.getenv("part") == null ? "part2" : System.getenv("part");
        Integer answer = part.equals("part2") ? new App().getSolutionPart2(lines) : new App().getSolutionPart1(lines);
        System.out.println(answer);
    }

    private Integer getSolutionPart1(List<Integer> input) {
        return 1;
    }

    private Integer getSolutionPart2(List<Integer> input) {
        return 2;
    }
}
