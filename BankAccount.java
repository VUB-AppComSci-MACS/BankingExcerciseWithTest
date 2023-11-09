public class BankAccount {

    private int uniqueID;
    private double balance;

    public BankAccount(int uniqueID, double amount) {
        this.uniqueID = uniqueID;
        this.balance = amount;
    }

    public void addToBalance(double amount) {
        balance += amount;
    }

    public boolean canBeRemovedFromBalance(double amount) {
        return !(balance < amount);
    }

    public void removeFromBalance(double amount) {
        if (canBeRemovedFromBalance(amount)) { // check if balance is sufficient to be removed
            balance -= amount;
        } else {
            System.out.println("Insufficient balance!");
        }
    }

    public double getBalance() {
        return balance;
    }

    public int getID() {
        return uniqueID;
    }
}
