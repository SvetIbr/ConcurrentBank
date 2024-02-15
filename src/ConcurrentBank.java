import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ConcurrentBank {
    private final ConcurrentMap<Long, BankAccount> accounts = new ConcurrentHashMap<>();
    private static long id = 1L;
    private final Object monitor = new Object();

    public synchronized BankAccount createAccount(double amount) {
        BankAccount bankAccount = new BankAccount(amount);
        bankAccount.setId(id);
        accounts.put(id, bankAccount);
        System.out.println("Создан аккаунт с идентификатором " + id);
        id++;
        return bankAccount;
    }

    public void transfer(BankAccount fromAccount, BankAccount toAccount, double amount) {
        synchronized (monitor) {
            if (fromAccount != null
                    && toAccount != null
                    && amount > 0
                    && fromAccount.getBalance() >= amount ) {
                fromAccount.withdraw(amount);
                toAccount.deposit(amount);
                System.out.printf("Выполнен перевод на сумму %f с аккаунта %d на аккаунт %d%n",
                        amount, fromAccount.getId(), toAccount.getId());
            } else {
                System.out.printf("Отменен перевод на сумму %f с аккаунта %d на аккаунт %d%n",
                        amount, fromAccount.getId(), toAccount.getId());
            }
        }
    }
    public double getTotalBalance() {
        double totalBalance = 0;
        for (BankAccount account : accounts.values()) {
            totalBalance += account.getBalance();
        }
        return totalBalance;
    }
}
