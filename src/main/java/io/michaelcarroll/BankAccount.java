package io.michaelcarroll;

import java.util.ArrayList;
import java.util.Date;

public abstract class BankAccount {

    private String bankAccountNumber;
    private double bankAccountBalance;
    private AccountStatus accountStatus;
    private double bankAccountInterestRate;
    private OverdraftPrevention overdraftPreventionStatus = OverdraftPrevention.ENABLED;
    private ArrayList<Transaction> bankAccountTransactionsLedger = new ArrayList<Transaction>();


    public void setBankAccountInterestRate(double bankAccountInterestRate) {
        this.bankAccountInterestRate = bankAccountInterestRate;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public double getBankAccountBalance() {
        if (!checkIfAccountStatusIsFrozen()) {
            return bankAccountBalance;
        } else {
            Display.accountIsFrozenNotification();
            return 0;
        }
    }

    public void setBankAccountBalance(double bankAccountBalance) {
        this.bankAccountBalance = bankAccountBalance;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    public void addTransactionToLedger(TransactionType transactionType, double amountOfTransaction, Date timeStamp) {
        this.bankAccountTransactionsLedger.add(new Transaction(transactionType, amountOfTransaction, timeStamp));
    }

    public void printAccountTransactionHistory() {
        for (int i = 0; i < bankAccountTransactionsLedger.size(); i++) {
            System.out.println(i + 1 + ".) " + bankAccountTransactionsLedger.get(i).toString());
        }
    }

    public void makeDepositIntoAccount(double amount) {
        if (checkIfAccountStatusIsOpen()) {
            bankAccountBalance += amount;
            addTransactionToLedger(TransactionType.DEPOSIT, amount, new Date());
            Display.creditTransactionApprovalConfirmation();
        }
    }

    public void makeWithdrawFromAccount(double amount) {
        if (checkIfAccountStatusIsOpen() && checkIfAccountHasOverdraftProtection()) {
            if (checkIfAccountBalanceIsOverdrawn(amount)) {
                Display.accountBalanceIsOverdrawnMessage();
            } else {
                bankAccountBalance -= amount;
                addTransactionToLedger(TransactionType.WITHDRAW, amount, new Date());
                Display.debitTransactionApprovalConfirmation();
            }
        }
        if (checkIfAccountStatusIsOpen() && !checkIfAccountHasOverdraftProtection()) {
            bankAccountBalance -= amount;
            Display.debitTransactionApprovalConfirmation();
        }
        if (!checkIfAccountStatusIsOpen()) {
            Display.debitTransactionDeniedConfirmation();
        }
    }

    public boolean checkIfAccountStatusIsFrozen() {
        if (accountStatus == AccountStatus.FROZEN) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkIfAccountStatusIsOpen() {
        if (accountStatus == AccountStatus.OPEN) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkIfAccountHasOverdraftProtection() {
        if (overdraftPreventionStatus == OverdraftPrevention.ENABLED) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkIfAccountHasAutomaticTransfer() {
        if (overdraftPreventionStatus == OverdraftPrevention.AUTOMATICTRANSFER) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkIfAccountBalanceIsOverdrawn(double amount) {
        if (bankAccountBalance - amount < 0) {
            return true;
        } else {
            return false;
        }
    }

    public void transferTo(int command, int id, String toBankAccountNumber, double amount) {
        bankAccountBalance -= amount;
        addTransactionToLedger(TransactionType.TRANSFER, amount, new Date());
        switch (command) {
            case 1:
                UserAccountsHandler.usersAccountsWithBank.get(id).getUserCheckingAccount(toBankAccountNumber).makeDepositIntoAccount(amount);
                break;
            case 2:
                UserAccountsHandler.usersAccountsWithBank.get(id).getUserSavingsAccount(toBankAccountNumber).makeDepositIntoAccount(amount);
                break;
            case 3:
                UserAccountsHandler.usersAccountsWithBank.get(id).getUserInvestmentAccount(toBankAccountNumber).makeDepositIntoAccount(amount);
        }
    }

    public void freezeAccount() {
        accountStatus = AccountStatus.FROZEN;
    }

    public void unFreezeAccount() {
        accountStatus = AccountStatus.OPEN;
    }

    public void closeAccount() {
        accountStatus = AccountStatus.CLOSED;
        bankAccountBalance = 0;
    }

}

