package payments_service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import common_lib.errors.ErrorCodes;
import common_lib.errors.ServiceError;
import jakarta.transaction.Transactional;
import payments_service.entities.Account;
import payments_service.storages.AccountStorage;

@Service
public class AccountService {
    private final AccountStorage accountStorage;

    @Autowired
    public AccountService(AccountStorage accountStorage) {
        this.accountStorage = accountStorage;
    }

    @Transactional
    public boolean createAccount(int userId) {
        if (accountStorage.existsById(userId)) {
            return false;
        }
        var account = Account.builder().userId(userId).build();
        accountStorage.save(account);
        return true;
    }

    @Transactional
    public int deposit(int userId, int amount) throws ServiceError {
        var a = accountStorage.findById(userId);
        if (!a.isPresent()) {
            throw new ServiceError(ErrorCodes.NOT_EXISTS);
        }

        var account = a.get();
        var balance = account.getAmount();

        if (balance + amount < 0) {
            throw new ServiceError(ErrorCodes.NOT_ENOUGH_MONEY);
        }

        account.setAmount(account.getAmount() + amount);
        accountStorage.save(account);
        return account.getAmount();
    }

    @Transactional
    public int getBalance(int userId) throws ServiceError {
        var account = accountStorage.findById(userId);
        if (!account.isPresent()) {
            throw new ServiceError(ErrorCodes.NOT_EXISTS);
        }
        return account.get().getAmount();
    }
}
