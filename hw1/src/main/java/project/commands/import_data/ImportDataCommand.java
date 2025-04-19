package project.commands.import_data;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import project.commands.Command;
import project.commands.CommandError;
import project.consts.ImportExportTarget;
import project.domains.BankAccount;
import project.domains.Category;
import project.domains.Operation;
import project.serializers.SerializerMap;
import project.serializers.exceptions.SerializerException;
import project.storages.HSEBank;

@Component
public class ImportDataCommand implements Command<Integer, ImportDataParams> {
    private HSEBank bank;
    private SerializerMap serializerMap;

    @Autowired
    public ImportDataCommand(HSEBank bank, SerializerMap serializerMap) {
        this.bank = bank;
        this.serializerMap = serializerMap;
    }

    @Override
    public Integer execute(ImportDataParams params) throws CommandError {
        var serializer = serializerMap.getSerializer(params.type(),
                params.target());
        if (serializer == null) {
            throw new CommandError("Unsupported import target");
        }

        List<Object> objs;
        try {
            objs = serializer.deserialize(params.data());
        } catch (SerializerException e) {
            throw new CommandError("Error deserializing data" + e.getMessage());
        }

        for (var obj : objs) {
            switch (params.target()) {
            case ImportExportTarget.OPERATIONS:
                bank.getOperationStorage().addOperation((Operation) obj);
                break;
            case ImportExportTarget.CATEGORIES:
                bank.getCategoryStorage().addCategory((Category) obj);
                break;
            case ImportExportTarget.BANK_ACCOUNTS:
                bank.getAccountStorage().addAccount((BankAccount) obj);
                break;
            default:
                break;
            }
        }

        return objs.size();
    }
}
