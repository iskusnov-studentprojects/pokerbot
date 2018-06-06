package bot.data;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

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
        this.cards = new Card[]{};
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

    public void setBank(int value){
        bank = value;
    }

    public void updateCards(List<Card> cards){
        this.cards = new Card[cards.size()];
        for(int i = 0; i < cards.size(); i++)
            this.cards[i] = cards.get(i);
    }
}
