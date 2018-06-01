package bot.data;

//todo Дописать, подумать на дполем you
public class Player {
    int stack;
    int mise;
    Card firstCard;
    Card secondCard;
    int position;

    public int getStack() {
        return stack;
    }

    public int getMise() {
        return mise;
    }

    public Card getFirstCard() {
        return firstCard;
    }

    public Card getSecondCard() {
        return secondCard;
    }

    public int getPosition() {
        return position;
    }

    public void increaseMise(int value){
        mise += value;
    }
}
