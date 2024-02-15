import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BankAccount {
    private long id;
    private double balance;
    private final Lock lock;

    public BankAccount(double amount) {
        this.balance = amount;
        this.lock = new ReentrantLock();
    }

    public void deposit(double amount) {
        lock.lock();
        try {
            balance += amount;
            System.out.println("Зачисление средств " + amount);
        } finally {
            lock.unlock();
        }
    }

    public void withdraw(double amount) {
        lock.lock();
        try {
            if (balance >= amount) {
                balance -= amount;
            } else {
                System.out.println("Недостаточно средств для списания");
            }
        } finally {
            lock.unlock();
        }
    }

    public double getBalance() {
        return balance;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

}
