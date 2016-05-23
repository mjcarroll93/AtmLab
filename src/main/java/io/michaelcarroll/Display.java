package io.michaelcarroll;

import java.text.NumberFormat;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Display {
    Scanner input = new Scanner(System.in);
    NumberFormat formatter = NumberFormat.getCurrencyInstance();
    private boolean powerOn = true;
    private int currentIdOnDisplay;
    private BankAccount currentAccountOnDisplay;

    public void runAtm() {
        carrollBankLogo();
        newAccountOrReturningUserHandler(getIntPrompt("Welcome to Carroll Bank!\n Are you a..\n 1.) New User\n2.) Returning User\n"));
        while (powerOn) {
            startAtmAndPromptUser();
        }
    }

    public void newAccountOrReturningUserHandler(int command) {
        switch (command) {
            case 1:
                String name = getStringPrompt("Please enter your name");
                currentIdOnDisplay = UserAccountsHandler.usersAccountsWithBank.size() + 1;
                int pin = getIntPrompt("Please enter a 4-digit Pin Code for later use");
                UserAccountsHandler.addAccountToBank(currentIdOnDisplay, new User(name, pin));
                System.out.println("Hello " + name + ". Your Id is " + currentIdOnDisplay + " and your pin code is " + pin);
                newCheckingSavingsOrInvestmentAccountSpecifier(accountTypeToOpen(), currentIdOnDisplay);
                break;
            case 2:
                currentIdOnDisplay = getIntPrompt("Welcome back! Enter your account ID to access your account");
                printMessage(UserAccountsHandler.usersAccountsWithBank.get(currentIdOnDisplay).getBankAccountHolderName());
                int pinCodeEntered = getIntPrompt("Enter your Pin Code");
                int actualPinCode = UserAccountsHandler.usersAccountsWithBank.get(currentIdOnDisplay).getBankAccountPinCode();
                boolean accessGranted = checkPinCode(pinCodeEntered, actualPinCode);
                if (accessGranted) {
                    int accessCommand = getIntPrompt("Would account would you like to access.. 1.)Checking 2.)Savings or 3.)Investment Account? Enter the " +
                            "corresponding number value ");
                    String accountNumber = getStringPrompt("Please enter your bank account number: ");
                    accessAccount(currentIdOnDisplay, accessCommand, accountNumber);
                } else {
                    printMessage("Sorry you entered the wrong Pin");
                    runAtm();
                }
                break;
            default:
                printMessage("Please enter either a 1 for new user or " +
                        "2 for returning user");
                newAccountOrReturningUserHandler(getIntPrompt("Welcome to Carroll Bank\n Are you a 1.) New User\n2.) Returning User"));
        }
    }

    public void startAtmAndPromptUser() {
        commandHandler(getIntPrompt(menuPrompt()));
    }

    public void commandHandler(int commandEntered) {
        switch (commandEntered) {
            case 1:
                displayBalance(currentAccountOnDisplay);
                break;
            case 2:
                currentAccountOnDisplay.makeWithdrawFromAccount(getDoublePrompt("Please enter an amount to withdraw: "));
                break;
            case 3:
                currentAccountOnDisplay.makeDepositIntoAccount(getDoublePrompt("Please enter the amount you'd like to deposit: "));
                break;
            case 5:
                currentAccountOnDisplay.printAccountTransactionHistory();
                break;
            case 6:
                newCheckingSavingsOrInvestmentAccountSpecifier(accountTypeToOpen(), currentIdOnDisplay);
                break;
            case 7:
                int answer = getIntPrompt("Are you sure you want to close this account?\n1.) YES 2.)NO");
                if (answer == 1) {
                    currentAccountOnDisplay.makeWithdrawFromAccount(currentAccountOnDisplay.getBankAccountBalance());
                    currentAccountOnDisplay.closeAccount();
                }
                break;
            case 4:
            case 8:
                int accountType = getIntPrompt("Which account type would you like to send money to 1.) Checking\n2.)Savings\n3.)Investment");
                int userId = getIntPrompt("What is the user's Id you'd like to transfer to?");
                String userAccountNumber = getStringPrompt("Enter account number to transfer to: ");
                double amount = getDoublePrompt("Please enter the amount you'd like to Transfer: ");
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
                printMessage("Please enter a valid command available on the screen: ");
                startAtmAndPromptUser();
                break;
        }
    }

    public void newCheckingSavingsOrInvestmentAccountSpecifier(int accountType, int id) {
        String accountNumber = getStringPrompt("Please enter desired bank Account Number: ");
        switch (accountType) {
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
                newCheckingSavingsOrInvestmentAccountSpecifier(accountType, id);
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
        printMessage("\nCurrent Balance in " + currentAccountOnDisplay.getClass().getName().replace("io.michaelcarroll.", "") + ": " + formatter.format(currentAccountOnDisplay.getBankAccountBalance()) + "\n");
    }

    public boolean checkPinCode(int pinEntered, int actualPin) {
        if (pinEntered != actualPin)
            return false;
        else
            return true;
    }

    public String menuPrompt() {
        return "WELCOME TO CARROLL BANK - PLEASE ENTER A CORRESPONDING COMMAND\n" +
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
                "12.)Exit ATM\n";

    }

    public int accountTypeToOpen() {
        return getIntPrompt("Would you like to open a 1.)Checking 2.)Savings or 3.)Investment Account? Enter the " +
                "corresponding number value ");
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

    public int getIntPrompt(String promptMsg) {
        int userInput = 0;
        while (true) {
            printMessage(promptMsg);
            try {
                userInput = input.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("I need an integer please");
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Illegal Option");
            }
            if (input.hasNextLine()) {
                input.nextLine();
            }
        }
        return userInput;
    }

    public String getStringPrompt(String promptMsg) {
        printMessage(promptMsg);
        String userInput = "";
        while (true) {
            try {
                userInput = input.next();
                if (input.hasNextLine()) {
                    input.nextLine();
                }
                return userInput;

            } catch (IllegalArgumentException e) {
                System.out.println("I need a string please");
            }
        }
    }

    public double getDoublePrompt(String promptMsg) {
        printMessage(promptMsg);
        double userInput = 0.0;
        while (true) {
            try {
                userInput = input.nextDouble();
                break;
            } catch (InputMismatchException e) {
                System.out.println("I need an double please");
            } catch (IndexOutOfBoundsException e) {
                System.out.println("That isn't one of the options");
            }
            if (input.hasNextLine()) {
                input.nextLine();
            }
        }
        return userInput;
    }

    public void printMessage(String msg) {
        System.out.print(msg);
    }
}
