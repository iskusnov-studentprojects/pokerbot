package bot.strategy;

import bot.data.*;
import bot.interfaces.IBotStrategy;
import bot.interfaces.IStrategyCreator;

import java.util.List;

public class BotController {
    private IBotStrategy strategy;
    private Player currentPlayer;

    public void initializeBotStrategy(Player player, List<Player> enemies, PublicState publicState, IStrategyCreator creator){
        currentPlayer = player;
        for(int i = 0; i < enemies.size(); i++)
            if(enemies.get(i).getId() == player.getId()){
                enemies.remove(i);
                break;
            }
        strategy = creator.create(player, publicState, enemies);
    }

    public int getPlayerID(){
        return currentPlayer.getId();
    }

    public Action getDecision(){
        return strategy.makeDecision();
    }

    public void updatePublicState(int bank, Card[] cards, int gameStage){
        strategy.updateBank(bank);
        strategy.updateCards(cards);
        strategy.updateStage(gameStage);
    }
}
