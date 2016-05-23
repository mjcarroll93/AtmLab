package io.michaelcarroll;


import java.util.Date;

public class Transaction {

    private TransactionType transactionType;
    private double amountOfTransaction;
    private BankAccount sourceAccount;
    private BankAccount destinationAccount;
    private Date timeStamp;
    private long financialTransactionNumber;

    public Transaction(TransactionType transactionType, double amountOfTransaction, Date timeStamp) {

        this.transactionType = transactionType;
        this.amountOfTransaction = amountOfTransaction;
        this.sourceAccount = sourceAccount;
        this.destinationAccount = destinationAccount;
        this.timeStamp = timeStamp;
        this.financialTransactionNumber = financialTransactionNumber;

    }

    @Override
    public String toString() {
        return "Transaction :\n" +
                "Transaction Type = " + transactionType +
                "\nAmount Of Transaction = " + amountOfTransaction +
                "\nDate = " + timeStamp +
                "\n";
    }
}
