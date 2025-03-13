package project.serializers.converters;

import org.springframework.stereotype.Component;

import project.domains.BankAccount;
import project.serializers.converters.base.ConvertUtils;
import project.serializers.converters.base.FieldConverter;
import project.serializers.converters.base.FieldList;

@Component
public class BankAccountFieldConverter implements FieldConverter<BankAccount> {
    @Override
    public FieldList convert(BankAccount obj) {
        var fields = new FieldList();
        fields.add("id", obj.getId());
        fields.add("name", obj.getName());
        fields.add("balance", obj.getBalance());
        return fields;
    }

    @Override
    public BankAccount convertBack(FieldList map) {
        var id = ConvertUtils.toInt(map.get("id"));
        var name = ConvertUtils.toString(map.get("name"));
        var balance = ConvertUtils.toDouble(map.get("balance"));

        return new BankAccount(id, name, balance);
    }
}
