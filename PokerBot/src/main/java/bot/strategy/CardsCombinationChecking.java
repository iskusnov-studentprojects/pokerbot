package bot.strategy;

import bot.data.Card;
import bot.data.CardSuit;
import bot.interfaces.IBotStrategy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Sergey on 31.05.2018.
 */
public abstract class CardsCombinationChecking {
    /**
     * Метод для определения наличия комбинации в наборе карт.
     * Каждая комбинация обладает силой, состоящей из базовой силы комбинации, определяемой ее старшенством
     * и силы карты кикера (cardValue).
     * Сила комбинаций:
     * Роял Флеш = 900
     * Стирит Флеш = 800
     * Каре = 700
     * Фулл Хаус = 600
     * Флеш = 500
     * Стрит = 400
     * Сет = 300
     * Две пары = 200
     * Пара = 100
     * Старшая карта = 0
     * @param hand две карты в руке
     * @param table карты на столе (от 3 до 5)
     * @return возвращает силу комбинации в целочисленном эквиваленте
     */
    protected int findCombination (Card[] hand, Card[] table){
        if(table != null && table.length > 0) {
            List<Card> cards = new ArrayList<Card>();
            cards.add(hand[0]);
            cards.add(hand[1]);
            for (int i = 0; i < table.length; i++)
                cards.add(table[i]);
            //Сортировка карт
            cards.sort(new Comparator<Card>() {
                public int compare(Card o1, Card o2) {
                    return o2.compareTo(o1);
                }
            });

            Card card = null;
            //Ищем флеш рояль, стрит флеш
            for (int i = 0; i < 3; i++) {
                int t = 1;
                card = cards.get(i);
                for (int j = i + 1; j < cards.size(); j++)
                    if (card.getCardValue() - t == cards.get(j).getCardValue()
                            && card.getSuit() == cards.get(j).getSuit())
                        t++;
                if (t == 5) {
                    if (card.getCardValue() == 14) //Если старшая карта в комбинации туз, то это флеш рояль
                        return 900;
                    else
                        return 800 + card.getCardValue();
                }
            }


            //Ищем каре и сет
            boolean set = false, pair = false, secondPair = false;
            Card setHeightCard = null, pairHeightCard = null; //старшая карта в комбиниции
            for (int i = 0; i < cards.size() - 2; i++) {
                card = cards.get(i);
                int num = 1;
                for (int j = i + 1; j < cards.size(); j++)
                    if (card.getCardValue() == cards.get(j).getCardValue())
                        num++;
                if (num == 4)
                    return 700 + card.getCardValue();

                if (num == 3) {
                    set = true;
                    if (setHeightCard == null)
                        setHeightCard = card;
                }
            }


            //Ищем пары
            for (int i = 0; i < cards.size() - 1; i++) {
                if (i == 0 && cards.get(i).getCardValue() == cards.get(i + 1).getCardValue() &&
                        cards.get(i).getCardValue() != cards.get(i + 2).getCardValue()) {
                    pair = true;
                    pairHeightCard = cards.get(i);
                }
                if (i > 0 && i < cards.size() - 2 && cards.get(i).getCardValue() != cards.get(i - 1).getCardValue() &&
                        cards.get(i).getCardValue() == cards.get(i + 1).getCardValue() &&
                        cards.get(i).getCardValue() != cards.get(i + 2).getCardValue()) {
                    if (!pair) {
                        pair = true;
                        pairHeightCard = cards.get(i);
                    } else {
                        secondPair = true;
                    }
                }
                if (i == cards.size() - 2 && cards.get(i).getCardValue() != cards.get(i - 1).getCardValue() &&
                        cards.get(i).getCardValue() == cards.get(i + 1).getCardValue()) {
                    if (!pair) {
                        pair = true;
                        pairHeightCard = cards.get(i);
                    } else {
                        secondPair = true;
                    }
                }
            }

            //Если сет и пара, то фулл хаус
            if (set && pair)
                return 600 + setHeightCard.getCardValue();

            //Ищем флеш
            for (int i = 0; i < 3; i++) {
                int s = 1;
                card = cards.get(i);
                for (int j = i + 1; j < cards.size(); j++)
                    if (card.getSuit() == cards.get(j).getSuit())
                        s++;
                if (s == 5)
                    return 500 + card.getCardValue();
            }


            //Ищем стрит
            for (int i = 0; i < 3; i++) {
                int t = 1;
                card = cards.get(i);
                for (int j = i + 1; j < cards.size(); j++)
                    if (card.getCardValue() - t == cards.get(j).getCardValue())
                        t++;
                if (t == 5)
                    return 400 + card.getCardValue();
            }


            //Обрабатываем результаты поиска сета, двух пар и пары
            if (set)
                return 300 + setHeightCard.getCardValue();
            if (pair) {
                if (secondPair)
                    return 200 + pairHeightCard.getCardValue();
                else
                    return 100 + pairHeightCard.getCardValue();
            }
        }

        //Если ничего не нашли, возвращаем старшую карту
        return hand[0].compareTo(hand[1])>=0?hand[0].getCardValue():hand[1].getCardValue();
    }

    /**
     * Функция для проверки прикупной руки для стирита
     * @param hand две карты в руке
     * @param table карты на столе (3 или 4 на терне и ривере соответственно)
     * @return возвращает шанс выпадения стрита
     */
    protected double findDrawStraight (Card[] hand, Card[] table){
        List<Card> cards = new ArrayList<Card>();
        cards.add(hand[0]);
        cards.add(hand[1]);
        for(int i = 0; i < table.length; i++)
            cards.add(table[i]);
        //Сортировка карт
        cards.sort(new Comparator<Card>() {
            public int compare(Card o1, Card o2) {
                return o2.compareTo(o1);
            }
        });

        //при четырех картах
        for(int i = 0; i < cards.size() - 3; i++){
            Card card  = cards.get(i);
            //четыре последовательные карты
            if(card.getCardValue() - 1 == cards.get(i+1).getCardValue() &&
                    card.getCardValue() - 2 == cards.get(i+2).getCardValue() &&
                    card.getCardValue() - 3 == cards.get(i+3).getCardValue()){
                if(card.getCardValue() == 14 ||
                        cards.get(i+3).getCardValue() == 2)
                    return table.length==3 ? (1.-((43./47)*(42./46))) : 4./46;
                else
                    return table.length==3 ? (1.-((39./47)*(38./46))) : 8./46;
            }

            //четыре карты, нехватает карты внутри последовательности
            if(i < cards.size() - 4) {
                if ((card.getCardValue() - 2 == cards.get(i + 1).getCardValue() && //пропуск после первой карты
                        card.getCardValue() - 3 == cards.get(i + 2).getCardValue() &&
                        card.getCardValue() - 4 == cards.get(i + 3).getCardValue()) ||
                        (card.getCardValue() - 1 == cards.get(i + 1).getCardValue() && // пропуск после второй карты
                                card.getCardValue() - 3 == cards.get(i + 2).getCardValue() &&
                                card.getCardValue() - 4 == cards.get(i + 3).getCardValue()) ||
                        (card.getCardValue() - 1 == cards.get(i + 1).getCardValue() && // пропуск после трейтьей карты
                                card.getCardValue() - 2 == cards.get(i + 2).getCardValue() &&
                                card.getCardValue() - 4 == cards.get(i + 3).getCardValue()))
                    return table.length == 3 ? (1. - ((43. / 47) * (42. / 46))) : 4. / 46;
            }
        }

        //при трех картах
        if(table.length == 3) {
            for (int i = 0; i < cards.size() - 2; i++) {
                Card card = cards.get(i);
                //три последовательные карты
                if (card.getCardValue() - 1 == cards.get(i + 1).getCardValue() &&
                        card.getCardValue() - 2 == cards.get(i + 2).getCardValue()) {
                    if (card.getCardValue() == 14 ||
                            cards.get(i + 2).getCardValue() == 2)
                        return (8. / 47) * (4. / 46);
                    else
                        return (8. / 47) * (8. / 46);
                }
                //три карты, нехватает карты внутри последовательности
                if (i < cards.size() - 3)
                    if ((card.getCardValue() - 2 == cards.get(i + 2).getCardValue() && //пропуск после первой карты
                            card.getCardValue() - 3 == cards.get(i + 3).getCardValue()) ||
                            (card.getCardValue() - 1 == cards.get(i + 1).getCardValue() && // пропуск после второй карты
                                    card.getCardValue() - 3 == cards.get(i + 3).getCardValue())) {
                                return (4. / 47) * (8. / 46);
                    }
            }
        }
        return 0;
    }

    /**
     * Функция для проверки прикупной руки для флеша
     * @param hand две карты в руке
     * @param table карты на столе (3 или 4 на терне и ривере соответственно)
     * @return возвращает шанс выпадения флеша
     */
    protected double findDrawFlash (Card[] hand, Card[] table){
        List<Card> cards = new ArrayList<Card>();
        cards.add(hand[0]);
        cards.add(hand[1]);
        for(int i = 0; i < table.length; i++)
            cards.add(table[i]);
        //Сортировка карт
        cards.sort(new Comparator<Card>() {
            public int compare(Card o1, Card o2) {
                return o2.compareTo(o1);
            }
        });

        int h=0, d=0, c=0, s=0;
        for(int i = 0; i < cards.size();i++){
            if(cards.get(i).getSuit() == CardSuit.Hearts)
                h++;
            if(cards.get(i).getSuit() == CardSuit.Diamonds)
                d++;
            if(cards.get(i).getSuit() == CardSuit.Clubs)
                c++;
            if(cards.get(i).getSuit() == CardSuit.Spades)
                s++;
        }
        if(h==4||d==4||c==4||s==4){
            if(table.length == 3)
                return (1.-((38./47)*(37./46)));
            else
                return 9./46;
        }
        if((h==3||d==3||c==3||s==3)&&table.length==3){
            return (9./47)*(8./46);
        }
        return 0;
    }
}
