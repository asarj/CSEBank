/**
 * Name: Ajay Sarjoo
 * SBID: 111623178
 * Stony Brook University
 * Professor Tashbook
 */

public class CreditCard extends BankCard {
    private int expirationDate; //cannot start with a 0, must be in myy or mmyy form
    protected double creditCardLimit; // in dollars

    public CreditCard(String cardHolder, long cardNumber, int expiration, double limit){
        super(cardHolder, cardNumber);
        this.expirationDate = expiration;
        this.creditCardLimit = limit;
    }

    public CreditCard(String cardHolder, long cardNumber, int expiration){
        super(cardHolder, cardNumber);
        this.expirationDate = expiration;
        this.creditCardLimit = 500.00;
    }

    public double limit(){
        return this.creditCardLimit;
    }

    public double availableCredit(){
        return limit() - balance();
    }

    public int expiration(){
        return this.expirationDate;
    }

    private double roundToTwoPlaces (double value) {
        value = value * 100;

        double truncatedValue = Math.floor(value);

        return truncatedValue / 100.0;
    }

    public String toString(){
        return "Card #" + number() + "\tExpiration date: " + expiration() + "\tBalance: $" + roundToTwoPlaces(balance());
    }

    @Override
    public boolean addTransaction(Transaction t) {
        if((t.type().equals("debit") && t.amount() <= availableCredit())){
            this.cardBalance += t.amount();
            this.transacts.add(t);
            return true;
        }
        else if((t.type().equals("debit") && t.amount() > availableCredit())){
            return false;
        }
        else if((t.type().equals("credit"))){
            this.cardBalance += t.amount();
            this.transacts.add(t);
            return true;
        }
        return false;
    }

    @Override
    public void printStatement() {
        System.out.println("Cardholder name: " + cardHolder() + "\nCard Number: " + number() + "\nExpiration Date: " + expiration() + "\nBalance: " + roundToTwoPlaces(balance()));
        for(int i = 0; i < this.transacts.size(); i++){
            System.out.println(this.transacts.get(i));
        }
        System.out.println();
    }
}
