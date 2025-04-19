package project.serializers.converters.base;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import project.serializers.exceptions.ConvertException;

public class ConvertUtils {
    private ConvertUtils() {
    }

    public static int toInt(Object obj) throws ConvertException {
        switch (obj) {
        case Integer idInt:
            return idInt;
        case Double idDouble:
            return (int) Math.round(idDouble);
        case String idString:
            try {
                return Integer.parseInt(idString);
            } catch (NumberFormatException e) {
                throw new ConvertException(
                        "Can't parse integer: " + obj.getClass().getName());
            }
        default:
            throw new ConvertException(
                    "Invalid integer format: " + obj.getClass().getName());
        }
    }

    public static double toDouble(Object obj) throws ConvertException {
        switch (obj) {
        case Double balanceDouble:
            return balanceDouble;
        case String balanceString:
            try {
                return Double.parseDouble(balanceString);
            } catch (NumberFormatException e) {
                throw new ConvertException(
                        "Can't parse double: " + obj.getClass().getName());
            }
        default:
            throw new ConvertException(
                    "Invalid double format: " + obj.getClass().getName());
        }
    }

    public static boolean toBoolean(Object obj) throws ConvertException {
        switch (obj) {
        case Boolean isTrue:
            return isTrue;
        case String isTrueString:
            return Boolean.parseBoolean(isTrueString);
        default:
            throw new ConvertException(
                    "Invalid boolean format: " + obj.getClass().getName());
        }
    }

    public static String toString(Object obj) {
        return obj == null ? "" : obj.toString();
    }

    public static <T extends Enum<T>> T toEnum(Object obj, Class<T> enumClass) {
        return Enum.valueOf(enumClass, ConvertUtils.toString(obj));
    }

    public static Date toDate(Object obj, DateFormat format)
            throws ConvertException {
        try {
            return format.parse(ConvertUtils.toString(obj));
        } catch (ParseException e) {
            throw new ConvertException("Invalid date format.");
        }
    }
}
