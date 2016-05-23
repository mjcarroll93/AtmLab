package io.michaelcarroll;

import java.util.HashMap;

public class UserAccountsHandler {

    public static HashMap<Integer, User> usersAccountsWithBank = new HashMap<Integer, User>();

    public static void addAccountToBank(int id, User user) {
        usersAccountsWithBank.put(id, user);
    }
}