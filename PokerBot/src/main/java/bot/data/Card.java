package bot.data;

public class Card implements Comparable<Card>{
    int cardValue;
    CardSuit suit;

    public Card(int cardValue, CardSuit suit) {
        this.cardValue = cardValue;
        this.suit = suit;
    }

    public int getCardValue() {
        return cardValue;
    }

    public CardSuit getSuit() {
        return suit;
    }

    public int compareTo(Card o) {
        return this.cardValue - o.cardValue;
    }
}
