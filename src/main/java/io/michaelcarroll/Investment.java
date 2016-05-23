package io.michaelcarroll;

public class Investment extends BankAccount  {

    private double returnOnInvestment;

    public Investment(String bankAccountNumber) {
        setBankAccountNumber(bankAccountNumber);
        setAccountStatus(AccountStatus.OPEN);
        setBankAccountInterestRate(0);
    }

}
