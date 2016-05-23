package io.michaelcarroll;


import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AtmTest {

    Checking checking;
    Savings savings;
    Investment investment;
    User testUser;
    User testUser2;

    @Before
    public void setup() {
        checking = new Checking("TestUser");
        savings = new Savings("TestUser");
        investment = new Investment("TestUser");
        testUser = new User("testUser", 9949);
        testUser2 = new User("testUser2", 4444);
    }


    @Test
    public void depositAmountIntoCheckingAccountTest() {
        double expectedValue = 100;
        checking.makeDepositIntoAccount(100);
        double actualValue = checking.getBankAccountBalance();
        assertEquals("The account balance is 100", expectedValue, actualValue, Math.ulp(0));
    }

    @Test
    public void withdrawAmountFromCheckingAccountTest() {
        double expectedValue = 50;
        checking.setBankAccountBalance(100);
        checking.makeWithdrawFromAccount(50);
        double actualValue = checking.getBankAccountBalance();
        assertEquals("The account balance is 50", expectedValue, actualValue, Math.ulp(0));
    }

    @Test
    public void checkCurrentBalanceInCheckingAccountTest() {
        double expectedValue = 25;
        checking.setBankAccountBalance(25);
        double actualValue = checking.getBankAccountBalance();
        assertEquals("The account balance is 0", expectedValue, actualValue, Math.ulp(0));
    }

    @Test
    public void depositAmountIntoSavingsAccountTest() {
        double expectedValue = 30;
        savings.makeDepositIntoAccount(30);
        double actualValue = savings.getBankAccountBalance();
        assertEquals("The account balance is 30", expectedValue, actualValue, Math.ulp(0));
    }

    @Test
    public void withdrawAmountFromSavingsAccountTest() {
        double expectedValue = 20;
        savings.setBankAccountBalance(40);
        savings.makeWithdrawFromAccount(20);
        double actualValue = savings.getBankAccountBalance();
        assertEquals("The account balance is 20", expectedValue, actualValue, Math.ulp(0));
    }

    @Test
    public void checkCurrentBalanceInSavingsAccountTest() {
        double expectedValue = 50.2;
        savings.setBankAccountBalance(50.2);
        double actualValue = savings.getBankAccountBalance();
        assertEquals("The account balance is 50.2", expectedValue, actualValue, Math.ulp(0));
    }

    @Test
    public void depositAmountIntoInvestmentAccountTest() {
        double expectedValue = 150;
        investment.makeDepositIntoAccount(150);
        double actualValue = investment.getBankAccountBalance();
        assertEquals("The expected balance is 150", expectedValue, actualValue, Math.ulp(0));
    }

    public void withdrawAmountFromInvestmentAccountTest() {
        double expectedValue = 100;
        investment.makeDepositIntoAccount(150);
        investment.makeWithdrawFromAccount(50);
        double actualValue = investment.getBankAccountBalance();
        assertEquals("The expected balance is 100", expectedValue, actualValue, Math.ulp(0));
    }

    @Test
    public void checkCurrentBalanceInInvestmentAccountTest() {
        double expectedValue = 100;
        investment.makeDepositIntoAccount(100);
        double actualValue = investment.getBankAccountBalance();
        assertEquals("The expected balance is 100", expectedValue, actualValue, Math.ulp(0));
    }



}

