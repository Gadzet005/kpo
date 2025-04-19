package project.serializers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import project.consts.ImportExportTarget;
import project.consts.ImportExportType;
import project.serializers.base.Serializer;
import project.serializers.converters.BankAccountFieldConverter;
import project.serializers.converters.CategoryFieldConverter;
import project.serializers.converters.OperationFieldConverter;
import project.serializers.converters.base.BaseFieldConverter;

@Component
public class SerializerMap {
    private OperationFieldConverter operationConverter;
    private CategoryFieldConverter categoryConverter;
    private BankAccountFieldConverter bankAccountConverter;

    @Autowired
    public SerializerMap(OperationFieldConverter operationConverter,
            CategoryFieldConverter categoryConverter,
            BankAccountFieldConverter bankAccountConverter) {
        this.operationConverter = operationConverter;
        this.categoryConverter = categoryConverter;
        this.bankAccountConverter = bankAccountConverter;
    }

    private BaseFieldConverter<?> getConverter(ImportExportTarget target) {
        switch (target) {
        case ImportExportTarget.OPERATIONS:
            return operationConverter;
        case ImportExportTarget.BANK_ACCOUNTS:
            return bankAccountConverter;
        case ImportExportTarget.CATEGORIES:
            return categoryConverter;
        default:
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public Serializer<Object> getSerializer(ImportExportType type,
            ImportExportTarget target) {
        BaseFieldConverter<?> converter = getConverter(target);
        switch (type) {
        case JSON:
            return (Serializer<Object>) new JSONSerializer<>(converter);
        case CSV:
            return (Serializer<Object>) new CSVSerializer<>(converter);
        case YAML:
            return (Serializer<Object>) new YAMLSerializer<>(converter);
        default:
            return null;
        }
    }
}
