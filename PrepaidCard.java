/**
 * Name: Ajay Sarjoo
 * SBID: 111623178
 * Stony Brook University
 * Professor Tashbook
 */

public class PrepaidCard extends BankCard {
    public PrepaidCard(String cardHolder, long cardNumber, double balance){
        super(cardHolder, cardNumber);
        this.cardBalance = balance;
    }

    public PrepaidCard(String cardHolder, long cardNumber){
        super(cardHolder, cardNumber);
        this.cardBalance = 0;
    }

    @Override
    public boolean addTransaction(Transaction t) {
        if(t.type().equals("debit") && t.amount() <= balance()){
            System.out.println("\nTransaction accepted");
            this.cardBalance -= t.amount();
            this.transacts.add(t);
            return true;
        }
        else if(t.type().equals("debit") && t.amount() > balance()){
            System.out.println("\nTransaction failed");
            return false;
        }
        else if(t.type().equals("credit")){
            System.out.println("\nTransaction accepted");
            this.cardBalance -= t.amount();
            this.transacts.add(t);
            return true;
        }
        return false;
    }

    public boolean addFunds(double amt){
        if (amt > 0){
            System.out.println("\nFunds added");
            this.cardBalance += amt;
            Transaction prepaidT = new Transaction("top-up", "User payment", -1 * amt);
            this.transacts.add(prepaidT);
        }
        return false;
    }

    private double roundToTwoPlaces (double value) {
        value = value * 100;

        double truncatedValue = Math.floor(value);

        return truncatedValue / 100.0;
    }

    public String toString(){
        return "Card Holder: " + cardHolder() + "\tCard #" + number() + "\tBalance: $" + roundToTwoPlaces(balance());
    }

    @Override
    public void printStatement() {
        System.out.println("Cardholder name: " + cardHolder() + "\nCard Number: " + number() + "\nBalance: " + roundToTwoPlaces(balance()));
        for(int i = 0; i < this.transacts.size(); i++){
            System.out.println(this.transacts.get(i));
        }
        System.out.println();
    }
}
