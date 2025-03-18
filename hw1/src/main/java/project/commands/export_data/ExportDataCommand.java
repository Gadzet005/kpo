package project.commands.export_data;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import project.commands.Command;
import project.commands.CommandError;
import project.consts.ImportExportTarget;
import project.serializers.SerializerMap;
import project.serializers.exceptions.SerializerException;
import project.storages.HSEBank;

@Component
public class ExportDataCommand implements Command<String, ExportDataParams> {
    private HSEBank bank;
    private SerializerMap serializerMap;

    @Autowired
    public ExportDataCommand(HSEBank bank, SerializerMap serializerMap) {
        this.bank = bank;
        this.serializerMap = serializerMap;
    }

    private List<Object> getExportObjects(ImportExportTarget target)
            throws CommandError {
        switch (target) {
        case ImportExportTarget.OPERATIONS:
            return new ArrayList<>(bank.getOperationStorage().getOperations());
        case ImportExportTarget.CATEGORIES:
            return new ArrayList<>(bank.getCategoryStorage().getCategories());
        case ImportExportTarget.BANK_ACCOUNTS:
            return new ArrayList<>(bank.getAccountStorage().getAccounts());
        default:
            throw new CommandError("Unsupported export target");
        }
    }

    @Override
    public String execute(ExportDataParams params) throws CommandError {
        var serializer = serializerMap.getSerializer(params.type(),
                params.target());
        if (serializer == null) {
            throw new CommandError("Unsupported import target");
        }

        var objs = getExportObjects(params.target());

        try {
            return serializer.serialize(objs);
        } catch (SerializerException e) {
            throw new CommandError("Error serializing data" + e.getMessage());
        }
    }
}
