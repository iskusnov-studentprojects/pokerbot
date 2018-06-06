package bot.interfaces;

import bot.data.Player;
import bot.data.PublicState;

import java.util.List;

public interface IStrategyCreator {
    IBotStrategy create(Player player, PublicState publicState, List<Player> enemies);
}
