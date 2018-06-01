package bot.data;

import java.util.Collection;
import java.util.Iterator;

//todo Добавление карт возможно стоит сделать другим, возвращать карты возможно стоит в виде ImmutableList
public class PublicState {
    int bank;
    int smallBlind;
    int bigBlind;
    Card[] cards;

    public PublicState(int bank, int smallBlind, int bigBlind, Collection<Card> cards) {
        this.bank = bank;
        this.smallBlind = smallBlind;
        this.bigBlind = bigBlind;
        this.cards = new Card[5];
        int i = 0;
        for(Iterator<Card> iterator = cards.iterator(); iterator.hasNext(); this.cards[i] = iterator.next(), i++);
    }

    public int getBank() {
        return bank;
    }

    public int getSmallBlind() {
        return smallBlind;
    }

    public int getBigBlind() {
        return bigBlind;
    }

    public Card[] getCards() {
        return cards;
    }

    public void increaseBank(int value){
        bank += value;
    }

    public void addCards(){

    }
}
