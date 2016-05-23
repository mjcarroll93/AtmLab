package io.michaelcarroll;


import java.util.HashMap;
import java.util.Map;

public class Checking extends BankAccount {

    public Checking(String bankAccountNumber) {
        setBankAccountNumber(bankAccountNumber);
        setAccountStatus(AccountStatus.OPEN);
        setBankAccountInterestRate(0);
        setBankAccountBalance(0);
    }


}
