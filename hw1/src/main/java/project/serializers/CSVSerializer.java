package project.serializers;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import project.serializers.converters.base.FieldConverter;
import project.serializers.converters.base.FieldList;

public class CSVSerializer<T> extends BaseSerializer<T> {
    private static final String DELIMITER = ",";

    public CSVSerializer(FieldConverter<T> converter) {
        super(converter);
    }

    @Override
    public String serialize(T[] objects) {
        if (objects == null || objects.length == 0) {
            return "";
        }

        FieldList[] converted = Arrays.stream(objects).map(converter::convert)
                .toArray(FieldList[]::new);

        String[] header = converted[0].names();

        StringBuilder csvBuilder = new StringBuilder();
        csvBuilder.append(String.join(DELIMITER, header)).append("\n");

        for (FieldList fieldList : converted) {
            String row = Arrays.stream(fieldList.values()).map(String::valueOf)
                    .collect(Collectors.joining(DELIMITER));
            csvBuilder.append(row).append("\n");
        }

        return csvBuilder.toString().trim();
    }

    @Override
    @SuppressWarnings("unchecked")
    public T[] deserialize(String data) {
        var lines = data.lines().toArray(String[]::new);

        if (lines.length == 0) {
            return (T[]) new Object[0];
        }

        var header = lines[0].split(DELIMITER);

        return (T[]) Arrays.stream(lines).skip(1).map(line -> {
            var values = line.split(DELIMITER);
            var fields = new FieldList();

            for (int i = 0; i < header.length; i++) {
                fields.add(header[i], values[i]);
            }

            return converter.convertBack(fields);
        }).toArray();
    }
}
