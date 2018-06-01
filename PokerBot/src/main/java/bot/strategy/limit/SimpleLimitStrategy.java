package bot.strategy.limit;

import bot.data.*;
import bot.interfaces.IBotStrategy;
import bot.strategy.ChartOfStartHandUnit;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class SimpleLimitStrategy implements IBotStrategy {
    List<Player> players;
    PublicState publicState;
    int gameStage;
    int makeFold;
    int makeRaise;
    boolean reRaise;

    public SimpleLimitStrategy(PublicState publicState, List<Player> players){
        this.publicState = publicState;
        this.players = players;
    }

    public void setValues(Player[] players, PublicState state) {

    }

    public void submitActions(Queue<Action> actions) {

    }

    public Action makeDecision() {
        //todo realization
        if(gameStage == 0)
            return preflopDecision(null);
        else
            return noPreflopDecision();
    }

    public void updatePublicState(Queue<Card> cards) {
        //todo realization
    }

    private Action preflopDecision(Queue<Action> actions){
        //todo realization
        Player player;

        return null;
    }

    private Action noPreflopDecision(){
        //todo realization
        return null;
    }

    /**
     * Чарт стартовых рук - таблица, в которой указаны стартовые пары карт, с которыми стоит вступать в игру,
     * в зависимости от позиции игрока и действий оппонентов.
     * В данной таблице первый индекс - позиция:
     * 0 - ранняя,
     * 1 - средняя,
     * 2 - поздняя;
     * 3 - малый блайнд
     * 4 - большой блайнд
     * Ваша пара карт:
     * 0 - все сбросились,
     * 1 - один рейз,
     * 2 - два и более рейза,
     * 3 - ререйз после вашего рейза.
     * Каждый элемент таблицы - список стартовых пар карт.
     * @return таблица списков стартовых пар карт.
     */
    private ChartOfStartHandUnit[][] makeChartOfStartHand(){
        ChartOfStartHandUnit[][] result = new ChartOfStartHandUnit[3][];
        for(int i = 0; i < 3; i++) {
            result[i] = new ChartOfStartHandUnit[4];
            for (int j = 0; j < 4; j++)
                result[i][j] = new ChartOfStartHandUnit();
        }
        //region Заполнение списка для ситуации: ранняя позиция, все сбросились (0,0)
        result[0][0].addRule(10,10,0); //JJ
        result[0][0].addRule(11,11,0); //QQ
        result[0][0].addRule(12,12,0); //KK
        result[0][0].addRule(13,13,0); //AA
        result[0][0].addRule(13,12,0); //AKo
        result[0][0].addRule(13,12,1); //AKs
        //endregion
        //region Заполнение списка для ситуации: ранняя позиция, один рейз (0,1)
        result[0][0].addRule(10,10,0); //JJ
        result[0][0].addRule(11,11,0); //QQ
        result[0][0].addRule(12,12,0); //KK
        result[0][0].addRule(13,13,0); //AA
        result[0][0].addRule(13,12,0); //AKo
        result[0][0].addRule(13,12,1); //AKs
        //endregion
        //region Заполнение списка для ситуации: ранняя позиция, два и более рейза (0,2)
        result[0][0].addRule(12,12,0); //KK
        result[0][0].addRule(13,13,0); //AA
        //endregion
        //region Заполнение списка для ситуации: ранняя позиция, ререйз после вашего рейза (0,3)
        result[0][0].addRule(13,13,0); //AA
        result[0][0].addRule(13,12,0); //AKo
        result[0][0].addRule(13,12,1); //AKs
        //endregion
        //region Заполнение списка для ситуации: средняя позиция, все сбросились (1,0)
        result[0][0].addRule(8,8,0); //99
        result[0][0].addRule(9,9,0); //TT
        result[0][0].addRule(10,10,0); //JJ
        result[0][0].addRule(11,11,0); //QQ
        result[0][0].addRule(12,12,0); //KK
        result[0][0].addRule(13,13,0); //AA
        result[0][0].addRule(13,12,0); //AKs
        result[0][0].addRule(13,12,0); //AKo
        result[0][0].addRule(13,11,0); //AQs
        result[0][0].addRule(13,11,0); //AQo
        result[0][0].addRule(13,10,0); //AJs
        //endregion
        //region Заполнение списка для ситуации: средняя позиция, один рейз (1,1)
        result[0][0].addRule(10,10,0); //JJ
        result[0][0].addRule(11,11,0); //QQ
        result[0][0].addRule(12,12,0); //KK
        result[0][0].addRule(13,13,0); //AA
        result[0][0].addRule(13,12,0); //AKo
        result[0][0].addRule(13,12,1); //AKs
        //endregion
        //region Заполнение списка для ситуации: средняя позиция, два и более рейза (1,2)
        result[0][0].addRule(12,12,0); //KK
        result[0][0].addRule(13,13,0); //AA
        //endregion
        //region Заполнение списка для ситуации: средняя позиция, ререйз после вашего рейза (1,3)
        result[0][0].addRule(13,13,0); //AA
        result[0][0].addRule(13,12,0); //AKo
        result[0][0].addRule(13,12,1); //AKs
        //endregion
        //region Заполнение списка для ситуации: поздняя позиция, все сбросились (2,0)
        result[0][0].addRule(6,6,0); //77
        result[0][0].addRule(7,7,0); //88
        result[0][0].addRule(8,8,0); //99
        result[0][0].addRule(9,9,0); //TT
        result[0][0].addRule(10,10,0); //JJ
        result[0][0].addRule(11,11,0); //QQ
        result[0][0].addRule(12,12,0); //KK
        result[0][0].addRule(13,13,0); //AA
        result[0][0].addRule(13,12,1); //AKs
        result[0][0].addRule(13,12,0); //AKo
        result[0][0].addRule(13,11,1); //AQs
        result[0][0].addRule(13,11,0); //AQo
        result[0][0].addRule(13,10,1); //AJs
        result[0][0].addRule(13,10,0); //AJo
        result[0][0].addRule(13,9,1); //ATs
        result[0][0].addRule(12,11,1); //KQs
        //endregion
        //region Заполнение списка для ситуации: поздняя позиция, один рейз (2,1)
        result[0][0].addRule(10,10,0); //JJ
        result[0][0].addRule(11,11,0); //QQ
        result[0][0].addRule(12,12,0); //KK
        result[0][0].addRule(13,13,0); //AA
        result[0][0].addRule(13,12,0); //AKo
        result[0][0].addRule(13,12,1); //AKs
        //endregion
        //region Заполнение списка для ситуации: поздняя позиция, два и более рейза (2,2)
        result[0][0].addRule(12,12,0); //KK
        result[0][0].addRule(13,13,0); //AA
        //endregion
        //region Заполнение списка для ситуации: поздняя позиция, ререйз после вашего рейза (2,3)
        result[0][0].addRule(13,13,0); //AA
        result[0][0].addRule(13,12,0); //AKo
        result[0][0].addRule(13,12,1); //AKs
        //endregion
        return result;
    }

    private boolean checkStartHand(Card firstCard, Card secondCard, int position, int enemyDecisions){
        boolean result = false;
        if(makeChartOfStartHand()[position][enemyDecisions].contains(firstCard, secondCard))
            result = true;
        return result;
    }

    private int checkCombination(){
        return 0;
    }

    //todo удалить этот метод
    public ChartOfStartHandUnit[][] testChartMethod(){
        return makeChartOfStartHand();
    }
}
