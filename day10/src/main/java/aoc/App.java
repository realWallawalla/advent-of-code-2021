package aoc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class App {
    Map<Character, Character> chunkPairs = Map.of('<', '>', '(', ')', '[', ']', '{', '}');

    public static void main(String[] args) throws IOException {
        List<ListIterator<Character>> lines = Files.lines(Path.of("input.txt"))
                .map(line -> line.chars().mapToObj(c -> (char) c)
                    .collect(Collectors.toList()))
                .map(List::listIterator)
                .toList();


       String part = System.getenv("part") == null ? "part2" : System.getenv("part");
       Long answer = part.equals("part2") ? new App().getSolutionPart2(lines) : new App().getSolutionPart1(lines);
       System.out.println(answer);
    }

    private Long getSolutionPart1(List<ListIterator<Character>> lineTokenizers) {
        List<Character> errors = new ArrayList<>();
        for (ListIterator<Character> tokenizer : lineTokenizers) {
            while (tokenizer.hasNext()) {
                Character currentToken = tokenizer.next();
                if (chunkPairs.containsKey(currentToken)) {
                    //all keys are chunks openers. go next until find a chunk closer
                    continue;
                }
                if (isLineCorrupted(tokenizer, chunkPairs)) {
                    //syntax error. collect token
                    errors.add(currentToken);
                    break;
                }
            }
        }
        return sumErrorScore(errors);
    }
    private boolean isLineCorrupted(ListIterator<Character> tokenizer, Map<Character, Character> chunkPairs) {
        // go back with pointer. its pointing to nth+1 elemnt, going back with prev and next need to be done twice otherwise you get element n.
        tokenizer.previous();
        Character currentOpener = tokenizer.previous();
        Character matchingCloser = chunkPairs.get(currentOpener);
        tokenizer.next();
        if (matchingCloser.equals(tokenizer.next())) {
            //remove matching pair
            tokenizer.remove();
            tokenizer.previous();
            tokenizer.remove();
            return false;
        }
        return true;
    }

    private Long sumErrorScore(List<Character> errors) {
        long score = 0;

        for (Character c: errors) {
            if (c.equals('>')) {
                score += 25137;
            } else if (c.equals('}')) {
                score += 1197;
            } else if (c.equals(']')) {
                score += 57;
            } else {
                score += 3;
            }
        }
        return score;
    }

    private Long getSolutionPart2(List<ListIterator<Character>> lineTokenizers) {
        List<Long> autoCompleteScores = new ArrayList<>();
        for (ListIterator<Character> tokenizer : lineTokenizers) {
            boolean isCorrupted = false;
            while (tokenizer.hasNext()) {
                if (chunkPairs.containsKey(tokenizer.next())) {
                    //all keys are chunks openers. go next until find a chunk closer
                    continue;
                }

                isCorrupted = isLineCorrupted(tokenizer, chunkPairs);
                if (isCorrupted) {
                    //syntax error. continue with next line
                    break;
                }
            }
            if (!isCorrupted) {
                autoCompleteScores.add(countScore(tokenizer));
            }
        }
        Collections.sort(autoCompleteScores);
        return autoCompleteScores.get((autoCompleteScores.size()-1)/2);
    }

    private long countScore(ListIterator<Character> tokenizer) {
        long score = 0;
        while (tokenizer.hasPrevious()) {
            Character previous = tokenizer.previous();
            Character closer = chunkPairs.get(previous);
            if (closer.equals('>')) {
                score = score * 5 + 4;
            } else if (closer.equals('}')) {
                score = score * 5 + 3;
            } else if (closer.equals(']')) {
                score = score * 5 + 2;
            } else {
                score = score * 5 + 1;
            }
        }
        return score;
    }
}