package project.serializers.converters.base;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

public class ConvertUtils {
    private ConvertUtils() {
    }

    public static int toInt(Object obj) {
        switch (obj) {
        case Integer idInt:
            return idInt;
        case Double idDouble:
            return (int) Math.round(idDouble);
        case String idString:
            return Integer.parseInt(idString);
        default:
            throw new IllegalArgumentException("Invalid int format.");
        }
    }

    public static double toDouble(Object obj) {
        switch (obj) {
        case Double balanceDouble:
            return balanceDouble;
        case String balanceString:
            return Double.parseDouble(balanceString);
        default:
            throw new IllegalArgumentException("Invalid double format.");
        }
    }

    public static boolean toBoolean(Object obj) {
        switch (obj) {
        case Boolean isTrue:
            return isTrue;
        case String isTrueString:
            return Boolean.parseBoolean(isTrueString);
        default:
            throw new IllegalArgumentException("Invalid boolean format.");
        }
    }

    public static String toString(Object obj) {
        return obj == null ? "" : obj.toString();
    }

    public static <T extends Enum<T>> T toEnum(Object obj, Class<T> enumClass) {
        return Enum.valueOf(enumClass, ConvertUtils.toString(obj));
    }

    public static Date toDate(Object obj, DateFormat format) {
        try {
            return format.parse(ConvertUtils.toString(obj));
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format.");
        }
    }
}
