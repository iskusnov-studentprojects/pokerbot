package bot.strategy.limit;

import bot.data.*;
import bot.interfaces.IBotStrategy;
import bot.strategy.CardsCombinationChecking;
import bot.strategy.ChartOfStartHandUnit;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class SimpleLimitStrategy extends CardsCombinationChecking implements IBotStrategy {
    List<Player> players;
    PublicState publicState;
    Player player;
    ChartOfStartHandUnit[] chart;
    int gameStage;


    public SimpleLimitStrategy(Player thisPlayer, PublicState publicState, List<Player> players){
        this.publicState = publicState;
        this.players = players;
        this.player = thisPlayer;
        this.chart = makeChartOfStartHand();
    }

    //todo realization
    public void setValues(Player[] players, PublicState state) {

    }

    //todo realization
    public void submitActions(Queue<Action> actions) {

    }

    public Action makeDecision() {
        Action action;
        if(gameStage == 0)
            action = preflopDecision();
        else
            action = noPreflopDecision();
        player.setStack(player.getStack() - action.getValue());
        player.setRate(player.getRate() + action.getValue());
        player.addAction(action);
        return action;
    }

    public void updateCards(Card[] cards) {
        List<Card> c = new ArrayList<>();
        for(int i = 0; i < cards.length; i++)
            c.add(cards[i]);
        publicState.updateCards(c);
    }

    @Override
    public void updateBank(int bank) {
        publicState.setBank(bank);
    }

    public void updateStage(int value){
        gameStage = value;
    }

    private Action preflopDecision(){
        int ecall = 0, eraise = 0;
        boolean reraise = false;
        for (Player i: players) {
            if(i.getActionStack().size() > 0 && i.getActionStack().peek().getType() == ActionTypes.Raise){
                eraise++;
            }
            if (i.getActionStack().size() > 0 && i.getActionStack().peek().getType() == ActionTypes.Call){
                ecall++;
            }
        }
        if(eraise == 0){
            if(player.getPosition() == players.size() - 1) { //Малый блайнд
                //Списки 1, 2, 7, 8 или 9
                if (chart[0].contains(player.getFirstCard(), player.getSecondCard()) || chart[1].contains(player.getFirstCard(), player.getSecondCard()) ||
                        chart[6].contains(player.getFirstCard(), player.getSecondCard()) || chart[7].contains(player.getFirstCard(), player.getSecondCard()) ||
                        chart[8].contains(player.getFirstCard(), player.getSecondCard()))
                    return new Action(ActionTypes.Raise, gameStage < 2 ? publicState.getBigBlind() * 2 : publicState.getBigBlind() * 4);
                    //Списки 4, 5, 10,18,19,21,22, 23 или 26
                else if (chart[3].contains(player.getFirstCard(), player.getSecondCard()) || chart[4].contains(player.getFirstCard(), player.getSecondCard()) ||
                        chart[9].contains(player.getFirstCard(), player.getSecondCard()) || chart[17].contains(player.getFirstCard(), player.getSecondCard()) ||
                        chart[18].contains(player.getFirstCard(), player.getSecondCard()) || chart[20].contains(player.getFirstCard(), player.getSecondCard()) ||
                        chart[21].contains(player.getFirstCard(), player.getSecondCard()) || chart[22].contains(player.getFirstCard(), player.getSecondCard()) ||
                        chart[25].contains(player.getFirstCard(), player.getSecondCard())) {
                    if (ecall == 0)
                        return new Action(ActionTypes.Raise, gameStage < 2 ? publicState.getBigBlind() * 2 : publicState.getBigBlind() * 4);
                    else
                        return new Action(ActionTypes.Call, gameStage < 2 ? publicState.getBigBlind() : publicState.getBigBlind() * 2);
                    //Спистки 3, 14, 15 или 16
                } else if (chart[2].contains(player.getFirstCard(), player.getSecondCard()) || chart[13].contains(player.getFirstCard(), player.getSecondCard()) ||
                        chart[14].contains(player.getFirstCard(), player.getSecondCard()) || chart[15].contains(player.getFirstCard(), player.getSecondCard())) {
                    if (ecall <= 1)
                        return new Action(ActionTypes.Raise, gameStage < 2 ? publicState.getBigBlind() * 2 : publicState.getBigBlind() * 4);
                    else
                        return new Action(ActionTypes.Call, gameStage < 2 ? publicState.getBigBlind() : publicState.getBigBlind() * 2);
                    //Спистки 6, 11, 12, 13, 17, 20, 24, 25 или 27
                } else if (chart[5].contains(player.getFirstCard(), player.getSecondCard()) || chart[10].contains(player.getFirstCard(), player.getSecondCard()) ||
                        chart[11].contains(player.getFirstCard(), player.getSecondCard()) || chart[12].contains(player.getFirstCard(), player.getSecondCard()) ||
                        chart[16].contains(player.getFirstCard(), player.getSecondCard()) || chart[19].contains(player.getFirstCard(), player.getSecondCard()) ||
                        chart[23].contains(player.getFirstCard(), player.getSecondCard()) || chart[24].contains(player.getFirstCard(), player.getSecondCard()) ||
                        chart[26].contains(player.getFirstCard(), player.getSecondCard()))
                    return new Action(ActionTypes.Call, gameStage < 2 ? publicState.getBigBlind() : publicState.getBigBlind() * 2);
                    //Спистки 28, 29, 30, 32 или 33
                else if (chart[27].contains(player.getFirstCard(), player.getSecondCard()) || chart[28].contains(player.getFirstCard(), player.getSecondCard()) ||
                        chart[29].contains(player.getFirstCard(), player.getSecondCard()) || chart[31].contains(player.getFirstCard(), player.getSecondCard()) ||
                        chart[32].contains(player.getFirstCard(), player.getSecondCard())) {
                    if (ecall >= 2)
                        return new Action(ActionTypes.Call, gameStage < 2 ? publicState.getBigBlind() : publicState.getBigBlind() * 2);
                    else
                        return new Action(ActionTypes.Fold, 0);
                }
                //Спистки 31, 34 или 35
                else if (chart[30].contains(player.getFirstCard(), player.getSecondCard()) || chart[33].contains(player.getFirstCard(), player.getSecondCard()) ||
                        chart[34].contains(player.getFirstCard(), player.getSecondCard())) {
                    if (ecall >= 3)
                        return new Action(ActionTypes.Call, gameStage < 2 ? publicState.getBigBlind() : publicState.getBigBlind() * 2);
                    else
                        return new Action(ActionTypes.Fold, 0);
                } else
                    return new Action(ActionTypes.Fold, 0);
            } else if(player.getPosition() == players.size()){ //Большой блайнд
                //Спистки 1, 2, 7, 8 или 9
                if(chart[0].contains(player.getFirstCard(), player.getSecondCard()) || chart[1].contains(player.getFirstCard(), player.getSecondCard()) ||
                        chart[6].contains(player.getFirstCard(), player.getSecondCard()) || chart[7].contains(player.getFirstCard(), player.getSecondCard()) ||
                        chart[8].contains(player.getFirstCard(), player.getSecondCard()))
                    return new Action(ActionTypes.Raise, gameStage < 2 ? publicState.getBigBlind()*2 : publicState.getBigBlind() * 4);
                //Спистки 3, 14 или  15
                else if(chart[2].contains(player.getFirstCard(), player.getSecondCard()) || chart[13].contains(player.getFirstCard(), player.getSecondCard()) ||
                        chart[14].contains(player.getFirstCard(), player.getSecondCard())){
                    if(ecall <= 1)
                        return new Action(ActionTypes.Raise, gameStage < 2 ? publicState.getBigBlind()*2 : publicState.getBigBlind() * 4);
                    else
                        return new Action(ActionTypes.Call, gameStage < 2 ? publicState.getBigBlind() : publicState.getBigBlind() * 2);
                }
                //Спистки 4, 5, 6, 10, 11, 12, 13, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34 или  35
                else if (chart[3].contains(player.getFirstCard(), player.getSecondCard()) || chart[4].contains(player.getFirstCard(), player.getSecondCard()) ||
                        chart[5].contains(player.getFirstCard(), player.getSecondCard()) || chart[9].contains(player.getFirstCard(), player.getSecondCard()) ||
                        chart[10].contains(player.getFirstCard(), player.getSecondCard()) || chart[11].contains(player.getFirstCard(), player.getSecondCard()) ||
                        chart[12].contains(player.getFirstCard(), player.getSecondCard()) || chart[15].contains(player.getFirstCard(), player.getSecondCard()) ||
                        chart[16].contains(player.getFirstCard(), player.getSecondCard()) || chart[17].contains(player.getFirstCard(), player.getSecondCard()) ||
                        chart[18].contains(player.getFirstCard(), player.getSecondCard()) || chart[19].contains(player.getFirstCard(), player.getSecondCard()) ||
                        chart[20].contains(player.getFirstCard(), player.getSecondCard()) || chart[21].contains(player.getFirstCard(), player.getSecondCard()) ||
                        chart[22].contains(player.getFirstCard(), player.getSecondCard()) || chart[23].contains(player.getFirstCard(), player.getSecondCard()) ||
                        chart[24].contains(player.getFirstCard(), player.getSecondCard()) || chart[25].contains(player.getFirstCard(), player.getSecondCard()) ||
                        chart[26].contains(player.getFirstCard(), player.getSecondCard()) || chart[27].contains(player.getFirstCard(), player.getSecondCard()) ||
                        chart[28].contains(player.getFirstCard(), player.getSecondCard()) || chart[29].contains(player.getFirstCard(), player.getSecondCard()) ||
                        chart[30].contains(player.getFirstCard(), player.getSecondCard()) || chart[31].contains(player.getFirstCard(), player.getSecondCard()) ||
                        chart[32].contains(player.getFirstCard(), player.getSecondCard()) || chart[33].contains(player.getFirstCard(), player.getSecondCard()) ||
                        chart[34].contains(player.getFirstCard(), player.getSecondCard()))
                    return new Action(ActionTypes.Check, 0);
                else return new Action(ActionTypes.Fold, 0);
                //todo формула позиции
            } else if(player.getPosition() <= 1) {//Ранняя позиция
                //Спистки 1,2,7 или 8
                if(chart[0].contains(player.getFirstCard(), player.getSecondCard()) || chart[1].contains(player.getFirstCard(), player.getSecondCard()) ||
                        chart[6].contains(player.getFirstCard(), player.getSecondCard()) || chart[7].contains(player.getFirstCard(), player.getSecondCard()))
                    return new Action(ActionTypes.Raise, gameStage < 2 ? publicState.getBigBlind()*2 : publicState.getBigBlind() * 4);
                //Спистки 3, 4, 9, 12 или 14
                else if (chart[2].contains(player.getFirstCard(), player.getSecondCard()) || chart[3].contains(player.getFirstCard(), player.getSecondCard()) ||
                        chart[8].contains(player.getFirstCard(), player.getSecondCard()) || chart[11].contains(player.getFirstCard(), player.getSecondCard()) ||
                        chart[13].contains(player.getFirstCard(), player.getSecondCard()))
                    return new Action(ActionTypes.Call, gameStage < 2 ? publicState.getBigBlind() : publicState.getBigBlind() * 2);
                //Спистки 15, 16 или 21
                else if (chart[14].contains(player.getFirstCard(), player.getSecondCard()) || chart[15].contains(player.getFirstCard(), player.getSecondCard()) ||
                        chart[20].contains(player.getFirstCard(), player.getSecondCard())){
                    if(ecall >= 1)
                        return new Action(ActionTypes.Call, gameStage < 2 ? publicState.getBigBlind() : publicState.getBigBlind() * 2);
                    else
                        return new Action(ActionTypes.Fold, 0);
                } else
                    return new Action(ActionTypes.Fold, 0);
                //todo формула позиции
            } else if (player.getPosition() <= 1){ //Средняя позиция
                //Спистки 1, 2, 7 или 8
                if(chart[0].contains(player.getFirstCard(), player.getSecondCard()) || chart[1].contains(player.getFirstCard(), player.getSecondCard()) ||
                        chart[6].contains(player.getFirstCard(), player.getSecondCard()) || chart[7].contains(player.getFirstCard(), player.getSecondCard()))
                    return new Action(ActionTypes.Raise, gameStage < 2 ? publicState.getBigBlind()*2 : publicState.getBigBlind() * 4);
                //Спистки 14
                else if (chart[13].contains(player.getFirstCard(), player.getSecondCard())) {
                    if (ecall == 0)
                        return new Action(ActionTypes.Raise, gameStage < 2 ? publicState.getBigBlind() * 2 : publicState.getBigBlind() * 4);
                    else
                        return new Action(ActionTypes.Call, gameStage < 2 ? publicState.getBigBlind() : publicState.getBigBlind() * 2);
                }
                //Спистки 3
                else if (chart[2].contains(player.getFirstCard(), player.getSecondCard())){
                    if (ecall <= 1)
                        return new Action(ActionTypes.Raise, gameStage < 2 ? publicState.getBigBlind() * 2 : publicState.getBigBlind() * 4);
                    else
                        return new Action(ActionTypes.Call, gameStage < 2 ? publicState.getBigBlind() : publicState.getBigBlind() * 2);
                }
                //Списки 4, 9, 12, 15, 16 или 18
                else if(chart[3].contains(player.getFirstCard(), player.getSecondCard()) || chart[8].contains(player.getFirstCard(), player.getSecondCard()) ||
                        chart[11].contains(player.getFirstCard(), player.getSecondCard()) || chart[14].contains(player.getFirstCard(), player.getSecondCard()) ||
                        chart[15].contains(player.getFirstCard(), player.getSecondCard()) || chart[17].contains(player.getFirstCard(), player.getSecondCard()))
                    return new Action(ActionTypes.Call, gameStage < 2 ? publicState.getBigBlind() : publicState.getBigBlind() * 2);
                //Списки 5, 13, 21, 22 или 26
                else if (chart[4].contains(player.getFirstCard(), player.getSecondCard()) || chart[12].contains(player.getFirstCard(), player.getSecondCard()) ||
                        chart[20].contains(player.getFirstCard(), player.getSecondCard()) || chart[21].contains(player.getFirstCard(), player.getSecondCard()) ||
                        chart[25].contains(player.getFirstCard(), player.getSecondCard())){
                    if(ecall >= 1)
                        return new Action(ActionTypes.Call, gameStage < 2 ? publicState.getBigBlind() : publicState.getBigBlind() * 2);
                    else
                        return new Action(ActionTypes.Fold, 0);
                }
                //Списки 29 или 32
                else if (chart[28].contains(player.getFirstCard(), player.getSecondCard()) || chart[31].contains(player.getFirstCard(), player.getSecondCard())){
                    if(ecall >= 3)
                        return new Action(ActionTypes.Call, gameStage < 2 ? publicState.getBigBlind() : publicState.getBigBlind() * 2);
                    else
                        return new Action(ActionTypes.Fold, 0);
                } else return new Action(ActionTypes.Fold, 0);
            } else { //Поздняя позиция
                //Списки 1, 2, 7, 8, 9 или 12
                if(chart[0].contains(player.getFirstCard(), player.getSecondCard()) || chart[1].contains(player.getFirstCard(), player.getSecondCard()) ||
                        chart[6].contains(player.getFirstCard(), player.getSecondCard()) || chart[7].contains(player.getFirstCard(), player.getSecondCard()) ||
                        chart[8].contains(player.getFirstCard(), player.getSecondCard()) || chart[1].contains(player.getFirstCard(), player.getSecondCard()))
                    return new Action(ActionTypes.Raise, gameStage < 2 ? publicState.getBigBlind() * 2 : publicState.getBigBlind() * 4);
                //Списки 4, 5, 10, 13, 19, 21, 22 или 26
                else if (chart[3].contains(player.getFirstCard(), player.getSecondCard()) || chart[5].contains(player.getFirstCard(), player.getSecondCard()) ||
                        chart[9].contains(player.getFirstCard(), player.getSecondCard()) || chart[12].contains(player.getFirstCard(), player.getSecondCard()) ||
                        chart[18].contains(player.getFirstCard(), player.getSecondCard()) || chart[20].contains(player.getFirstCard(), player.getSecondCard()) ||
                        chart[21].contains(player.getFirstCard(), player.getSecondCard()) || chart[25].contains(player.getFirstCard(), player.getSecondCard())){
                    if(ecall == 0)
                        return new Action(ActionTypes.Raise, gameStage < 2 ? publicState.getBigBlind() * 2 : publicState.getBigBlind() * 4);
                    else
                        return new Action(ActionTypes.Call, gameStage < 2 ? publicState.getBigBlind() : publicState.getBigBlind() * 2);
                }
                //Списки 3, 14, 15, 16, 17 или 18
                else if(chart[2].contains(player.getFirstCard(), player.getSecondCard()) || chart[13].contains(player.getFirstCard(), player.getSecondCard()) ||
                        chart[14].contains(player.getFirstCard(), player.getSecondCard()) || chart[15].contains(player.getFirstCard(), player.getSecondCard()) ||
                        chart[16].contains(player.getFirstCard(), player.getSecondCard()) || chart[17].contains(player.getFirstCard(), player.getSecondCard())){
                    if(ecall<=1)
                        return new Action(ActionTypes.Raise, gameStage < 2 ? publicState.getBigBlind() * 2 : publicState.getBigBlind() * 4);
                    else
                        return new Action(ActionTypes.Call, gameStage < 2 ? publicState.getBigBlind() : publicState.getBigBlind() * 2);
                }
                //Списки 6, 23, 24, 27 или 29
                else if(chart[5].contains(player.getFirstCard(), player.getSecondCard()) || chart[22].contains(player.getFirstCard(), player.getSecondCard()) ||
                        chart[23].contains(player.getFirstCard(), player.getSecondCard()) || chart[15].contains(player.getFirstCard(), player.getSecondCard()) ||
                        chart[26].contains(player.getFirstCard(), player.getSecondCard()) || chart[28].contains(player.getFirstCard(), player.getSecondCard())){
                    if(ecall>=2)
                        return new Action(ActionTypes.Call, gameStage < 2 ? publicState.getBigBlind() : publicState.getBigBlind() * 2);
                    else
                        return new Action(ActionTypes.Fold, 0);
                }
                //Списки 28, 31 или 32
                else if(chart[27].contains(player.getFirstCard(), player.getSecondCard()) || chart[30].contains(player.getFirstCard(), player.getSecondCard()) ||
                        chart[31].contains(player.getFirstCard(), player.getSecondCard())){
                    if(ecall>=3)
                        return new Action(ActionTypes.Call, gameStage < 2 ? publicState.getBigBlind() : publicState.getBigBlind() * 2);
                    else
                        return new Action(ActionTypes.Fold, 0);
                }
                //Списки 20 или 35
                else if(chart[19].contains(player.getFirstCard(), player.getSecondCard()) || chart[34].contains(player.getFirstCard(), player.getSecondCard())){
                    if(ecall>=4)
                        return new Action(ActionTypes.Call, gameStage < 2 ? publicState.getBigBlind() : publicState.getBigBlind() * 2);
                    else
                        return new Action(ActionTypes.Fold, 0);
                } else
                    return new Action(ActionTypes.Fold, 0);
            }
        } else {//Рейзы были
            if(player.getPosition() == players.size() - 1){ //Малый блайнд
                //Списки 1 или 7
                if(chart[0].contains(player.getFirstCard(), player.getSecondCard()) || chart[6].contains(player.getFirstCard(), player.getSecondCard()))
                    return new Action(ActionTypes.Raise, gameStage < 2 ? publicState.getBigBlind() * 2 : publicState.getBigBlind() * 4);
                //Списки 2
                else if(chart[1].contains(player.getFirstCard(), player.getSecondCard())) {
                    if(ecall + eraise <= 1)
                        return new Action(ActionTypes.Raise, gameStage < 2 ? publicState.getBigBlind() * 2 : publicState.getBigBlind() * 4);
                    else
                        return new Action(ActionTypes.Call, gameStage < 2 ? publicState.getBigBlind() : publicState.getBigBlind() * 2);
                }
                //Списки 4,5 или 6
                else if(chart[3].contains(player.getFirstCard(), player.getSecondCard()) || chart[4].contains(player.getFirstCard(), player.getSecondCard()) ||
                        chart[5].contains(player.getFirstCard(), player.getSecondCard())) {
                    if(ecall + eraise <= 1)
                        return new Action(ActionTypes.Raise, gameStage < 2 ? publicState.getBigBlind() * 2 : publicState.getBigBlind() * 4);
                    else
                        return new Action(ActionTypes.Call, gameStage < 2 ? publicState.getBigBlind() : publicState.getBigBlind() * 2);
                }
                //Списки 16,21 или 26
                else if(chart[15].contains(player.getFirstCard(), player.getSecondCard()) || chart[20].contains(player.getFirstCard(), player.getSecondCard()) ||
                        chart[25].contains(player.getFirstCard(), player.getSecondCard())) {
                    if(ecall + eraise >= 4)
                        return new Action(ActionTypes.Call, gameStage < 2 ? publicState.getBigBlind() : publicState.getBigBlind() * 2);
                    else
                        return new Action(ActionTypes.Fold, 0);
                } else
                    return new Action(ActionTypes.Fold, 0);
            } else if(player.getPosition() == players.size() - 1){ //Большай блайнд
                //Списки 1 или 7
                if(chart[0].contains(player.getFirstCard(), player.getSecondCard()) || chart[6].contains(player.getFirstCard(), player.getSecondCard()))
                    return new Action(ActionTypes.Raise, gameStage < 2 ? publicState.getBigBlind() * 2 : publicState.getBigBlind() * 4);
                //Списки 2
                else if(chart[1].contains(player.getFirstCard(), player.getSecondCard())) {
                    if(ecall + eraise <= 1)
                        return new Action(ActionTypes.Raise, gameStage < 2 ? publicState.getBigBlind() * 2 : publicState.getBigBlind() * 4);
                    else
                        return new Action(ActionTypes.Call, gameStage < 2 ? publicState.getBigBlind() : publicState.getBigBlind() * 2);
                }
                //Списки 3,4,5,8,9,14,16,18,21,22 или 26
                else if(chart[2].contains(player.getFirstCard(), player.getSecondCard()) || chart[3].contains(player.getFirstCard(), player.getSecondCard()) ||
                        chart[4].contains(player.getFirstCard(), player.getSecondCard()) || chart[7].contains(player.getFirstCard(), player.getSecondCard()) ||
                        chart[8].contains(player.getFirstCard(), player.getSecondCard()) || chart[13].contains(player.getFirstCard(), player.getSecondCard()) ||
                        chart[15].contains(player.getFirstCard(), player.getSecondCard()) || chart[17].contains(player.getFirstCard(), player.getSecondCard()) ||
                        chart[20].contains(player.getFirstCard(), player.getSecondCard()) || chart[21].contains(player.getFirstCard(), player.getSecondCard()) ||
                        chart[25].contains(player.getFirstCard(), player.getSecondCard()))
                    return new Action(ActionTypes.Call, gameStage < 2 ? publicState.getBigBlind() : publicState.getBigBlind() * 2);
                //Списки 6,12,24,29,31,32 или 35
                else if(chart[5].contains(player.getFirstCard(), player.getSecondCard()) || chart[11].contains(player.getFirstCard(), player.getSecondCard()) ||
                        chart[23].contains(player.getFirstCard(), player.getSecondCard()) || chart[28].contains(player.getFirstCard(), player.getSecondCard()) ||
                        chart[30].contains(player.getFirstCard(), player.getSecondCard()) || chart[31].contains(player.getFirstCard(), player.getSecondCard()) ||
                        chart[34].contains(player.getFirstCard(), player.getSecondCard())) {
                    if(ecall + eraise >= 2)
                        return new Action(ActionTypes.Call, gameStage < 2 ? publicState.getBigBlind() : publicState.getBigBlind() * 2);
                    else
                        return new Action(ActionTypes.Fold, 0);
                }
                //Списки 13,20,25,28,30,33 или 34
                else if(chart[12].contains(player.getFirstCard(), player.getSecondCard()) || chart[19].contains(player.getFirstCard(), player.getSecondCard()) ||
                        chart[24].contains(player.getFirstCard(), player.getSecondCard()) || chart[27].contains(player.getFirstCard(), player.getSecondCard()) ||
                        chart[29].contains(player.getFirstCard(), player.getSecondCard()) || chart[32].contains(player.getFirstCard(), player.getSecondCard()) ||
                        chart[33].contains(player.getFirstCard(), player.getSecondCard())) {
                    if(ecall + eraise >= 3)
                        return new Action(ActionTypes.Call, gameStage < 2 ? publicState.getBigBlind() : publicState.getBigBlind() * 2);
                    else
                        return new Action(ActionTypes.Fold, 0);
                } else
                    return new Action(ActionTypes.Fold, 0);
                //todo формула позиции
            } else if(player.getPosition() == 1) { //Ранняя позиция
                //Списки 1 или 7
                if(chart[0].contains(player.getFirstCard(), player.getSecondCard()) || chart[6].contains(player.getFirstCard(), player.getSecondCard()))
                    return new Action(ActionTypes.Raise, gameStage < 2 ? publicState.getBigBlind() * 2 : publicState.getBigBlind() * 4);
                //Списки 2
                else if(chart[1].contains(player.getFirstCard(), player.getSecondCard())) {
                    if(ecall + eraise <= 1)
                        return new Action(ActionTypes.Raise, gameStage < 2 ? publicState.getBigBlind() * 2 : publicState.getBigBlind() * 4);
                    else
                        return new Action(ActionTypes.Call, gameStage < 2 ? publicState.getBigBlind() : publicState.getBigBlind() * 2);
                }
                //Списки 8
                else if(chart[7].contains(player.getFirstCard(), player.getSecondCard()))
                    return new Action(ActionTypes.Call, gameStage < 2 ? publicState.getBigBlind() : publicState.getBigBlind() * 2);
                else
                    return new Action(ActionTypes.Fold, 0);
                //todo формула позиции
            } else if(player.getPosition() == 1) { //Средняя позиция
                //Списки 1 или 7
                if(chart[0].contains(player.getFirstCard(), player.getSecondCard()) || chart[6].contains(player.getFirstCard(), player.getSecondCard()))
                    return new Action(ActionTypes.Raise, gameStage < 2 ? publicState.getBigBlind() * 2 : publicState.getBigBlind() * 4);
                //Списки 2
                else if(chart[1].contains(player.getFirstCard(), player.getSecondCard())) {
                    if(ecall + eraise <= 1)
                        return new Action(ActionTypes.Raise, gameStage < 2 ? publicState.getBigBlind() * 2 : publicState.getBigBlind() * 4);
                    else
                        return new Action(ActionTypes.Call, gameStage < 2 ? publicState.getBigBlind() : publicState.getBigBlind() * 2);
                }
                //Списки 8
                else if(chart[7].contains(player.getFirstCard(), player.getSecondCard()))
                    return new Action(ActionTypes.Call, gameStage < 2 ? publicState.getBigBlind() : publicState.getBigBlind() * 2);
                //Списки 3
                else if(chart[2].contains(player.getFirstCard(), player.getSecondCard())) {
                    if(ecall + eraise >= 2)
                        return new Action(ActionTypes.Call, gameStage < 2 ? publicState.getBigBlind() : publicState.getBigBlind() * 2);
                    else
                        return new Action(ActionTypes.Fold, 0);
                }
                //Списки 21
                else if(chart[20].contains(player.getFirstCard(), player.getSecondCard())) {
                    if(ecall + eraise >= 3)
                        return new Action(ActionTypes.Call, gameStage < 2 ? publicState.getBigBlind() : publicState.getBigBlind() * 2);
                    else
                        return new Action(ActionTypes.Fold, 0);
                } else
                    return new Action(ActionTypes.Fold, 0);
            } else { //Поздняя позиция
                //Списки 1 или 7
                if(chart[0].contains(player.getFirstCard(), player.getSecondCard()) || chart[6].contains(player.getFirstCard(), player.getSecondCard()))
                    return new Action(ActionTypes.Raise, gameStage < 2 ? publicState.getBigBlind() * 2 : publicState.getBigBlind() * 4);
                //Списки 2
                else if(chart[1].contains(player.getFirstCard(), player.getSecondCard())) {
                    if(ecall + eraise <= 1)
                        return new Action(ActionTypes.Raise, gameStage < 2 ? publicState.getBigBlind() * 2 : publicState.getBigBlind() * 4);
                    else
                        return new Action(ActionTypes.Call, gameStage < 2 ? publicState.getBigBlind() : publicState.getBigBlind() * 2);
                }
                //Списки 3,8 или 14
                else if(chart[2].contains(player.getFirstCard(), player.getSecondCard()) || chart[7].contains(player.getFirstCard(), player.getSecondCard()) ||
                        chart[13].contains(player.getFirstCard(), player.getSecondCard()))
                    return new Action(ActionTypes.Call, gameStage < 2 ? publicState.getBigBlind() : publicState.getBigBlind() * 2);
                //Списки 26
                else if(chart[25].contains(player.getFirstCard(), player.getSecondCard())) {
                    if(ecall + eraise >= 1)
                        return new Action(ActionTypes.Call, gameStage < 2 ? publicState.getBigBlind() : publicState.getBigBlind() * 2);
                    else
                        return new Action(ActionTypes.Fold, 0);
                }
                //Списки 15
                else if(chart[15].contains(player.getFirstCard(), player.getSecondCard())) {
                    if(ecall + eraise >= 2)
                        return new Action(ActionTypes.Call, gameStage < 2 ? publicState.getBigBlind() : publicState.getBigBlind() * 2);
                    else
                        return new Action(ActionTypes.Fold, 0);
                }
                //Списки 4 или 21
                else if(chart[3].contains(player.getFirstCard(), player.getSecondCard()) || chart[20].contains(player.getFirstCard(), player.getSecondCard())) {
                    if (ecall + eraise >= 3)
                        return new Action(ActionTypes.Call, gameStage < 2 ? publicState.getBigBlind() : publicState.getBigBlind() * 2);
                    else
                        return new Action(ActionTypes.Fold, 0);
                }
                //Списки 5 или 6
                else if(chart[4].contains(player.getFirstCard(), player.getSecondCard()) || chart[5].contains(player.getFirstCard(), player.getSecondCard())) {
                        if(ecall + eraise >= 4)
                            return new Action(ActionTypes.Call, gameStage < 2 ? publicState.getBigBlind() : publicState.getBigBlind() * 2);
                        else
                            return new Action(ActionTypes.Fold, 0);
                } else
                    return new Action(ActionTypes.Fold, 0);
            }
        }
    }

    private Action noPreflopDecision() {
        Card[] hand = new Card[]{player.getFirstCard(), player.getSecondCard()};
        int comb = findCombination(hand, publicState.getCards());
        double flush = findDrawFlash(hand, publicState.getCards()),
                straight = findDrawStraight(hand, publicState.getCards());
        if (comb > 300) {
            if ((gameStage < 2 ? publicState.getBigBlind() * 2 : publicState.getBigBlind() * 4) <= player.getStack())
                return new Action(ActionTypes.Raise, gameStage < 2 ? publicState.getBigBlind() * 2 : publicState.getBigBlind() * 4);
            else if ((gameStage < 2 ? publicState.getBigBlind() : publicState.getBigBlind() * 2) <= player.getStack())
                return new Action(ActionTypes.Call, gameStage < 2 ? publicState.getBigBlind() : publicState.getBigBlind() * 2);
            else
                return new Action(ActionTypes.AllIn, player.getStack());
        } else if (comb > 100) {
            if ((gameStage < 2 ? publicState.getBigBlind() : publicState.getBigBlind() * 2) <= player.getStack())
                return new Action(ActionTypes.Call, gameStage < 2 ? publicState.getBigBlind() : publicState.getBigBlind() * 2);
            else
                return new Action(ActionTypes.AllIn, player.getStack());
        } else if (publicState.getBank() * (flush + straight) - (gameStage < 2 ? publicState.getBigBlind() : publicState.getBigBlind() * 2) * (1. - flush - straight) >= 0) {
            if ((gameStage < 2 ? publicState.getBigBlind() : publicState.getBigBlind() * 2) <= player.getStack())
                return new Action(ActionTypes.Call, gameStage < 2 ? publicState.getBigBlind() : publicState.getBigBlind() * 2);
            else
                return new Action(ActionTypes.AllIn, player.getStack());
        } else return new Action(ActionTypes.Fold, 0);
    }

    private Action makeAction(ActionTypes type, boolean allInPossible){
        if(type == ActionTypes.Raise){
            return new Action(ActionTypes.Call, gameStage < 2 ? publicState.getBigBlind() : publicState.getBigBlind() * 2);
        }
        return null;
    }

    /**
     * Создать массив списков стартовых рук. Используется для принятия решения на префлоп стадии.
     * @return массив списков стартовых пар карт.
     */
    private ChartOfStartHandUnit[] makeChartOfStartHand(){
        ChartOfStartHandUnit[] result = new ChartOfStartHandUnit[35];
        for(int i = 0; i < 35; i++)
            result[i] = new ChartOfStartHandUnit();

        result[0].addRule(14,14,0); //AA
        result[0].addRule(13,13,0); //KK
        result[0].addRule(12,12,0); //QQ

        result[1].addRule(11,11,0); //JJ

        result[2].addRule(10,10,0); //TT
        result[2].addRule(9,9,0); //99

        result[3].addRule(8,8,0); //88
        result[3].addRule(7,7,0); //77

        result[4].addRule(6,6,0); //66
        result[4].addRule(5,5,0); //55

        result[5].addRule(4,4,0); //44
        result[5].addRule(3,3,0); //33
        result[5].addRule(2,2,0); //22

        result[6].addRule(14,13,1); //AKs
        result[6].addRule(14,13,0); //AK

        result[7].addRule(14,12,1); //AQs
        result[7].addRule(14,12,0); //AQ
        result[7].addRule(14,11,1); //AJs

        result[8].addRule(14,11,0); //AJ
        result[8].addRule(14,10,1); //ATs
        result[8].addRule(14,9,1); //A9s

        result[9].addRule(14,10,0); //AT

        result[10].addRule(14,9,0); //A9

        result[11].addRule(14,8,1); //A8s
        result[11].addRule(14,7,1); //A7s

        result[12].addRule(14,6,1); //A6s
        result[12].addRule(14,5,1); //A5s
        result[12].addRule(14,4,1); //A4s
        result[12].addRule(14,3,1); //A3s
        result[12].addRule(14,2,1); //A2s

        result[13].addRule(13,12,1); //KQs

        result[14].addRule(13,12,0); //KQ

        result[15].addRule(13,11,1); //KJs

        result[16].addRule(13,11,0); //KJ

        result[17].addRule(13,10,1); //KTs

        result[18].addRule(13,10,0); //KT

        result[19].addRule(13,9,1); //K9s
        result[19].addRule(13,8,1); //K8s
        result[19].addRule(13,7,1); //K7s
        result[19].addRule(13,6,1); //K6s
        result[19].addRule(13,5,1); //K5s

        result[20].addRule(12,11,1); //QJs

        result[21].addRule(12,10,1); //QTs

        result[22].addRule(12,11,1); //QJ
        result[22].addRule(12,10,1); //QT

        result[23].addRule(12,9,1); //Q9s
        result[23].addRule(12,8,1); //Q8s

        result[24].addRule(12,7,1); //Q7s
        result[24].addRule(12,6,1); //Q6s
        result[24].addRule(12,5,1); //Q5s

        result[25].addRule(11,10,1); //JTs

        result[26].addRule(11,10,0); //JT

        result[27].addRule(11,9,1); //J9s
        result[27].addRule(11,8,1); //J8s

        result[28].addRule(10,9,1); //T9s

        result[29].addRule(10,9,0); //T9

        result[30].addRule(10,8,1); //T8s

        result[31].addRule(9,8,1); //98s

        result[32].addRule(9,8,0); //98

        result[33].addRule(9,7,1); //97s

        result[34].addRule(8,7,1); //87s
        result[34].addRule(7,6,1); //76s
        return result;
    }
}
