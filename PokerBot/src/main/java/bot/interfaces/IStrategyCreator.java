package bot.interfaces;

import bot.data.Player;
import bot.data.PublicState;

import java.util.List;

public interface IStrategyCreator {
    IBotStrategy create(PublicState publicState, List<Player> players);
}
