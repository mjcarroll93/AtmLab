package io.michaelcarroll;

import java.util.HashMap;
import java.util.Map;

public class User {

    private int bankAccountPinCode;
    private String bankAccountHolderName;
    private Map<String, Map<String, BankAccount>> userBankAccounts = new HashMap<>();
    private Map<String, BankAccount> userAccountMap = new HashMap<>();


    public User(String bankAccountHolderName, int bankAccountPinCode) {
        this.bankAccountHolderName = bankAccountHolderName;
        this.bankAccountPinCode = bankAccountPinCode;
    }

    public int getBankAccountPinCode() {
        return bankAccountPinCode;
    }

    public String getBankAccountHolderName() {
        return bankAccountHolderName;
    }


    public void addCheckingAccount(String accountNumber) {
        userAccountMap.put(accountNumber, new Checking(accountNumber));
        userBankAccounts.put("Checking", userAccountMap);
    }

    public void addSavingsAccount(String accountNumber) {
        userAccountMap.put(accountNumber, new Savings(accountNumber));
        userBankAccounts.put("Savings", userAccountMap);
    }

    public void addInvestmentAccount(String accountNumber) {
        userAccountMap.put(accountNumber, new Investment(accountNumber));
        userBankAccounts.put("Investment", userAccountMap);
    }

    public BankAccount getUserCheckingAccount(String accountNumber) {
        return userBankAccounts.get("Checking").get(accountNumber);
    }

    public BankAccount getUserSavingsAccount(String accountNumber) {
        return userBankAccounts.get("Savings").get(accountNumber);
    }

    public BankAccount getUserInvestmentAccount(String accountNumber) {
        return userBankAccounts.get("Investment").get(accountNumber);
    }

}
