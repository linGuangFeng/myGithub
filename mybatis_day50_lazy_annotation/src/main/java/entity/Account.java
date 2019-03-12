package entity;

public class Account {
    User user;
    int accountId;
    double money;

    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "Account{" +
                "user=" + user +
                ", accountId=" + accountId +
                ", money=" + money +
                '}';
    }

    public void setUser(User user) {
        this.user = user;
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

    public Account() {
    }

    public Account(User user, int accountId, double money) {
        this.user = user;
        this.accountId = accountId;
        this.money = money;
    }
}
