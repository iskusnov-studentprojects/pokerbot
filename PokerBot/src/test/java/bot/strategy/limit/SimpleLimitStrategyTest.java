package bot.strategy.limit;

import bot.data.Card;
import bot.data.CardSuit;
import bot.data.Player;
import bot.data.PublicState;
import bot.interfaces.IBotStrategy;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class SimpleLimitStrategyTest {

    SimpleLimitStrategy strategy;

    @Test
    public void justTest() throws Exception{
        Player player = new Player(100, new Card(14, CardSuit.Clubs), new Card(14, CardSuit.Spades), 0);
        List<Card> cardList = new ArrayList<>();
        PublicState publicState = new PublicState(0, 2, 4, cardList);
        List<Player> players = new ArrayList<>();
        players.add(new Player(0, null, null, 1));
        players.add(new Player(0, null, null, 1));
        players.add(new Player(0, null, null, 1));
        strategy = new SimpleLimitStrategy(player, publicState, players);
        strategy.makeDecision();
    }
}