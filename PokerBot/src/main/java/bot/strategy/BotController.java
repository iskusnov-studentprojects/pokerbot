package bot.strategy;

import bot.data.Action;
import bot.data.Player;
import bot.data.PublicState;
import bot.interfaces.IBotStrategy;
import bot.interfaces.IStrategyCreator;

import java.util.List;

public class BotController {
    private IBotStrategy strategy;
    public void initializeBotStrategy(List players, PublicState publicState, IStrategyCreator creator){
        strategy = creator.create(publicState, players);
    }

    public Action getAction(){
        //todo realization
        return null;
    }

    public void addAction(Action action){
        //todo realization
    }

    public void getPlayerID(){
        //todo realization
    }
}
