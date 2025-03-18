package project.factories.bank_account;

public record BankAccountParams(String name) {
    public static BankAccountParams empty() {
        return new BankAccountParams("Bank account");
    }
}