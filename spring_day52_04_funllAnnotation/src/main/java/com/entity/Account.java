package com.entity;

public class Account {
    int uid;
    int accountId;
    double money;

    public Account() {
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "Account{：\n" +
                "\n uid=" + uid +
                "\n accountId=" + accountId +
                "\n money=" + money +
                "\n"+
                '}';
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public Account(int uid, int accountId, double money) {
        this.uid = uid;
        this.accountId = accountId;
        this.money = money;
    }
}
