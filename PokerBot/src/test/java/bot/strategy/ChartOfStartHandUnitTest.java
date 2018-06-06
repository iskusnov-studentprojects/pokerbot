package bot.strategy;

import bot.data.Card;
import bot.data.CardSuit;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Sergey on 03.06.2018.
 */
public class ChartOfStartHandUnitTest {
    ChartOfStartHandUnit object;

    @Before
    public void setUp() throws Exception {
        object = new ChartOfStartHandUnit();
        object.addRule(14,6,1); //A6s
        object.addRule(14,5,1); //A5s
        object.addRule(14,4,1); //A4s
        object.addRule(14,3,1); //A3s
        object.addRule(14,2,1); //A2s
    }

    @Test
    public void containsWithTrueResult() throws Exception {
        assertEquals(true, object.contains(new Card(14, CardSuit.Clubs), new Card(4, CardSuit.Clubs)));
    }

    @Test
    public void containsWithFalseResult() throws Exception {
        assertEquals(false, object.contains(new Card(10, CardSuit.Clubs), new Card(6, CardSuit.Clubs)));
    }

    @Test
    public void containsWithFalseResult2() throws Exception {
        assertEquals(false, object.contains(new Card(14, CardSuit.Clubs), new Card(6, CardSuit.Diamonds)));
    }
}