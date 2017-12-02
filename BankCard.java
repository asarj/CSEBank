/**
 * Name: Ajay Sarjoo
 * SBID: 111623178
 * Stony Brook University
 * Professor Tashbook
 */

import java.util.ArrayList;

public abstract class BankCard {
    private String cardHolderName;
    protected long cardNumber;
    protected double cardBalance;
    protected ArrayList<Transaction> transacts = new ArrayList<Transaction>();

    public BankCard(String cardHolderName, long cardNumber) {
        this.cardHolderName = cardHolderName;
        this.cardNumber = cardNumber;
        this.cardBalance = 0;
    }

    public double balance(){
        return this.cardBalance;
    }

    public long number(){
        return this.cardNumber;
    }

    public String cardHolder(){
        return this.cardHolderName;
    }

    public String toString(){
        return "Card #" + number() + "\tBalance: $" + balance();
    }

    public abstract boolean addTransaction(Transaction t);
    // When implemented, this method attempts to performs the specified Transaction on a card.
    // Its return value indicates success or failure.
    public abstract void printStatement();
    // When implemented, this method prints the current card's basic information
    // (cardholder name, card number, balance, etc.),
    // along with a list of transactions that have been completed successfully.
}
