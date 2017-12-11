/**
 * Name: Ajay Sarjoo
 * SBID: 111623178
 * Stony Brook University
 * Professor Tashbook
 **/

// Driver class for the final project
import java.util.*;
import java.io.*;

public class TransactionProcessor {
    public static BankCard convertToCard(String data){
        Scanner s = new Scanner(data);
        long cardNumber = s.nextLong();
        String name = s.next();
        String type = "";
        if(getCardType(cardNumber) != null) {
            type = getCardType(cardNumber);
            if (type.equals("PrepaidCard")) {
                PrepaidCard pc;
                if (s.hasNextDouble()) {
                    pc = new PrepaidCard(name, cardNumber, s.nextDouble());
                } else {
                    pc = new PrepaidCard(name, cardNumber);
                }
                return pc;
            } else {
                int exp = s.nextInt();


                if (type.equals("CreditCard")) {
                    CreditCard cc;
                    if (s.hasNextDouble()) {
                        cc = new CreditCard(name, cardNumber, exp, s.nextDouble());
                    } else {
                        cc = new CreditCard(name, cardNumber, exp);
                    }
                    return cc;
                } else if (type.equals("RewardsCard")) {
                    RewardsCard rc;
                    if (s.hasNextDouble()) {
                        rc = new RewardsCard(name, cardNumber, exp, s.nextDouble());
                    } else {
                        rc = new RewardsCard(name, cardNumber, exp);
                    }
                    return rc;
                }
            }
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
                    RewardsCard rc = (RewardsCard)(c.get(i));
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

    public static void main(String[] args){//Prompt for user output, checks addtransaction in rewards card
        Scanner s = new Scanner(System.in);
        System.out.print("Enter a carddata file name: ");
        String cd = s.nextLine();
        System.out.print("Enter a transaction file name: ");
        String tf = s.nextLine();
        System.out.println("\n\n\n------------------RESULTS FOR " + cd + " & " + tf + " ------------------");
        CardList c1 = (loadCardData(cd));
        processTransactions(tf, c1);
        try{
            for(int i = 0; i < c1.size(); i++){
                c1.get(i).printStatement();
            }
        }
        catch(NullPointerException n){
            System.out.println("Something went wrong");
        }
    }
}