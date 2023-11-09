import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Bank {
    private int uniqueIDAccounts = 0;

    private int uniqueIDClient = 0;
    private List<Client> clients = new ArrayList<>();
    private Client currentClient = null;

    public Bank() {
        /***
         Constructor
         ***/
    }

    public void addNewClient(Client client, int pin) {
        var test = clients;
        if (clients.stream().anyMatch(curClient -> curClient.getIDnumber() == client.getIDnumber())) {
            System.out.println("Client is already exist");
            return;
        }

        client.setPin(pin);
        client.setIDnumber(uniqueIDClient++);
        clients.add(client);
        System.out.println("Client was successfully added!: " + (uniqueIDClient - 1));
    }

    public void logOut() {
        // logout user
        currentClient = null;
        System.out.println("User logged out!");
    }

    public void logIn(int id, int pin) {
        // check if user is already logged in
        if (getCurrentClient() != null) {
            System.out.println("User is already logged in! Logging out...");

            logOut();
        }

        // check if user is known to the bank
        Client checkedCLient = getClients().stream().filter(
                        curClient -> curClient.getIDnumber() == id
                                && curClient.getPin() == pin)
                .findFirst()
                .orElse(null);
        if (checkedCLient == null) {
            System.out.print("This user is not know to the bank, please check if you gave the correct ID and PIN!");
            return;
        }

        // login user
        currentClient = checkedCLient;
    }

    public void addAccount(Client client, double amount) {
        // check whether the client exists
//        if (clients.stream().noneMatch(curClient -> curClient.getIDnumber() == client.getIDnumber())) {
//            return;
//        }

        if (amount < 0) {
            System.out.println("An account was added with a negative starting amount, this should not be possible!");
            return;
        }

        // add account to client
        BankAccount newBankAccount = new BankAccount(uniqueIDAccounts++, amount); // create a brand-new account
        client.addAccount(newBankAccount);
    }

    public void removeAccount(BankAccount toRemove, BankAccount transferAccount) {
        // check if current client exists
        if (currentClient == null) {
            System.out.println("Client not found!");
            return;
        }
        List<BankAccount> accounts = currentClient.getAccounts();

        // check if toRemove and transferAccount exist
        if (toRemove == null) {
            System.out.println("Account not exist");
            return;
        }

        if (transferAccount == null) {
            System.out.println("The account was removed with no transfer of the balance."
                    + "The sum of the balance of all the accounts before removal should be bigger than after removal");
        } else if (toRemove.getBalance() > 0) {
            System.out.println("The account was removed with a transfer of the balance. " +
                    "The sum of the balance of all the accounts before removal should be the same as after removal");

            // transfer left over balance
            transferAccount.addToBalance(toRemove.getBalance());
        }

        // remove account
        accounts.remove(toRemove);
    }


    public void transfer(BankAccount transferFrom, BankAccount transferTo, double amount) {
        BankAccount checkedTransferFrom = clients.
                stream().flatMap(client -> client.getAccounts()
                        .stream().filter(account ->
                                account.getID() == transferFrom.getID()))
                .findFirst().orElse(null);

        BankAccount checkedTransferTo = clients.
                stream().flatMap(client -> client.getAccounts()
                        .stream().filter(account ->
                                account.getID() == transferTo.getID()))
                .findFirst().orElse(null);

        if (checkedTransferFrom == null || checkedTransferTo == null) {
            System.out.println("It is not possible to do a transfer if one or more of the clients are not member at the bank.");
            return;
        }

        if (checkedTransferFrom.getBalance() < amount) {
            System.out.println("A transfer of two clients registered to the bank has failed. The amount that needed to be transferred was less than the minimum of all the accounts.");
            return;
        }

        checkedTransferFrom.removeFromBalance(amount);
        checkedTransferTo.addToBalance(amount);

        System.out.println("A transfer of two clients registered to the bank has gone trough. The amount that needed to be transferred was more than the maximum of all the accounts.");
    }

    public void displayAccounts() {
        /***
         Give a display to the user what accounts are associated with them.
         Give info abouth the index (for easy access), the ID and the amount.
         ***/
    }

    public int maxIDClient() {
        return uniqueIDClient;
    }

    public Client getCurrentClient() {
        return currentClient;
    }

    public List<Client> getClients() {
        return clients;
    }

}
