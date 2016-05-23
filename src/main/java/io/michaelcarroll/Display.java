package io.michaelcarroll;

import java.text.NumberFormat;
import java.util.Scanner;

public class Display {
    Scanner input = new Scanner(System.in);
    NumberFormat formatter = NumberFormat.getCurrencyInstance();
    private boolean powerOn = true;
    private int currentIdOnDisplay;
    private BankAccount currentAccountOnDisplay;

    public void runAtm() {
        carrollBankLogo();
        returningUserOrNewUserMessage();
        newAccountOrReturningUserHandler(getCommandFromUser());
        while (powerOn) {
            startAtmAndPromptUser();
        }
    }

    public void newAccountOrReturningUserHandler(int command) {
        switch (command) {
            case 1:
                promptUserToEnterName();
                String name = input.nextLine();
                currentIdOnDisplay = UserAccountsHandler.usersAccountsWithBank.size() + 1;
                promptUserToEnterPinCode();
                int pin = input.nextInt();
                UserAccountsHandler.addAccountToBank(currentIdOnDisplay, new User(name, pin));
                System.out.println("Hello " + name + ". Your Id is " + currentIdOnDisplay + " and your pin code is " + pin);
                checkingSavingsOrInvestmentPrompt();
                newCheckingSavingsOrInvestmentAccountSpecifier(getCommandFromUser(), currentIdOnDisplay);
                break;
            case 2:
                welcomeBackMessage();
                currentIdOnDisplay = getCommandFromUser();
                System.out.println(UserAccountsHandler.usersAccountsWithBank.get(currentIdOnDisplay).getBankAccountHolderName());
                whatIsYourPinCode();
                int pinCodeEntered = getCommandFromUser();
                int actualPinCode = UserAccountsHandler.usersAccountsWithBank.get(currentIdOnDisplay).getBankAccountPinCode();
                boolean accessGranted = checkPinCode(pinCodeEntered, actualPinCode);
                if (accessGranted) {
                    whichAccountTypeWouldYouLikeToAccessMessage();
                    int accessCommand = getCommandFromUser();
                    whatIsYourBankAccountNumber();
                    String accountNumber = getStringFromUser();
                    accessAccount(currentIdOnDisplay, accessCommand, accountNumber);
                } else {
                    sorryYouEnteredTheWrongPin();
                    runAtm();
                }
                break;
            default:
                System.out.println("Please enter either a 1 for new user or " +
                        "2 for returning user");
                returningUserOrNewUserMessage();
                newAccountOrReturningUserHandler(getCommandFromUser());
        }
    }

    public int getCommandFromUser() {
        return input.nextInt();
    }

    public double getAmountFromUser() {
        return input.nextDouble();
    }

    public void startAtmAndPromptUser() {
        menuPrompt();
        int commandEntered = getCommandFromUser();
        commandHandler(commandEntered);
    }

    public void commandHandler(int commandEntered) {
        switch (commandEntered) {
            case 1:
                displayBalance(currentAccountOnDisplay);
                break;
            case 2:
                withdrawMessage();
                currentAccountOnDisplay.makeWithdrawFromAccount(getAmountFromUser());
                break;
            case 3:
                depositMessage();
                currentAccountOnDisplay.makeDepositIntoAccount(getAmountFromUser());
                break;
            case 5:
                currentAccountOnDisplay.printAccountTransactionHistory();
                break;
            case 6:
                checkingSavingsOrInvestmentPrompt();
                newCheckingSavingsOrInvestmentAccountSpecifier(getCommandFromUser(), currentIdOnDisplay);
                break;
            case 7:
                areYouSureYouWantToCloseThisAccount();
                int answer = getCommandFromUser();
                if (answer == 1) {
                    currentAccountOnDisplay.makeWithdrawFromAccount(currentAccountOnDisplay.getBankAccountBalance());
                    currentAccountOnDisplay.closeAccount();
                }
                break;
            case 4:
            case 8:
                askForTransferAccountType();
                int accountType = getCommandFromUser();
                askForTransferUserId();
                int userId = getCommandFromUser();
                askForAccountNumber();
                String userAccountNumber = getStringFromUser();
                askForAmountToTransfer();
                double amount = getAmountFromUser();
                currentAccountOnDisplay.transferTo(accountType, userId, userAccountNumber, amount);
                break;
            case 9:
                currentAccountOnDisplay.freezeAccount();
                break;
            case 10:
                currentAccountOnDisplay.unFreezeAccount();
                break;
            case 11:
                runAtm();
                break;
            case 12:
                powerOn = false;
                break;
            default:
                System.out.println("Please enter a valid command available on the screen: ");
                startAtmAndPromptUser();
                break;
        }
    }

    public void newCheckingSavingsOrInvestmentAccountSpecifier(int answer, int id) {
        enterBankAccountNumberMessage();
        String accountNumber = getStringFromUser();
        switch (answer) {
            case 1:
                UserAccountsHandler.usersAccountsWithBank.get(id).addCheckingAccount(accountNumber);
                accessAccount(id, 1, accountNumber);
                break;
            case 2:
                UserAccountsHandler.usersAccountsWithBank.get(id).addSavingsAccount(accountNumber);
                accessAccount(id, 2, accountNumber);
                break;
            case 3:
                UserAccountsHandler.usersAccountsWithBank.get(id).addInvestmentAccount(accountNumber);
                accessAccount(id, 3, accountNumber);
                break;
            default:
                System.out.println("Please enter either a '1' for Checking, '2' for Saving or '3' for Investment ");
                newCheckingSavingsOrInvestmentAccountSpecifier(answer, id);
        }
    }

    public void accessAccount(int currentIdOnDisplay, int accountType, String accountNumber) {
        switch (accountType) {
            case 1:
                currentAccountOnDisplay = UserAccountsHandler.usersAccountsWithBank.get(currentIdOnDisplay).getUserCheckingAccount(accountNumber);
                break;
            case 2:
                currentAccountOnDisplay = UserAccountsHandler.usersAccountsWithBank.get(currentIdOnDisplay).getUserSavingsAccount(accountNumber);
                break;
            case 3:
                currentAccountOnDisplay = UserAccountsHandler.usersAccountsWithBank.get(currentIdOnDisplay).getUserInvestmentAccount(accountNumber);
        }

    }

    public void displayBalance(BankAccount currentAccountOnDisplay) {
        System.out.println("\nCurrent Balance in " + currentAccountOnDisplay.getClass().getName().replace("io.michaelcarroll.", "") + ": " + formatter.format(currentAccountOnDisplay.getBankAccountBalance()) + "\n");
    }

    public boolean checkPinCode(int pinEntered, int actualPin) {
        if (pinEntered != actualPin)
            return false;
        else
            return true;
    }

    public void sorryYouEnteredTheWrongPin() {
        System.out.println("Sorry you entered the wrong Pin");
    }

    public void returningUserOrNewUserMessage() {
        System.out.println("Welcome to Carroll Bank\n" +
                "Are you a 1.) New User " +
                "2.) Returning User");
    }

    public void menuPrompt() {
        System.out.print("WELCOME TO CARROLL BANK - PLEASE ENTER A CORRESPONDING COMMAND\n" +
                "1.)Display Balance\n" +
                "2.) Withdraw\n" +
                "3.)Deposit\n" +
                "4.)Transfer funds across accounts\n" +
                "5.) Print transaction history\n" +
                "6.) Open new account\n" +
                "7.) Close account\n " +
                "8.)Transfer to another user's account\n" +
                "9.)Freeze Account\n" +
                "10.)Unfreeze account\n" +
                "11.)Log Out\n" +
                "12.)Exit ATM\n"
        );
    }

    public void withdrawMessage() {
        System.out.println("Please enter an amount to withdraw: ");
    }

    public void depositMessage() {
        System.out.println("Please enter the amount you'd like to deposit: ");
    }

    public void askForAmountToTransfer() {
        System.out.println("Please enter the amount you'd like to Transfer: ");
    }

    public void promptUserToEnterName() {
        System.out.println("Please enter your name");
    }

    public void promptUserToEnterPinCode() {
        System.out.println("Please enter a 4-digit Pin Code for later use");
    }

    public void welcomeBackMessage() {
        System.out.println("Welcome back! Enter your account ID to access your account");
    }

    public void checkingSavingsOrInvestmentPrompt() {
        System.out.println("Would you like to open a 1.)Checking 2.)Savings or 3.)Investment Account? Enter the " +
                "corresponding number value ");
    }

    public void whichAccountTypeWouldYouLikeToAccessMessage() {
        System.out.println("Would account would you like to access.. 1.)Checking 2.)Savings or 3.)Investment Account? Enter the " +
                "corresponding number value ");
    }

    public void whatIsYourBankAccountNumber() {
        System.out.println("Please enter your bank account number: ");
    }

    public void whatIsYourPinCode() {
        System.out.println("Enter your Pin Code");
    }

    public void enterBankAccountNumberMessage() {
        System.out.println("Please enter desired bank Account Number: ");
    }

    public void askForTransferAccountType() {
        System.out.println("Which account type would you like to send money to 1.) Checking\n2.)Savings\n3.)Investment");
    }

    public void askForTransferUserId() {
        System.out.println("What is the user's Id you'd like to transfer to?");
    }

    public void askForAccountNumber() {
        System.out.println("Enter account number to transfer to: ");
    }

    public void areYouSureYouWantToCloseThisAccount() {
        System.out.println("Are you sure you want to close this account?\n1.) YES 2.)NO");
    }

    public String getStringFromUser() {
        return input.next();
    }

    public static void accountIsFrozenNotification() {
        System.out.println("Account is Frozen");
    }

    public static void creditTransactionApprovalConfirmation() {
        System.out.println("**Credit Transaction Approved**");
    }

    public static void debitTransactionApprovalConfirmation() {
        System.out.println("**Debit Transaction Approved**");
    }

    public static void debitTransactionDeniedConfirmation() {
        System.out.println("**Debit Transaction Denied**");
    }

    public static void accountBalanceIsOverdrawnMessage() {
        System.out.println("\nERROR - Account Overdrawn...Transaction not completed\n");
    }

    public void carrollBankLogo() {
        System.out.println("  /$$$$$$                                          /$$ /$$                        \n" +
                " /$$__  $$                                        | $$| $$                        \n" +
                "| $$  \\__/  /$$$$$$   /$$$$$$   /$$$$$$   /$$$$$$ | $$| $$                        \n" +
                "| $$       |____  $$ /$$__  $$ /$$__  $$ /$$__  $$| $$| $$                        \n" +
                "| $$        /$$$$$$$| $$  \\__/| $$  \\__/| $$  \\ $$| $$| $$                        \n" +
                "| $$    $$ /$$__  $$| $$      | $$      | $$  | $$| $$| $$                        \n" +
                "|  $$$$$$/|  $$$$$$$| $$      | $$      |  $$$$$$/| $$| $$                        \n" +
                " \\______/  \\_______/|__/      |__/       \\______/ |__/|__/                        \n" +
                "                                                                                  \n" +
                "                                                                                  \n" +
                "                                                                                  \n" +
                "                                           /$$$$$$$                      /$$      \n" +
                "                                          | $$__  $$                    | $$      \n" +
                "                                          | $$  \\ $$  /$$$$$$  /$$$$$$$ | $$   /$$\n" +
                "                                          | $$$$$$$  |____  $$| $$__  $$| $$  /$$/\n" +
                "                                          | $$__  $$  /$$$$$$$| $$  \\ $$| $$$$$$/ \n" +
                "                                          | $$  \\ $$ /$$__  $$| $$  | $$| $$_  $$ \n" +
                "                                          | $$$$$$$/|  $$$$$$$| $$  | $$| $$ \\  $$\n" +
                "                                          |_______/  \\_______/|__/  |__/|__/  \\__/\n" +
                "                                                                                  \n" +
                "                                                                               ");
    }
}
