package project.serializers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import project.serializers.base.BaseSerializer;
import project.serializers.converters.base.FieldConverter;
import project.serializers.converters.base.FieldList;
import project.serializers.exceptions.ConvertException;
import project.serializers.exceptions.SerializerException;

public class CSVSerializer<T> extends BaseSerializer<T> {
    private static final String DELIMITER = ",";

    public CSVSerializer(FieldConverter<T> converter) {
        super(converter);
    }

    @Override
    public String serialize(List<T> objects) throws SerializerException {
        if (objects.isEmpty()) {
            return "";
        }

        var converted = converter.convertList(objects);

        String[] header = converted.get(0).names();

        StringBuilder csvBuilder = new StringBuilder();
        csvBuilder.append(String.join(DELIMITER, header)).append("\n");

        for (FieldList fieldList : converted) {
            String row = Arrays.stream(fieldList.values()).map(String::valueOf)
                    .collect(Collectors.joining(DELIMITER));
            csvBuilder.append(row).append("\n");
        }

        return csvBuilder.toString().trim();
    }

    private T convertRow(String[] header, String[] row)
            throws ConvertException {
        var fields = new FieldList();
        for (int i = 0; i < header.length; i++) {
            fields.add(header[i], row[i]);
        }
        return converter.convertBack(fields);
    }

    @Override
    public List<T> deserialize(String data) throws SerializerException {
        var lines = data.lines().toList();

        if (lines.isEmpty()) {
            return List.of();
        }

        var header = lines.get(0).split(DELIMITER);
        ArrayList<T> result = new ArrayList<>();
        for (var line : lines.subList(1, lines.size())) {
            var values = line.split(DELIMITER);
            if (values.length != header.length) {
                throw new SerializerException("Invalid CSV format");
            }
            result.add(convertRow(header, values));
        }

        return result;
    }
}
