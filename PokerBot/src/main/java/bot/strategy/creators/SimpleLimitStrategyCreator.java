package bot.strategy.creators;

import bot.data.Player;
import bot.data.PublicState;
import bot.interfaces.IBotStrategy;
import bot.interfaces.IStrategyCreator;
import bot.strategy.limit.SimpleLimitStrategy;

import java.util.List;

public class SimpleLimitStrategyCreator implements IStrategyCreator{

    public IBotStrategy create(PublicState publicState, List<Player> players) {
        return new SimpleLimitStrategy(publicState, players);
    }
}
