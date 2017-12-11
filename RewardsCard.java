/**
 * Name: Ajay Sarjoo
 * SBID: 111623178
 * Stony Brook University
 * Professor Tashbook
 */

public class RewardsCard extends CreditCard {
    protected int rewardPoints;

    public RewardsCard(String holder, long number, int expiration, double limit){
        super(holder, number, expiration, limit);
        this.rewardPoints = 0;
    }

    public RewardsCard(String holder, long number, int expiration){
        super(holder, number, expiration);
        this.rewardPoints = 0;
    }

    public int rewardPoints(){
        return this.rewardPoints;
    }

    public boolean redeemPoints(int pts){
        if(pts <= rewardPoints()){
            this.cardBalance -= pts/100.0;
            this.rewardPoints -= pts;
            Transaction rewardT = new Transaction("Redemption", "CSEBank", this.cardBalance);
            this.transacts.add(rewardT);
            return true;
        }
        return false;
    }

    private double roundToTwoPlaces (double value) {
        value = value * 100;

        double truncatedValue = Math.floor(value);

        return truncatedValue / 100.0;
    }

    public String toString(){
        return "Card #" + number() + "\tExpiration date: " + expiration() + "\tBalance: $" + roundToTwoPlaces(balance()) + "\tRewards Points: " + rewardPoints();
    }

    public boolean addTransaction(Transaction t){
        if((t.type().equals("debit") && t.amount() <= availableCredit())){
            this.cardBalance += t.amount();
            this.rewardPoints += (t.amount() * 100);
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

    public void printStatement(){
        System.out.println("Cardholder name: " + cardHolder() + "\nCard Number: " + number() + "\nExpiration Date: " + expiration() + "\nBalance: " + roundToTwoPlaces(balance()) + "\nRewards points: " + rewardPoints());
        for(int i = 0; i < this.transacts.size(); i++){
            System.out.println(this.transacts.get(i));
        }
        System.out.println();
    }
}
