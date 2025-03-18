package project.serializers.converters;

import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import project.consts.OperationType;
import project.domains.Operation;
import project.serializers.converters.base.ConvertUtils;
import project.serializers.converters.base.BaseFieldConverter;
import project.serializers.converters.base.FieldList;
import project.serializers.exceptions.ConvertException;
import project.storages.HSEBank;

@Component
public class OperationFieldConverter extends BaseFieldConverter<Operation> {
    public static final String DATE_FORMAT_STRING = "dd-MM-yyyy";
    private SimpleDateFormat dateFormat = new SimpleDateFormat(
            DATE_FORMAT_STRING);
    private HSEBank bank;

    @Autowired
    public OperationFieldConverter(HSEBank bank) {
        this.bank = bank;
    }

    @Override
    public FieldList convert(Operation obj) {
        var fields = new FieldList();
        fields.add("id", obj.getId());
        fields.add("type", obj.getType().name());
        fields.add("amount", obj.getAmount());
        fields.add("date", dateFormat.format(obj.getDate()));
        fields.add("description", obj.getDescription());
        fields.add("categoryId", obj.getCategory().getId());
        fields.add("accountId", obj.getAccount().getId());
        return fields;
    }

    @Override
    public Operation convertBack(FieldList map) throws ConvertException {
        var id = ConvertUtils.toInt(map.get("id"));
        var type = ConvertUtils.toEnum(map.get("type"), OperationType.class);
        var amount = ConvertUtils.toDouble(map.get("amount"));
        var date = ConvertUtils.toDate(map.get("date"), dateFormat);
        var description = ConvertUtils.toString(map.get("description"));
        var categoryId = ConvertUtils.toInt(map.get("categoryId"));
        var accountId = ConvertUtils.toInt(map.get("accountId"));

        var category = bank.getCategoryStorage().getCategory(categoryId);
        var account = bank.getAccountStorage().getAccount(accountId);

        return new Operation(id, type, amount, date, description, account,
                category);
    }
}
