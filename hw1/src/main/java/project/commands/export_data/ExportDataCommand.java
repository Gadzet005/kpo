package project.commands.export_data;

import java.io.IOException;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import project.commands.Command;
import project.commands.CommandError;
import project.consts.ImportExportTarget;
import project.serializers.SerializerMap;
import project.storages.HSEBank;

@Component
public class ExportDataCommand implements Command<Integer, ExportDataParams> {
    private HSEBank bank;
    private SerializerMap serializerMap;

    @Autowired
    public ExportDataCommand(HSEBank bank, SerializerMap serializerMap) {
        this.bank = bank;
        this.serializerMap = serializerMap;
    }

    private Collection<? extends Object> getExportObjects(
            ImportExportTarget target) throws CommandError {
        switch (target) {
        case ImportExportTarget.OPERATIONS:
            return bank.getOperationStorage().getOperations();
        case ImportExportTarget.CATEGORIES:
            return bank.getCategoryStorage().getCategories();
        case ImportExportTarget.BANK_ACCOUNTS:
            return bank.getAccountStorage().getAccounts();
        default:
            throw new CommandError("Unsupported export target");
        }
    }

    @Override
    public Integer execute(ExportDataParams params) throws CommandError {
        var serializer = serializerMap.getSerializer(params.type(),
                params.target());
        if (serializer == null) {
            throw new CommandError("Unsupported import target");
        }

        var objs = getExportObjects(params.target());
        var data = serializer.serialize(objs.toArray());
        try {
            params.writer().write(data);
        } catch (IOException e) {
            throw new CommandError("Error writing to output stream" + e);
        }

        // TODO: return real number of exported objects
        return objs.size();
    }
}
