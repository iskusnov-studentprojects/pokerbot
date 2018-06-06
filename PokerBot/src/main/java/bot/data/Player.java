package bot.data;

import java.util.Stack;

//todo Дописать
public class Player {
    private int id;
    private int stack;
    private int rate;
    private Card firstCard;
    private Card secondCard;
    private int position;
    private Stack<Action> actionStack;

    public Player(int stack, Card firstCard, Card secondCard, int position){
        this.stack = stack;
        this.firstCard = firstCard;
        this.secondCard = secondCard;
        this.position = position;
        rate = 0;
        actionStack = new Stack<>();
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public Stack<Action> getActionStack() {
        return actionStack;
    }

    public int getStack() {
        return stack;
    }

    public void setStack(int value){
        stack = value;
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

    public void addAction(Action action){
        actionStack.add(action);
    }

    public int getId() {
        return id;
    }
}
