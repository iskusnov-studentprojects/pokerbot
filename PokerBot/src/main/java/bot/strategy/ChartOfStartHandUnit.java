package bot.strategy;

import bot.data.Card;

import java.util.List;

public class ChartOfStartHandUnit {
    List<int[]> pairs;

    //todo realization
    public boolean contains(Card firstCard, Card secondCard){
        
        return false;
    }

    /**
     *
     * @param firstCardValue значение первой карты
     * @param secondCardValue значение второй карты
     * @param suitsCombination указывает какое должно быть сочетание мастей карт: 0 - разные масти, 1 - одинаковые масти
     */
    public void addRule(int firstCardValue, int secondCardValue, int suitsCombination){
        pairs.add(new int[]{firstCardValue, secondCardValue, suitsCombination});
    }
}
