import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.IntStream;

class SqueakyClean {
    private static final char UNDERSCORE = '_';
    private static final char KEBAB = '-';

    private static final Map<Character, Character> REPLACE_MAP = Map.of(
            '4', 'a',
            '3', 'e',
            '0', 'o',
            '1', 'l',
            '7', 't',
            ' ', UNDERSCORE
    );

    private static boolean isPreserveChar(char c) {
        return Character.isLetter(c) || c == UNDERSCORE || c == KEBAB;
    }

    static String clean(String identifier) {
        char[] charArray = identifier.toCharArray();

        return IntStream.range(0, charArray.length)
                .mapToObj(i -> charArray[i])

                // Convert to normal text
                .map(it -> REPLACE_MAP.getOrDefault(it, it))

                // Omit characters that are not letters
                .filter(SqueakyClean::isPreserveChar)

                // Convert kebab-case to camelCase
                .collect(new Collector<Character, StringBuilder, String>() {
                    private boolean meetKebab = false;

                    @Override
                    public Supplier<StringBuilder> supplier() {
                        return StringBuilder::new;
                    }

                    @Override
                    public BiConsumer<StringBuilder, Character> accumulator() {
                        return (sb, c) -> {
                            if (c == KEBAB) {
                                // meet kebab skip this round and set mark
                                meetKebab = true;
                            } else {
                                // normal append this character and set mark to false
                                sb.append(meetKebab ? Character.toUpperCase(c) : c);
                                meetKebab = false;
                            }
                        };
                    }

                    @Override
                    public BinaryOperator<StringBuilder> combiner() {
                        return StringBuilder::append;
                    }

                    @Override
                    public Function<StringBuilder, String> finisher() {
                        return StringBuilder::toString;
                    }

                    @Override
                    public Set<Characteristics> characteristics() {
                        return Set.of();
                    }
                });
    }
}