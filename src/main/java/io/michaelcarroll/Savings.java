package io.michaelcarroll;


public class Savings extends BankAccount {

    public Savings(String bankAccountNumber) {
        setBankAccountNumber(bankAccountNumber);
        setAccountStatus(AccountStatus.OPEN);
        setBankAccountInterestRate(.03);
    }
}
