import java.util.ArrayList;
import java.util.List;

public class Client {
    private int age;
    private String name;
    private int IDnumber;
    private String address;
    private List<BankAccount> accounts = new ArrayList<>();

    private int bankID;

    private int pin;

    public Client(String name, int age, int IDnumber, String address) {
        this.age = age;
        this.name = name;
        this.IDnumber = IDnumber;
        this.address = address;
    }

    public void addAccount(BankAccount bankAccount) {
        accounts.add(bankAccount);
    }

    public void removeAccount(BankAccount selectedAccount) {
        // cannot remove account if it has balance
        if (selectedAccount.getBalance() > 0) {
            System.out.println("Cannot remove, the account has balance!");
            return;
        }

        // remove selected account
        accounts.remove(selectedAccount);
    }

    public int getAge() {
        return age;
    }

    public int getIDnumber() {
        return IDnumber;
    }

    public void setIDnumber(int IDnumber) {
        this.IDnumber = IDnumber;
    }

    public String getName() {
        return name;
    }

    public List<BankAccount> getAccounts() {
        return accounts;
    }

    public void addBankID(int bankID) {
        this.bankID = bankID;
    }

    public int getBankID() {
        return bankID;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public int getPin() {
        return pin;
    }
}
