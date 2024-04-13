import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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

    private static boolean isPreserveChar(CharacterInString c) {
        char theChar = c.character;
        return Character.isLetter(theChar) || theChar == UNDERSCORE;
    }

    private static CharacterInString replaceCharIfNeed(CharacterInString c) {
        char theChar = c.character;
        return new CharacterInString(REPLACE_MAP.getOrDefault(theChar, theChar), c.predecessor);
    }

    private static CharacterInString kebabCase2CamelCase(CharacterInString c) {
        char theChar = c.character;
        char predecessor = c.predecessor;
        return new CharacterInString(predecessor == KEBAB ? Character.toUpperCase(theChar) : theChar, predecessor);
    }

    static String clean(String identifier) {
        char[] charArray = identifier.toCharArray();

        return IntStream.range(0, charArray.length)
                .mapToObj(i -> new CharacterInString(charArray[i], i > 0 ? charArray[i - 1] : ' '))
                .map(SqueakyClean::replaceCharIfNeed)
                .map(SqueakyClean::kebabCase2CamelCase)
                .filter(SqueakyClean::isPreserveChar)
                .map(CharacterInString::character)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();


    }

    record CharacterInString(char character, char predecessor) {}
}