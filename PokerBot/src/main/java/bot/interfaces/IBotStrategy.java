package bot.interfaces;

import bot.data.Action;
import bot.data.Card;
import bot.data.Player;
import bot.data.PublicState;

import java.util.Queue;

public interface IBotStrategy {
    void setValues(Player[] players, PublicState state);
    void submitActions(Queue<Action> actions);
    Action makeDecision();
    void updatePublicState(Queue<Card> cards);
}
