package bot.strategy;

import bot.data.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Queue;

import static org.junit.Assert.*;

/**
 * Created by Sergey on 01.06.2018.
 */
public class BaseStrategyTest {
    BaseStrategy object;

    @Before
    public void setUp() throws Exception {
        object = new BaseStrategy() {
            @Override
            public void setValues(Player[] players, PublicState state) {

            }

            @Override
            public void submitActions(Queue<Action> actions) {

            }

            @Override
            public Action makeDecision() {
                return null;
            }

            @Override
            public void updatePublicState(Queue<Card> cards) {

            }
        };
    }

    @Test
    public void findRoyalFlush() throws Exception{
        Card[] hand = {new Card(11, CardSuit.Hearts), new Card(13, CardSuit.Hearts)},
                table = {new Card(12, CardSuit.Hearts),
                        new Card(8, CardSuit.Hearts),
                        new Card(14, CardSuit.Hearts),
                        new Card(6, CardSuit.Hearts),
                        new Card(10, CardSuit.Hearts)
                };
        assertEquals(900, object.findCombination(hand, table));
    }

    @Test
    public void findStraightFlush() throws Exception{
        Card[] hand = {new Card(8, CardSuit.Hearts), new Card(6, CardSuit.Hearts)},
                table = {new Card(9, CardSuit.Hearts),
                        new Card(8, CardSuit.Spades),
                        new Card(7, CardSuit.Hearts),
                        new Card(2, CardSuit.Diamonds),
                        new Card(5, CardSuit.Hearts)
                };
        assertEquals(809, object.findCombination(hand, table));
    }

    @Test
    public void findFiveOfAKind() throws Exception{ //Каре
        Card[] hand = {new Card(14, CardSuit.Clubs), new Card(6, CardSuit.Hearts)},
                table = {new Card(9, CardSuit.Diamonds),
                        new Card(14, CardSuit.Hearts),
                        new Card(7, CardSuit.Spades),
                        new Card(14, CardSuit.Clubs),
                        new Card(14, CardSuit.Diamonds)
                };
        assertEquals(714, object.findCombination(hand, table));
    }

    @Test
    public void findFullHouse() throws Exception{
        Card[] hand = {new Card(13, CardSuit.Clubs), new Card(6, CardSuit.Hearts)},
                table = {new Card(9, CardSuit.Diamonds),
                        new Card(6, CardSuit.Hearts),
                        new Card(7, CardSuit.Spades),
                        new Card(6, CardSuit.Clubs),
                        new Card(13, CardSuit.Diamonds)
                };
        assertEquals(606, object.findCombination(hand, table));
    }

    @Test
    public void findFlush() throws Exception{
        Card[] hand = {new Card(2, CardSuit.Clubs), new Card(5, CardSuit.Hearts)},
                table = {new Card(5, CardSuit.Clubs),
                        new Card(10, CardSuit.Clubs),
                        new Card(5, CardSuit.Spades),
                        new Card(9, CardSuit.Clubs),
                        new Card(3, CardSuit.Clubs)
                };
        assertEquals(510, object.findCombination(hand, table));
    }

    @Test
    public void findStraight() throws Exception{
        Card[] hand = {new Card(6, CardSuit.Diamonds), new Card(7, CardSuit.Hearts)},
                table = {new Card(5, CardSuit.Diamonds),
                        new Card(8, CardSuit.Clubs),
                        new Card(5, CardSuit.Spades),
                        new Card(9, CardSuit.Clubs),
                        new Card(3, CardSuit.Spades)
                };
        assertEquals(409, object.findCombination(hand, table));
    }

    @Test
    public void findSet() throws Exception{
        Card[] hand = {new Card(5, CardSuit.Diamonds), new Card(2, CardSuit.Hearts)},
                table = {new Card(12, CardSuit.Diamonds),
                        new Card(2, CardSuit.Clubs),
                        new Card(9, CardSuit.Spades),
                        new Card(2, CardSuit.Clubs),
                        new Card(3, CardSuit.Spades)
                };
        assertEquals(302, object.findCombination(hand, table));
    }

    @Test
    public void findTwoPair() throws Exception{ //Каре
        Card[] hand = {new Card(5, CardSuit.Diamonds), new Card(2, CardSuit.Hearts)},
                table = {new Card(12, CardSuit.Diamonds),
                        new Card(2, CardSuit.Clubs),
                        new Card(9, CardSuit.Spades),
                        new Card(5, CardSuit.Clubs),
                        new Card(3, CardSuit.Spades)
                };
        assertEquals(205, object.findCombination(hand, table));
    }

    @Test
    public void findPair() throws Exception{
        Card[] hand = {new Card(5, CardSuit.Diamonds), new Card(2, CardSuit.Hearts)},
                table = {new Card(12, CardSuit.Diamonds),
                        new Card(2, CardSuit.Clubs),
                        new Card(9, CardSuit.Spades),
                        new Card(11, CardSuit.Clubs),
                        new Card(3, CardSuit.Spades)
                };
        assertEquals(102, object.findCombination(hand, table));
    }

    @Test
    public void findHighCard() throws Exception{
        Card[] hand = {new Card(5, CardSuit.Diamonds), new Card(2, CardSuit.Hearts)},
                table = {new Card(12, CardSuit.Diamonds),
                        new Card(4, CardSuit.Clubs),
                        new Card(9, CardSuit.Spades),
                        new Card(11, CardSuit.Clubs),
                        new Card(3, CardSuit.Spades)
                };
        assertEquals(5, object.findCombination(hand, table));
    }
}