package project.utils;

import java.util.Arrays;

public class EnumUtils {
    private EnumUtils() {
    }

    public static <T extends Enum<T>> String[] getNames(Class<T> e) {
        return Arrays.stream(e.getEnumConstants()).map(Enum::name)
                .toArray(String[]::new);
    }
}
