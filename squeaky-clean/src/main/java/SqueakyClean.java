import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class SqueakyClean {
    private static final char spaceChar = ' ';
    private static final Map<Character, Character> leetSpeakTable = Map.of(
            '4', 'a',
            '3', 'e',
            '0', 'o',
            '1', 'l',
            '7', 't'
    );
    private static final char kebab = '-';
    private static final char underscore = '_';

    static class Result {

        private boolean meetKebeb = false;
        private Stream<Character> sb = Stream.empty();

        public Result() {}

        protected Result(boolean meetKebeb, Stream<Character> sb) {
            this.meetKebeb = meetKebeb;
            this.sb = sb;
        }

        public static Result append(Result acc, char c) {
            if (c == kebab) {
                return new Result(true, acc.sb);
            } else {
                return new Result(
                        false,
                        Stream.concat(acc.sb, Stream.of(acc.meetKebeb ? Character.toUpperCase(c) : c))
                );
            }
        }
    }

    static String clean(String identifier) {
        char[] charArray = identifier.toCharArray();

        return IntStream.range(0, charArray.length)
                .mapToObj(i -> charArray[i])

                // Replace any spaces encountered with underscores
                .map(it -> it == spaceChar ? underscore : it)

                // Convert kebab-case to camelCase
                .reduce(
                        new Result(),
                        Result::append,
                        (result1, result2) -> result2
                )
                .sb
                // Convert leetspeak to normal text
                .map(it -> leetSpeakTable.getOrDefault(it, it))

                // Omit characters that are not letters
                .filter(it -> Character.isLetter(it) || it == underscore)
                .collect(
                        StringBuilder::new,
                        StringBuilder::append,
                        StringBuilder::append
                )
                .toString();

    }
}