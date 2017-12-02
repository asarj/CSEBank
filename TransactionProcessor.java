/**
 * Name: Ajay Sarjoo
 * SBID: 111623178
 * Stony Brook University
 * Professor Tashbook
 */

// Driver class for the final project
import java.util.*;
import java.io.*;

public class TransactionProcessor {
    public static BankCard convertToCard(String data){
        Scanner s = new Scanner(data);
        long cardNumber = s.nextLong();
        String type = "";
        if(getCardType(cardNumber) != null){
            type = getCardType(cardNumber);
            if(type.equals("CreditCard")){
                CreditCard cc;
                if(s.hasNextDouble()){
                    cc = new CreditCard(s.next(), cardNumber, s.nextInt(), s.nextDouble());
                }
                else{
                    cc = new CreditCard(s.next(), cardNumber, s.nextInt());
                }
                return cc;
            }
            else if(type.equals("RewardsCard")){
                RewardsCard rc;
                if(s.hasNextDouble()){
                    rc = new RewardsCard(s.next(), cardNumber, s.nextInt(), s.nextDouble());
                }
                else{
                    rc = new RewardsCard(s.next(), cardNumber, s.nextInt());
                }
                return rc;
            }
            else if(type.equals("PrepaidCard")){
                PrepaidCard pc;
                if(s.hasNextDouble()){
                    pc = new PrepaidCard(s.next(), cardNumber, s.nextDouble());
                }
                else{
                    pc = new PrepaidCard(s.next(), cardNumber);
                }
                return pc;
            }
        }
        else{
            s.nextLine();
        }
        return null;
    }

    public static CardList loadCardData(String fName){
        CardList cl = new CardList();
        try{
            File data = new File(fName);
            Scanner c = new Scanner(data);
            while(c.hasNextLine()){
                cl.add(convertToCard(c.nextLine()));
            }
            return cl;
        }
        catch(IOException i){
            return null;
        }

    }

    public static void processTransactions(String filename, CardList c){
        try{
            long card = 0;
            int points = 0;
            double money = 0.0;
            File list = new File(filename);
            Scanner p = new Scanner(list);
            while(p.hasNextLine()){
                String[] s = p.nextLine().split(" ");
                long cardNum = Long.parseLong(s[0]);
                int i = c.indexOf(cardNum);

                String trans = s[1];
                if(trans.equals("redeem")){
                    points = Integer.parseInt(s[2]);
                    RewardsCard rc = (RewardsCard)c.get(i);
                    rc.redeemPoints(points);
                }
                else if(trans.equals("top-up")){
                    money = Double.parseDouble(s[2]);
                    PrepaidCard pc = (PrepaidCard)(c.get(i));
                    pc.addFunds(money);
                }
                else{
                    Transaction t = new Transaction(s[1], s[3], Double.parseDouble(s[2]));
                    BankCard bc = c.get(i);
                    bc.addTransaction(t);
                }
            }
        }
        catch(FileNotFoundException f){
            System.out.println("File not found");
        }
    }
    private static String getCardType (long number) {
        // Return a String indicating whether 'number' belongs to a
        // CreditCard, RewardsCard, or a PrepaidCard (or null if it's none
        // of the three)

        String result;

        int firstTwo = Integer.parseInt(("" + number).substring(0,2));

        switch(firstTwo) {
            case 84:
            case 85: result = "CreditCard"; break;
            case 86:
            case 87: result = "RewardsCard"; break;
            case 88:
            case 89: result = "PrepaidCard"; break;
            default: result = null; // invalid card number
        }

        return result;
    }

    public static void main(String[] args){
        System.out.println("\n\n\n------------------RESULTS FOR carddata1.txt & transactiondata1.txt------------------");
        CardList c1 = (loadCardData("carddata1.txt"));
        processTransactions("transactiondata1.txt", c1);
        for(int i = 0; i < c1.size(); i++){
            c1.get(i).printStatement();
        }

        System.out.println("\n\n\n------------------RESULTS FOR carddata2.txt & transactiondata2.txt------------------");
        CardList c2 = (loadCardData("carddata2.txt"));
        processTransactions("transactiondata2.txt", c2);
        for(int i = 0; i < c2.size(); i++){
            c2.get(i).printStatement();
        }
    }
}