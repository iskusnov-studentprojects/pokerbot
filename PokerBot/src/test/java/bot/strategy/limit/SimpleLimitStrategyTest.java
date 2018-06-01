package bot.strategy.limit;

import bot.interfaces.IBotStrategy;
import org.junit.Before;

import static org.junit.Assert.*;

public class SimpleLimitStrategyTest {

    SimpleLimitStrategy strategy;

    @Before
    public void setUp() throws Exception {
        //strategy = new SimpleLimitStrategy();
    }

    void chartTableTest(){
        strategy.testChartMethod();
    }
}