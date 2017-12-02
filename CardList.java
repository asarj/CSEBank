/**
 * Name: Ajay Sarjoo
 * SBID: 111623178
 * Stony Brook University
 * Professor Tashbook
 */

import java.util.ArrayList;

public class CardList {
    private ArrayList<BankCard> bankcards = new ArrayList<>();

    public CardList(){}
    public void add(BankCard b){
        this.bankcards.add(b);
    }

    public void add(int i, BankCard b){
        if(i >= 0 && i <= this.bankcards.size()){
            this.bankcards.add(i, b);
        }
        else{
            this.bankcards.add(b);
        }
    }

    public int size(){
        return this.bankcards.size();
    }

    public BankCard get(int i){
        if(i >= 0 && i < this.bankcards.size()){
            return this.bankcards.get(i);
        }
        return null;
    }

    public int indexOf(long cardNumber){
        for(int i = 0; i < this.bankcards.size(); i++){
            if(this.bankcards.get(i).number() == cardNumber){
                return i;
            }
        }

        return -1;
    }
}
