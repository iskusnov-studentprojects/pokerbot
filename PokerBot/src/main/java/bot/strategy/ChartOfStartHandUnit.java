package bot.strategy;

import bot.data.Card;

import java.util.ArrayList;
import java.util.List;

public class ChartOfStartHandUnit {
    List<int[]> hands;

    /**
     * Конструктор
     */
    public ChartOfStartHandUnit(){
        hands = new ArrayList<>();
    }

    /**
     * Проверить, есть ли указанная пара карт в списке
     * @param firstCard первая карта
     * @param secondCard вторая карта
     * @return возвращает результат проверки наличия пары карт в списке
     */
    public boolean contains(Card firstCard, Card secondCard){
        for (int[] i: hands) {
            if ((firstCard.getCardValue() == i[0] && secondCard.getCardValue() == i[1] ||
                    firstCard.getCardValue() == i[1] && secondCard.getCardValue() == i[0]) &&
                    (i[2] == 1 ? (firstCard.getSuit() == secondCard.getSuit()) : (firstCard.getSuit() != secondCard.getSuit())))
                return true;
        }
        return false;
    }

    /**
     * Добавить пару карт в список
     * @param firstCardValue значение первой карты
     * @param secondCardValue значение второй карты
     * @param suitsCombination указывает какое должно быть сочетание мастей карт: 0 - разные масти, 1 - одинаковые масти
     */
    public void addRule(int firstCardValue, int secondCardValue, int suitsCombination){
        hands.add(new int[]{firstCardValue, secondCardValue, suitsCombination});
    }
}
