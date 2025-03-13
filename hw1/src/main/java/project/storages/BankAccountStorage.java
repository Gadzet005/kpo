package project.storages;

import java.util.Collection;
import java.util.HashMap;

import org.springframework.stereotype.Component;

import project.domains.BankAccount;
import project.factories.bank_account.BankAccountFactory;
import project.factories.bank_account.BankAccountParams;
import project.utils.IdCounter;

/**
 * This class manages a collection of bank accounts. It provides methods to
 * create, retrieve, check existence and remove bank accounts.
 */
@Component
public class BankAccountStorage {
    private HashMap<Integer, BankAccount> accounts = new HashMap<>();
    private IdCounter counter;
    private BankAccountFactory factory = new BankAccountFactory(counter);

    /**
     * Creates a new bank account using the provided parameters and adds it to
     * the storage.
     *
     * @param params The parameters for creating a new bank account.
     * @return The newly created bank account.
     */
    public BankAccount createAccount(BankAccountParams params) {
        var account = factory.create(params);
        accounts.put(account.getId(), account);
        return account;
    }

    /**
     * Adds a bank account to the storage if it does not already exist.
     *
     * @param account The bank account to add.
     * @return True if the account was added, false if it already existed.
     * 
     */
    public boolean addAccount(BankAccount account) {
        if (hasAccount(account.getId())) {
            return false;
        }
        accounts.put(account.getId(), account);
        return true;
    }

    /**
     * Retrieves a bank account by its unique identifier.
     *
     * @param id The unique identifier of the bank account.
     * @return The bank account with the specified identifier, or null if not
     *         found.
     */
    public BankAccount getAccount(int id) {
        return accounts.get(id);
    }

    public Collection<BankAccount> getAccounts() {
        return accounts.values();
    }

    /**
     * Checks if a bank account with the specified identifier exists in the
     * storage.
     *
     * @param id The unique identifier of the bank account.
     * @return True if the bank account exists, false otherwise.
     */
    public boolean hasAccount(int id) {
        return accounts.containsKey(id);
    }

    /**
     * Removes a bank account from the storage by its unique identifier.
     *
     * @param id The unique identifier of the bank account.
     * @return The removed bank account, or null if not found.
     */
    public BankAccount removeAccount(int id) {
        return accounts.remove(id);
    }
}
