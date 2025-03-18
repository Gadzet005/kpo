package project.storages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
public class HSEBank {
    @Getter
    private BankAccountStorage accountStorage;
    @Getter
    private CategoryStorage categoryStorage;
    @Getter
    private OperationStorage operationStorage;

    @Autowired
    public HSEBank(BankAccountStorage accountStorage,
            CategoryStorage categoryStorage,
            OperationStorage operationStorage) {
        this.accountStorage = accountStorage;
        this.categoryStorage = categoryStorage;
        this.operationStorage = operationStorage;
    }
}
